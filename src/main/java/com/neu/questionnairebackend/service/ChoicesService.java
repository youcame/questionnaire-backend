package com.neu.questionnairebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;

import java.util.List;

/**
* @author HUANG
* @description 针对表【choices】的数据库操作Service
* @createDate 2023-06-20 11:22:41
*/
public interface ChoicesService extends IService<Choices> {
    boolean createChoices(List<AddSurveyRequest.QuestionRequest.OptionRequest> optionRequests, int id);
}
