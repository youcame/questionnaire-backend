package com.neu.questionnairebackend.bizmq;

import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.constant.SurveyMqConstant;
import com.neu.questionnairebackend.exception.BusinessException;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.service.SurveyService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.BindException;

@Component
@Slf4j
public class SurveyMessageConsumer {

    @Resource
    SurveyService surveyService;

    @RabbitListener(queues = {SurveyMqConstant.SURVEY_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        log.info("receiveMessage message = {}", message);
        if(StringUtils.isBlank(message)){
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        int id = Integer.parseInt(message);
        Survey survey = surveyService.getById(id);
        AddSurveyRequest surveyRequest = surveyService.getSurveyById(id);
        System.out.println(surveyRequest);
    }

}
