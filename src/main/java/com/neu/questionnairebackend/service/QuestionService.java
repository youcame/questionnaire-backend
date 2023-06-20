package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author HUANG
* @description 针对表【question】的数据库操作Service
* @createDate 2023-06-07 16:33:36
*/
public interface QuestionService extends IService<Question> {
    boolean addQuestions(List<AddSurveyRequest.QuestionRequest> questions, int surveyId);
}
