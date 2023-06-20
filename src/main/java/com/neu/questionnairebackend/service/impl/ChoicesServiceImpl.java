package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.mapper.OptionMapper;
import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.domain.Option;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.service.ChoicesService;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author HUANG
* @description 针对表【choices】的数据库操作Service实现
* @createDate 2023-06-20 11:22:41
*/
@Service
public class ChoicesServiceImpl extends ServiceImpl<ChoicesMapper, Choices>
    implements ChoicesService{
    @Resource
    private ChoicesMapper choicesMapper;

    @Override
    public boolean createChoices(List<AddSurveyRequest.QuestionRequest.OptionRequest> optionRequests, int id) {
        for(AddSurveyRequest.QuestionRequest.OptionRequest optionRequest: optionRequests){
            Choices choices = new Choices();
            choices.setQuestionId(id);
            choices.setDescription(optionRequest.getDestination());
            if(optionRequest.getDestination()!=null) {
                choices.setDestination(Integer.parseInt(optionRequest.getDestination()));
            }
            choicesMapper.insert(choices);
        }
        return true;
    }
}




