package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.domain.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;

import java.util.List;

/**
* @author HUANG
* @description 针对表【question】的数据库操作Service
* @createDate 2023-06-20 10:47:58
*/
public interface QuestionService extends IService<Question> {
    boolean addQuestions(List<AddSurveyRequest.QuestionRequest> questions, int surveyId);
    public List<Choices> getChoices(int id);
}
