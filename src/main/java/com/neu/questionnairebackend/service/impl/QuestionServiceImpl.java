package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.service.ChoicesService;
import com.neu.questionnairebackend.service.OptionService;
import com.neu.questionnairebackend.service.QuestionService;
import com.neu.questionnairebackend.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author HUANG
* @description 针对表【question】的数据库操作Service实现
* @createDate 2023-06-20 10:47:58
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private ChoicesService choicesService;

    @Override
    public boolean addQuestions(List<AddSurveyRequest.QuestionRequest> questions, int surveyId) {
        for(AddSurveyRequest.QuestionRequest questionRequest: questions){
            Question question = new Question();
            question.setSurveyId(surveyId);
            question.setQuestionDescription(questionRequest
                    .getQuestionDescription());
            questionMapper.insert(question);
            Integer id = question.getId();
            choicesService.createChoices(questionRequest.getOptions(), id);
        }
        return true;
    }
}




