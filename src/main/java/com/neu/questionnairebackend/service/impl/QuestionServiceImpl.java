package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.model.domain.Option;
import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.service.OptionService;
import com.neu.questionnairebackend.service.QuestionService;
import com.neu.questionnairebackend.mapper.QuestionMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import java.util.List;

/**
* @author HUANG
* @description 针对表【question】的数据库操作Service实现
* @createDate 2023-06-07 16:33:36
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{
    @Resource
    private OptionService optionService;
    @Resource
    private QuestionMapper questionMapper;

    @Override
    public boolean addQuestions(List<AddSurveyRequest.QuestionRequest> questions, int surveyId) {
        int id = 1;
        for(AddSurveyRequest.QuestionRequest questionRequest: questions){
            Question question = new Question();
            question.setId(id);
            question.setSurveyId(surveyId);
            question.setDescription(questionRequest
                    .getQuestionDescription());
            questionMapper.insert(question);
            optionService.createOptions(questionRequest.getOptions(), id);
            id++;
        }
        return true;
    }
}




