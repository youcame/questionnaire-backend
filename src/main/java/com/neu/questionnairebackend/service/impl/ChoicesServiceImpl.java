package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.model.domain.Choices;
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


    /**
     *
     * @param optionRequests 选项的dto类
     * @param id 问题的id
     * @return  创建问题对应的选项
     */
    @Override
    public boolean createChoices(List<AddSurveyRequest.QuestionRequest.OptionRequest> optionRequests, int id) {
        for(AddSurveyRequest.QuestionRequest.OptionRequest optionRequest: optionRequests){
            Choices choices = new Choices();
            choices.setQuestionId(id);
            choices.setDescription(optionRequest.getOption());
            if(optionRequest.getDestination()!=null && !optionRequest.getDestination().isEmpty()) {
                choices.setDestination(Integer.parseInt(optionRequest.getDestination()));
            }
            else choices.setDestination(0);
            choicesMapper.insert(choices);
        }
        return true;
    }
}




