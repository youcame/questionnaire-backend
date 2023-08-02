package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.service.ChoicesService;
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

    @Resource
    private ChoicesMapper choicesMapper;

    @Override
    public boolean addQuestions(List<AddSurveyRequest.QuestionRequest> questions, int surveyId) {
        for(AddSurveyRequest.QuestionRequest questionRequest: questions){
            Question question = this.getQuestionFromRequest(questionRequest, surveyId);
            questionMapper.insert(question);
            Integer id = question.getId();
            List<AddSurveyRequest.QuestionRequest.OptionRequest> options = questionRequest.getOptions();
            choicesService.createChoices(options, id);
        }
        return true;
    }


    @Override
    public List<Choices> getChoices(int id) {
        QueryWrapper<Choices> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("questionId", id);
        List<Choices> choicesList = choicesMapper.selectList(queryWrapper);
        return choicesList;
    }

    @Override
    public Question getQuestionFromRequest(AddSurveyRequest.QuestionRequest questionRequest, int surveyId) {
        Question question = new Question();
        question.setSurveyId(surveyId);
        question.setQuestionDescription(questionRequest
                .getQuestionDescription());
        question.setQuestionType(questionRequest.getQuestionType());
        return question;
    }


}




