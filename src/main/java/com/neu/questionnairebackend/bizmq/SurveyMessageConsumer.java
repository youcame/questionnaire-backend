package com.neu.questionnairebackend.bizmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.constant.SurveyMqConstant;
import com.neu.questionnairebackend.exception.BusinessException;
import com.neu.questionnairebackend.model.domain.Answersheet;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.model.dto.AnswerRequest;
import com.neu.questionnairebackend.service.AnswersheetService;
import com.neu.questionnairebackend.service.SurveyService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.BindException;
import java.util.List;

@Component
@Slf4j
public class SurveyMessageConsumer {

    @Resource
    SurveyService surveyService;
    @Resource
    AnswersheetService answersheetService;

    @RabbitListener(queues = {SurveyMqConstant.SURVEY_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("receiveMessage message = {}", message);
        if(StringUtils.isBlank(message)){
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        int id = Integer.parseInt(message);
        Survey survey = surveyService.getById(id);
        survey.setAiStatus("running");
        //问题列表
        AddSurveyRequest surveyRequest = surveyService.getSurveyById(id);
        List<AddSurveyRequest.QuestionRequest> addQuestion = surveyRequest.getAddQuestion();
        StringBuilder sb = new StringBuilder();
        int questionNum = 0;
        int optionNum = 0;
        //选项列表
        QueryWrapper<Answersheet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("surveyId", id);
        List<Answersheet> one = answersheetService.list(queryWrapper);
        AnswerRequest request = answersheetService.getAnswerById(one.get(0).getId());
        List<AnswerRequest.QuestionDTO> questions = request.getQuestions();
        for (AddSurveyRequest.QuestionRequest questionRequest : addQuestion) {
            questionNum++;
            sb.append("第").append(questionNum).append("题:");
            sb.append("题目为:").append(questionRequest.getQuestionDescription()).append("\\n");
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
        String response = AiUtil.getResponse(sb.toString());
        System.out.println(response);
    }

}
