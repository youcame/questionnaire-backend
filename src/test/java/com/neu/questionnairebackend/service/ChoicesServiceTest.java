package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import com.neu.questionnairebackend.service.impl.ChoicesServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ChoicesServiceTest {

    @Mock
    private ChoicesMapper choicesMapper;

    @InjectMocks
    private ChoicesServiceImpl choicesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateChoices_Success() {
        // Arrange
        AddSurveyRequest.QuestionRequest.OptionRequest optionRequest = new AddSurveyRequest.QuestionRequest.OptionRequest();
        optionRequest.setOption("Option 1");
        optionRequest.setDestination("1");
        List<AddSurveyRequest.QuestionRequest.OptionRequest> optionRequests = new ArrayList<>();
        optionRequests.add(optionRequest);
        int questionId = 1;
        Choices expectedChoices = new Choices();
        expectedChoices.setQuestionId(questionId);
        expectedChoices.setDescription("Option 1");
        expectedChoices.setDestination(1);
        when(choicesMapper.insert(any(Choices.class))).thenReturn(1);
        boolean result = choicesService.createChoices(optionRequests, questionId);
        Assertions.assertTrue(result);
        verify(choicesMapper, times(1)).insert(expectedChoices);
    }

    @Test
    void testCreateChoices_EmptyOptionList() {
        List<AddSurveyRequest.QuestionRequest.OptionRequest> optionRequests = new ArrayList<>();
        int questionId = 1;
        boolean result = choicesService.createChoices(optionRequests, questionId);
        Assertions.assertTrue(result);
        verify(choicesMapper, never()).insert(any(Choices.class));
    }
    @Test
    void testCreateChoices_EmptyDestination() {
        AddSurveyRequest.QuestionRequest.OptionRequest optionRequest = new AddSurveyRequest.QuestionRequest.OptionRequest();
        optionRequest.setOption("Option 1");
        optionRequest.setDestination("");
        List<AddSurveyRequest.QuestionRequest.OptionRequest> optionRequests = new ArrayList<>();
        optionRequests.add(optionRequest);
        int questionId = 1;
        Choices expectedChoices = new Choices();
        expectedChoices.setQuestionId(questionId);
        expectedChoices.setDescription("Option 1");
        expectedChoices.setDestination(0);
        when(choicesMapper.insert(any(Choices.class))).thenReturn(1);
        boolean result = choicesService.createChoices(optionRequests, questionId);
        Assertions.assertTrue(result);
        verify(choicesMapper, times(1)).insert(expectedChoices);
    }

}