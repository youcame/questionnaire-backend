package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.Survey;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.questionnairebackend.model.dto.ModifySurveyRequest;

/**
* @author HUANG
* @description 针对表【survey】的数据库操作Service
* @createDate 2023-06-06 15:58:09
*/
public interface SurveyService extends IService<Survey> {

    boolean updateFrontSurvey(ModifySurveyRequest survey);

    //boolean addSurvey()
}
