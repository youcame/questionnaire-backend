package com.neu.questionnairebackend.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.mapper.QuestionMapper;
import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.model.dto.ModifySurveyRequest;
import com.neu.questionnairebackend.service.QuestionService;
import com.neu.questionnairebackend.service.SurveyService;
import com.neu.questionnairebackend.mapper.SurveyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author HUANG
* @description 针对表【survey】的数据库操作Service实现
* @createDate 2023-06-06 15:58:09
*/
@Service
public class SurveyServiceImpl extends ServiceImpl<SurveyMapper, Survey>
    implements SurveyService{

    @Resource
    private SurveyMapper surveyMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionService questionService;
    @Override
    public boolean updateFrontSurvey(ModifySurveyRequest survey) {
        if(survey.getTotalTimes()<=0)return false;
        if(survey != null) {
            int id = survey.getId();
            Survey frontSurvey = surveyMapper.selectById(id);
            frontSurvey.setSurveyType(survey.getSurveyType());
            frontSurvey.setSurveyName(survey.getSurveyName());
            frontSurvey.setDescription(survey.getDescription());
            frontSurvey.setSurveyStatus(0);
            frontSurvey.setTotalTimes(survey.getTotalTimes());
            frontSurvey.setUpdateTime(new Date());
            return this.updateById(frontSurvey);
        }
        return false;
    }

    @Override
    public boolean addSurvey(AddSurveyRequest addSurveyRequest, HttpServletRequest httpServletRequest) {
        List<AddSurveyRequest.QuestionRequest> questionList = addSurveyRequest.getAddQuestion();
        Survey survey = new Survey();
        for(AddSurveyRequest.QuestionRequest question: questionList){
            if("timeLimit".equals(addSurveyRequest.getSurveyType())){
                survey.setSurveyType(1);
                survey.setCanFinishTime(addSurveyRequest.getRelate());
            }
            if("timesCanWrite".equals(addSurveyRequest.getSurveyType())){
                survey.setSurveyType(2);
                survey.setTotalTimes(Integer.parseInt(addSurveyRequest.getRelate()));
            }
            if("public".equals(addSurveyRequest.getSurveyType())){
                survey.setSurveyType(2);
            }
            survey.setSurveyStatus(0);
            survey.setSurveyName(addSurveyRequest.getSurveyName());
            survey.setDescription(addSurveyRequest.getSurveyDescription());
            survey.setUpdateTime(new Date());
            survey.setCreateTime(new Date());
            QueryWrapper<Survey> queryWrapper = new QueryWrapper<>();
            Long aLong = surveyMapper.selectCount(queryWrapper);
            survey.setId(aLong.intValue()+1);
            surveyMapper.insert(survey);
            questionService.addQuestions(questionList, survey.getId());
        }
        return false;
    }

//    @Override
//    public AddSurveyRequest getSurveyById(int id, HttpServletRequest request) {
//        AddSurveyRequest addSurveyRequest = new AddSurveyRequest();
//        Survey survey = surveyMapper.selectById(id);
//        addSurveyRequest.setSurveyDescription(survey.getDescription());
//        //todo:好像不行,缺一个relate
//        addSurveyRequest.setSurveyType(survey.getSurveyType().toString());
//        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("surveyId", id);
//        List<Question> questions = questionMapper.selectList(queryWrapper);
//
//
//        return null;
//    }

    @Override
    public AddSurveyRequest getSurveyById(int id) {
        AddSurveyRequest addSurveyRequest = new AddSurveyRequest();
        Survey survey = surveyMapper.selectById(id);
        addSurveyRequest.setSurveyName(survey.getSurveyName());
        addSurveyRequest.setSurveyDescription(survey.getDescription());
        addSurveyRequest.setSurveyType(survey.getSurveyType().toString());
        addSurveyRequest.setRelate(survey.getCanFinishTime()); // 假设`relate`对应的字段是`canFinishTime`
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("surveyId", id);
        List<Question> questions = questionMapper.selectList(queryWrapper);
        List<AddSurveyRequest.QuestionRequest> questionRequests = new ArrayList<>();
        for (Question question : questions) {
            AddSurveyRequest.QuestionRequest questionRequest = new AddSurveyRequest.QuestionRequest();
            questionRequest.setQuestionDescription(question.getQuestionDescription());
            List<AddSurveyRequest.QuestionRequest.OptionRequest> choicesRequests = new ArrayList<>();
            for (Choices choices : questionService.getChoices(question.getId())) {
                AddSurveyRequest.QuestionRequest.OptionRequest choicesRequest = new AddSurveyRequest.QuestionRequest.OptionRequest();
                choicesRequest.setOption(choices.getDescription());
                choicesRequest.setDestination(choices.getDestination().toString());
                choicesRequests.add(choicesRequest);
            }

            questionRequest.setOptions(choicesRequests);
            questionRequests.add(questionRequest);
        }

        addSurveyRequest.setAddQuestion(questionRequests);

        return addSurveyRequest;
    }
}




