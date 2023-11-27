package com.neu.questionnairebackend.bizmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class SurveyMessageProducer {
    @Resource
    RabbitTemplate rabbitTemplate;

    public void sendMessageToSubmitSurvey(){

    }
}
