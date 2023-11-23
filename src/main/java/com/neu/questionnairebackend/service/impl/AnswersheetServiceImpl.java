package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.exception.BusinessException;
import com.neu.questionnairebackend.mapper.AnswersheetMapper;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import com.neu.questionnairebackend.mapper.QuestionMapper;
import com.neu.questionnairebackend.mapper.SurveyMapper;
import com.neu.questionnairebackend.model.domain.Answersheet;
import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.dto.AnswerRequest;
import com.neu.questionnairebackend.model.dto.AnswerRequest.QuestionDTO;
import com.neu.questionnairebackend.model.dto.AnswerRequest.QuestionDTO.OptionDTO;
import com.neu.questionnairebackend.model.dto.RecordUserAnswerRequest;
import com.neu.questionnairebackend.service.AnswersheetService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AnswersheetServiceImpl extends ServiceImpl<AnswersheetMapper, Answersheet> implements AnswersheetService {

    @Resource
    private AnswersheetMapper answersheetMapper;
    @Resource
    private SurveyMapper surveyMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private ChoicesMapper choicesMapper;
    @Resource
    private RedissonClient redissonClient;

    /**
     * @param answerId
     * @return 通过Answer的Id得到一个问卷的答案信息
     */
    @Override
    public AnswerRequest getAnswerById(int answerId) {
        Answersheet answersheet = answersheetMapper.selectById(answerId);
        AnswerRequest answerRequest = new AnswerRequest();
        answerRequest.setId(answersheet.getId());
        // 获取问卷信息
        Survey survey = surveyMapper.selectById(answersheet.getSurveyId());
        answerRequest.setSurveyName(survey.getSurveyName());
        answerRequest.setSurveyDescription(survey.getDescription());
        answerRequest.setProjectId(survey.getProjectId());
        // 获取问题列表
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        List<Question> questionList = questionMapper.selectList(
                new QueryWrapper<Question>().eq("surveyId", answersheet.getSurveyId()));
        for (int i = 0; i < questionList.size(); i++) {
            Question question = questionList.get(i);
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setId(question.getId());
            questionDTO.setQuestionDescription(question.getQuestionDescription());
            QueryWrapper<Answersheet> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("questionID", question.getId());
            queryWrapper.eq("surveyId", survey.getId());
            queryWrapper.eq("userAccount", answersheet.getUserAccount());
            Answersheet answersheet1 = answersheetMapper.selectOne(queryWrapper);
            String userAnswers = answersheet1.getSelectChoices();
            // 获取选项列表
            List<OptionDTO> optionDTOList = new ArrayList<>();
            List<Choices> choicesList = choicesMapper.selectList(
                    new QueryWrapper<Choices>().eq("questionId", question.getId()));

            String statistic = this.getStatistic(question, choicesList.size());

            //遍历选项的信息
            for (Choices choices : choicesList) {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setId(choices.getId());
                optionDTO.setLabel(choices.getDescription());
                optionDTOList.add(optionDTO);
            }
            questionDTO.setOptions(optionDTOList);
            questionDTO.setStatistics(statistic);
            // 设置用户答案
            List<String> userAnswerList = new ArrayList<>();
            String[] userAnswerArray = userAnswers.split(",");
            userAnswerList.addAll(Arrays.asList(userAnswerArray));
            questionDTO.setUserAnswer(userAnswerList);
            questionDTOList.add(questionDTO);
        }
        answerRequest.setQuestions(questionDTOList);
        return answerRequest;
    }

    @Override
    public AnswerRequest getAnswerById(int id, Integer userId) {
        AnswerRequest answerById = this.getAnswerById(id);
        QueryWrapper<AnswerRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        return answerById;
    }

    /**
     * 获取所有问卷回答的情况
     *
     * @param surveyId
     * @return
     */
    @Override
    public List<Answersheet> getAllAnswers(Integer surveyId) {
        //查找所有问题回答
        List<Answersheet> allAnswersheets = answersheetMapper.selectList(null);
        Set<String> uniqueSurveyUserIds = new HashSet<>();
        List<Answersheet> filteredAnswersheets = new ArrayList<>();
        for (Answersheet answersheet : allAnswersheets) {
            String surveyUserId = answersheet.getSurveyId() + "-" + answersheet.getUserId();
            if (!uniqueSurveyUserIds.contains(surveyUserId)) {
                uniqueSurveyUserIds.add(surveyUserId);
                if (surveyId != null) {
                    if (answersheet.getSurveyId() != surveyId) continue;
                    filteredAnswersheets.add(answersheet);
                } else filteredAnswersheets.add(answersheet);
            }
        }
        return filteredAnswersheets;
    }

    @Override
    public Boolean recordUserAnswer(RecordUserAnswerRequest answerRequest) {
        List<RecordUserAnswerRequest.Questions> list = answerRequest.getQuestions();
        int n = list.size();
        Integer surveyId = answerRequest.getSurveyId();
        Long answerRequestId = answerRequest.getId();
        String lockName = String.format("survey:submit:%s:%s", surveyId, answerRequestId);
        RLock lock = redissonClient.getLock(lockName);
        try {
            if (lock.tryLock(0, 30000L, TimeUnit.MILLISECONDS)) {
                QueryWrapper<Answersheet> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("surveyId", surveyId);
                queryWrapper.eq("userId", answerRequestId);
                Long aLong = answersheetMapper.selectCount(queryWrapper);
                if (aLong > 0) throw new BusinessException(ErrorCode.PARAM_ERROR, "你已经填写过此问卷了");
                for (int i = 0; i < n; i++) {
                    Answersheet answersheet = new Answersheet();
                    answersheet.setUserId(answerRequestId);
                    answersheet.setUserAccount(answerRequest.getUserAccount());
                    answersheet.setSurveyId(surveyId);
                    String selectChoices = list.get(i).getAns().stream().map(String::valueOf).collect(Collectors.joining(","));
                    answersheet.setQuestionID(list.get(i).getId());
                    answersheet.setSelectChoices(selectChoices);
                    int insert = answersheetMapper.insert(answersheet);
                    if (insert <= 0) throw new BusinessException(ErrorCode.SYSTEM_ERROR, "保存答案时出现错误");
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
        return true;
    }

    /**
     * 获取某个问题的答案信息
     *
     * @param question(问题对象),n(选项个数)
     * @return
     */
    private String getStatistic(Question question, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            int questionId = question.getId();
            QueryWrapper<Answersheet> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("questionId", questionId);
            long total = this.count(queryWrapper);
            queryWrapper.eq("selectChoices", String.valueOf(i));
            long count = this.count(queryWrapper);
            sb.append("选择" + (i == 1 ? "A" : i == 2 ? "B" : i == 3 ? "C" : i == 4 ? "D" : i == 5 ? "E" : "F") + "的人数为:"
                    + String.valueOf(count) + ",占比为:" + String.format("%.2f", (count * 1.0 / total * 100)) + "%\n");
        }
        return sb.toString();
    }
}