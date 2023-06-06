package com.neu.questionnairebackend.service;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.mapper.SurveyMapper;
import com.neu.questionnairebackend.model.domain.Survey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SurveyServiceTest {
    @Resource
    private SurveyService surveyService;
    @Resource
    private SurveyMapper surveyMapper;

    @Test
    void addSurvey(){
        Survey survey = new Survey();
        survey.setId(1);
        survey.setSurveyType(0);
        survey.setSurveyName("123");
        survey.setDescription("123");
        survey.setFinishUserId(1L);
        survey.setSurveyStatus(0);
        survey.setUrl("123");
        survey.setCanFinishTime("12");
        survey.setTotalTimes(1);
        survey.setNowTimes(0);
        survey.setCanFinishUserId(0L);
        survey.setCreateTime(new Date());
        survey.setUpdateTime(new Date());
        survey.setDeleteTime(new Date());
        survey.setIsDelete(0);
        surveyMapper.insert(survey);
        QueryWrapper<Survey> surveyQueryWrapper = new QueryWrapper<>();
        Long count = surveyMapper.selectCount(surveyQueryWrapper);
        Assertions.assertEquals(count,1);

    }

}