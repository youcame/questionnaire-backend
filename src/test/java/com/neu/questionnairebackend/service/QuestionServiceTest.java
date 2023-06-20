package com.neu.questionnairebackend.service;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.mapper.QuestionMapper;
import com.neu.questionnairebackend.model.domain.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {
    @Resource
    private QuestionMapper questionMapper;

    @Test
    void addQuestion(){
        Question question = new Question();
        question.setId(3);
        question.setSurveyId(1);
        question.setQuestionDescription("");
        question.setQuestionType(0);
        question.setTotalTimes(1);
        question.setAns("");
        System.out.println(questionMapper.insert(question));
    }

    @Test
    void SearchQuestions(){
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("surveyId", 3);
        List<Question> questions = questionMapper.selectList(queryWrapper);
        System.out.println(questions);
    }
}