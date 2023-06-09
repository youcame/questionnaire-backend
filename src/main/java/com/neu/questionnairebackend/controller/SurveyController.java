package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.mapper.SurveyMapper;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.domain.request.ModifySurveyRequest;
import com.neu.questionnairebackend.model.domain.request.ModifyUserRequest;
import com.neu.questionnairebackend.service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 问卷接口
 */
@RestController
@Slf4j
@RequestMapping("/survey")
public class SurveyController {

    @Resource
    private SurveyService surveyService;
    @Resource
    private SurveyMapper surveyMapper;

    @GetMapping("/search")
    public List<Survey> getSurveyList(String surveyName, String surveyType, HttpServletRequest request){
        QueryWrapper<Survey> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(surveyName)){
            queryWrapper.like("surveyName", surveyName);
        }
        if(StringUtils.isNotBlank(surveyType)){
            queryWrapper.like("surveyType", surveyType);
        }
        return surveyService.list(queryWrapper);
    }

    @PostMapping("/delete")
    public boolean deleteSurvey(@RequestBody Integer id, HttpServletRequest request) {
        if(!UserAuthority.isAdmin(request)){
            return false;
        }
        if (id < 0) {
            return false;
        } else return surveyService.removeById(id);
    }

    @PostMapping("/update")
    public boolean updateUser(@RequestBody ModifySurveyRequest survey, HttpServletRequest request){
        if(survey == null || !UserAuthority.isAdmin(request)){
            return false;
        }
        else{
            return surveyService.updateFrontSurvey(survey);
        }
    }

}
