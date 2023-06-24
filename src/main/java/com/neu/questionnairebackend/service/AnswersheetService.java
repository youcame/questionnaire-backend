package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.Answersheet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.model.dto.AnswerRequest;

import java.util.List;

/**
* @author HUANG
* @description 针对表【answersheet】的数据库操作Service
* @createDate 2023-06-24 13:37:16
*/
public interface AnswersheetService extends IService<Answersheet> {
    AnswerRequest getAnswerById(int id);
    List<Answersheet> getAllAnswers();
}
