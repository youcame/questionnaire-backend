package com.neu.questionnairebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@MapperScan("com.neu.questionnairebackend.mapper")
@SpringBootApplication
@EnableScheduling
public class QuestionnaireBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionnaireBackendApplication.class, args);
    }

}
