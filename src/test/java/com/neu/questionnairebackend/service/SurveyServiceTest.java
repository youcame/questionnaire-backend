package com.neu.questionnairebackend.service;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.mapper.SurveyMapper;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.domain.request.ModifySurveyRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SurveyServiceTest {
    @Resource
    private SurveyService surveyService;
    @Resource
    private SurveyMapper surveyMapper;

    Logger log = LoggerFactory.getLogger(SurveyServiceTest.class);

    @Test
    void addSurvey(){
        Survey survey = new Survey();
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
        int insert = surveyMapper.insert(survey);
        log.info("插入问卷的id为:{}", insert);
    }

    @Test
    void updateFrontUser(){
        ModifySurveyRequest survey = new ModifySurveyRequest();
        survey.setId(1);
        survey.setSurveyType(0);
        survey.setSurveyName("123");
        survey.setDescription("123");
        survey.setSurveyStatus(0);
        survey.setCanFinishTime("12");
        survey.setTotalTimes(1);
        boolean b = surveyService.updateFrontSurvey(survey);
        log.info("更新问卷的结果为:{}", b);
    }

}