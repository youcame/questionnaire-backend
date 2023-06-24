package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import com.neu.questionnairebackend.service.AnswersheetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AnswersheetServiceImpl extends ServiceImpl<AnswersheetMapper, Answersheet> implements AnswersheetService {

    @Resource
    private AnswersheetMapper answersheetMapper;
    @Resource
    private SurveyMapper surveyMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private ChoicesMapper choicesMapper;

    @Override
    public AnswerRequest getAnswerById(int id) {
        Answersheet answersheet = answersheetMapper.selectById(id);
        AnswerRequest answerRequest = new AnswerRequest();
        answerRequest.setId(answersheet.getId());

        // 获取问卷信息
        Survey survey = surveyMapper.selectById(answersheet.getSurveyId());
        answerRequest.setSurveyName(survey.getSurveyName());
        answerRequest.setSurveyDescription(survey.getDescription());

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
            queryWrapper.eq("questionID",question.getId());
            queryWrapper.eq("surveyId", survey.getId());
            Answersheet answersheet1 = answersheetMapper.selectOne(queryWrapper);
            String userAnswers = answersheet1.getSelectChoices();

            // 获取选项列表
            List<OptionDTO> optionDTOList = new ArrayList<>();
            List<Choices> choicesList = choicesMapper.selectList(
                    new QueryWrapper<Choices>().eq("questionId", question.getId()));
            for (Choices choices : choicesList) {
                OptionDTO optionDTO = new OptionDTO();
                optionDTO.setId(choices.getId());
                optionDTO.setLabel(choices.getDescription());
                optionDTOList.add(optionDTO);
            }
            questionDTO.setOptions(optionDTOList);
            System.out.println(userAnswers);
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
    public List<Answersheet> getAllAnswers() {
        List<Answersheet> allAnswersheets = answersheetMapper.selectList(null);
        Set<String> uniqueSurveyUserIds = new HashSet<>();
        List<Answersheet> filteredAnswersheets = new ArrayList<>();

        for (Answersheet answersheet : allAnswersheets) {
            String surveyUserId = answersheet.getSurveyId() + "-" + answersheet.getUserId();
            if (!uniqueSurveyUserIds.contains(surveyUserId)) {
                uniqueSurveyUserIds.add(surveyUserId);
                filteredAnswersheets.add(answersheet);
            }
        }

        return filteredAnswersheets;
    }
}