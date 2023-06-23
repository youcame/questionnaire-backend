package com.neu.questionnairebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import com.neu.questionnairebackend.mapper.QuestionMapper;
import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.service.ChoicesService;
import com.neu.questionnairebackend.service.QuestionService;
import com.neu.questionnairebackend.service.impl.QuestionServiceImpl;
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
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuestionServiceTest {

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private ChoicesService choicesService;

    @Mock
    private ChoicesMapper choicesMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddQuestions() {
        List<AddSurveyRequest.QuestionRequest> questions = new ArrayList<>();
        AddSurveyRequest.QuestionRequest questionRequest = new AddSurveyRequest.QuestionRequest();
        questionRequest.setQuestionDescription("Test Question");
        questionRequest.setQuestionType(1);
        List<AddSurveyRequest.QuestionRequest.OptionRequest> options = new ArrayList<>();
        questionRequest.setOptions(options);
        questions.add(questionRequest);
        int surveyId = 1;
        Question question = new Question();
        question.setSurveyId(surveyId);
        question.setQuestionDescription("Test Question");
        question.setQuestionType(1);
        when(questionMapper.insert(question)).thenReturn(1);
        boolean result = questionService.addQuestions(questions, surveyId);
        Assertions.assertTrue(result);
        verify(questionMapper, times(1)).insert(question);
        verify(choicesService, times(1)).createChoices(options, question.getId());
    }

    @Test
    void testGetChoices() {
        int id = 1;
        QueryWrapper<Choices> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("questionId", id);
        List<Choices> choicesList = new ArrayList<>();
        when(choicesMapper.selectList(queryWrapper)).thenReturn(choicesList);
        List<Choices> result = questionService.getChoices(id);
        assertEquals(choicesList, result);
        verify(choicesMapper, times(1)).selectList(queryWrapper);
    }

}