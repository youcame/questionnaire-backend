package com.neu.questionnairebackend.bizmq;

import com.neu.questionnairebackend.constant.AiConstant;
import com.neu.questionnairebackend.constant.SurveyMqConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;


@Component
public class SurveyMessageInit {
    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = SurveyMqConstant.SURVEY_EXCHANGE_NAME;
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 创建队列，随机分配一个队列名称
            String queueName = SurveyMqConstant.SURVEY_QUEUE_NAME;
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME,  SurveyMqConstant.SURVEY_ROUTING_KEY);
        } catch (Exception e) {

        }

    }
}
