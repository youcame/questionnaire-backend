package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.model.domain.Survey;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.model.dto.ModifySurveyRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
* @author HUANG
* @description 针对表【survey】的数据库操作Service
* @createDate 2023-06-06 15:58:09
*/
public interface SurveyService extends IService<Survey> {

    boolean updateFrontSurvey(ModifySurveyRequest survey);

    boolean addSurvey(AddSurveyRequest addSurveyRequest, HttpServletRequest request);

    AddSurveyRequest getSurveyById(int id);

    boolean deleteById(int id);

    boolean updateSurvey(AddSurveyRequest addSurveyRequest, int id, HttpServletRequest request);

    Survey getSurveyFromRequest(AddSurveyRequest addSurveyRequest);
    List<Question> getQuestions(int surveyId);
}
