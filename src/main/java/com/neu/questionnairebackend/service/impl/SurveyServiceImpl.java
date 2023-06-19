package com.neu.questionnairebackend.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.domain.request.ModifySurveyRequest;
import com.neu.questionnairebackend.service.SurveyService;
import com.neu.questionnairebackend.mapper.SurveyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author HUANG
* @description 针对表【survey】的数据库操作Service实现
* @createDate 2023-06-06 15:58:09
*/
@Service
public class SurveyServiceImpl extends ServiceImpl<SurveyMapper, Survey>
    implements SurveyService{

    @Resource
    private SurveyMapper surveyMapper;
    @Override
    public boolean updateFrontSurvey(ModifySurveyRequest survey) {
        if(survey.getTotalTimes()<=0)return false;
        if(survey != null) {
            int id = survey.getId();
            Survey frontSurvey = surveyMapper.selectById(id);
            frontSurvey.setSurveyType(survey.getSurveyType());
            frontSurvey.setSurveyName(survey.getSurveyName());
            frontSurvey.setDescription(survey.getDescription());
            frontSurvey.setSurveyStatus(0);
            frontSurvey.setTotalTimes(survey.getTotalTimes());
            frontSurvey.setUpdateTime(new Date());
            return this.updateById(frontSurvey);
        }
        return false;
    }
}




