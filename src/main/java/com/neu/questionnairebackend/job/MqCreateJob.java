package com.neu.questionnairebackend.job;

import com.neu.questionnairebackend.constant.SurveyMqConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MqCreateJob {

    @Scheduled(cron = "0 0 22 * * *")
    public void doMqCreateJob(){
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = SurveyMqConstant.SURVEY_EXCHANGE_NAME;
            // 检查交换机是否已存在
            try {
                channel.exchangeDeclarePassive(EXCHANGE_NAME);
            } catch (IOException e) {
                // 交换机不存在，进行声明
                channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            }
            // 创建队列，随机分配一个队列名称
            String queueName = SurveyMqConstant.SURVEY_QUEUE_NAME;

            // 检查队列是否已存在
            try {
                channel.queueDeclarePassive(queueName);
            } catch (IOException e) {
                // 队列不存在，进行声明
                channel.queueDeclare(queueName, true, false, false, null);
                channel.queueBind(queueName, EXCHANGE_NAME, SurveyMqConstant.SURVEY_ROUTING_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
