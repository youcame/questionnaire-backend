package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.bizmq.AiUtil;
import com.neu.questionnairebackend.bizmq.SurveyMessageProducer;
import com.neu.questionnairebackend.common.BaseResponse;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.common.ResultUtil;
import com.neu.questionnairebackend.constant.AiStatus;
import com.neu.questionnairebackend.exception.BusinessException;
import com.neu.questionnairebackend.manager.RedisLimiterManager;
import com.neu.questionnairebackend.mapper.AnswersheetMapper;
import com.neu.questionnairebackend.model.domain.Answersheet;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.model.dto.AnswerRequest;
import com.neu.questionnairebackend.model.dto.RecordUserAnswerRequest;
import com.neu.questionnairebackend.model.vo.AiFrontendVo;
import com.neu.questionnairebackend.service.AnswersheetService;
import com.neu.questionnairebackend.service.SurveyService;
import com.neu.questionnairebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import static com.neu.questionnairebackend.constant.UserConstant.ADMIN_ROLE;
import static com.neu.questionnairebackend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@Slf4j
@RequestMapping("/answer")
public class AnswerSheetController {

    @Resource
    AnswersheetService answersheetService;
    @Resource
    SurveyService surveyService;
    @Resource
    SurveyMessageProducer surveyMessageProducer;
    @Resource
    RedisLimiterManager redisLimiterManager;
    @Resource
    ThreadPoolExecutor threadPoolExecutor;

    /**
     *
     * @param id 为survey的Id
     * @param userId
     * @return  如果只有（answer）id，则只返回所有回答过的问卷列表信息；如果有（answer）Id与userId与
     *
     */
    @GetMapping("/getAnswerById")
    public BaseResponse<AnswerRequest> getAnswerById(int id, Integer userId) {
        if (id <= 0) throw new BusinessException(ErrorCode.PARAM_ERROR);
        else if (userId == null || userId <= 0 ){
            return ResultUtil.success(answersheetService.getAnswerById(id));
        }
        else {
            AnswerRequest answerById = answersheetService.getAnswerById(id);
            return ResultUtil.success(answerById);
        }
    }

    /**
     * 查看某个问卷所有的回答情况
     * @param surveyId
     * @return
     */
    @GetMapping("/getAnswers")
    public BaseResponse<List<Answersheet>> getAnswers(Integer surveyId){
        List<Answersheet> allAnswers = answersheetService.getAllAnswers(surveyId);
        return ResultUtil.success(allAnswers);
    }

    /**
     * 记录用户提交的问卷
     * @param recordRequest
     * @param request
     * @return
     */
    @RequestMapping("/recordAnswer")
    public BaseResponse<Boolean> recordUserAnswer(@RequestBody RecordUserAnswerRequest recordRequest, HttpServletRequest request){
        if(recordRequest==null||request.getSession().getAttribute(USER_LOGIN_STATE)==null){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"未接收到答案或未登录");
        }
        answersheetService.recordUserAnswer(recordRequest);
        return ResultUtil.success(true);
    }

    /**
     * 获取ai回复的请求
     * @param surveyId
     * @param request
     * @return
     */
    @GetMapping("/ai/mq")
    public BaseResponse<Boolean> sendAiResponse(Integer surveyId,HttpServletRequest request){
        User loginUser = UserAuthority.getLoginUser(request);
        if(!(ADMIN_ROLE == loginUser.getUserRole())){
            throw new BusinessException(ErrorCode.NO_AUTH,"目前对管理员和vip用户开放哦~");
        }
        redisLimiterManager.doLimit("getAiRequest_"+ loginUser.getId());
        surveyMessageProducer.sendMessageAnalyse(surveyId);
        return ResultUtil.success(true);
    }

    /**
     * 获取ai回复的请求(异步)
     * @param surveyId
     * @param httpRequest
     * @return
     */
    @GetMapping("/ai/async")
    public BaseResponse<Boolean> sendAiResponseAsync(Integer surveyId,HttpServletRequest httpRequest){
        User loginUser = UserAuthority.getLoginUser(httpRequest);
        if(!(ADMIN_ROLE == loginUser.getUserRole())){
            throw new BusinessException(ErrorCode.NO_AUTH,"目前对管理员和vip用户开放哦~");
        }
        redisLimiterManager.doLimit("getAiRequest_"+ loginUser.getId());
        Survey survey = surveyService.getById(surveyId);
        //问题列表
        AddSurveyRequest surveyRequest = surveyService.getSurveyById(surveyId);
        List<AddSurveyRequest.QuestionRequest> addQuestion = surveyRequest.getAddQuestion();
        StringBuilder sb = new StringBuilder();
        int questionNum = 0;
        int optionNum = 0;
        //选项列表
        QueryWrapper<Answersheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("surveyId", surveyId);
        List<Answersheet> one = answersheetService.list(queryWrapper);
        if(one.size()==0){
            survey.setAiStatus(AiStatus.FAILED);
            surveyService.updateById(survey);
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        AnswerRequest request = answersheetService.getAnswerById(one.get(0).getId());
        List<AnswerRequest.QuestionDTO> questions = request.getQuestions();
        for (AddSurveyRequest.QuestionRequest questionRequest : addQuestion) {
            questionNum++;
            //sb.append("第").append(questionNum).append("题:");
            sb.append("题目:").append(questionRequest.getQuestionDescription()).append("\\n");
            List<AddSurveyRequest.QuestionRequest.OptionRequest> options = questionRequest.getOptions();
            for (AddSurveyRequest.QuestionRequest.OptionRequest option : options) {
                optionNum++;
                int i=optionNum;
                String xuanxiang=(i == 1 ? "A" : i == 2 ? "B" : i == 3 ? "C" : i == 4 ? "D" : i == 5 ? "E" : "F");
                sb.append(xuanxiang).append(":").append(option.getOption()).append(";");
            }
            sb.append(questions.get(questionNum-1).getStatistics()).append("。\\n");
            optionNum=0;
        }
        CompletableFuture.runAsync(() -> {
            survey.setAiStatus(AiStatus.RUNNING);
            surveyService.updateById(survey);
            String response = AiUtil.getResponse(sb.toString());
            if (StringUtils.isBlank(response)) {
                survey.setAiStatus(AiStatus.FAILED);
                surveyService.updateById(survey);
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "gpt生成错误");
            }
            survey.setAiStatistic(response);
            survey.setAiStatus(AiStatus.FINISH);
            boolean b = surveyService.updateById(survey);
            if (!b) {
                survey.setAiStatus(AiStatus.FAILED);
                surveyService.updateById(survey);
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "gpt生成错误");
            }
        },threadPoolExecutor);
        return ResultUtil.success(true);
    }

    /**
     * 查询ai回复的请求
     * @param surveyId
     * @return
     */
    @GetMapping("/get/ai")
    public BaseResponse<AiFrontendVo> getAiResponse(Integer surveyId){
        AiFrontendVo aiFrontendVo = new AiFrontendVo();
        Survey survey = surveyService.getById(surveyId);
        BeanUtils.copyProperties(survey,aiFrontendVo);
        return ResultUtil.success(aiFrontendVo);
    }


}
