package com.neu.questionnairebackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.neu.questionnairebackend.mapper")
@SpringBootApplication
public class QuestionnaireBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionnaireBackendApplication.class, args);
    }

}
