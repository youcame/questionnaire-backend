package com.neu.questionnairebackend.service.impl;

import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.service.ChoicesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChoicesServiceImplTest {
    @Resource
    ChoicesService choicesService;
    @Test
    void createChoices() {
        AddSurveyRequest.QuestionRequest questionRequest = new AddSurveyRequest.QuestionRequest();
        choicesService.createChoices(questionRequest.getOptions(), 10);
    }
}