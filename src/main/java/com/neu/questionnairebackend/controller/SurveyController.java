package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.exception.BusinessException;
import com.neu.questionnairebackend.mapper.SurveyMapper;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.dto.AddSurveyRequest;
import com.neu.questionnairebackend.model.dto.ModifySurveyRequest;
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

    /**
     *
     * @param surveyName
     * @param surveyType
     * @param request
     * @return  筛选之后的用户列表
     */
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

    /**
     *
     * @param id
     * @param request
     * @return 删除的结果
     */
    @PostMapping("/delete")
    public boolean deleteSurvey(@RequestBody Integer id, HttpServletRequest request) {
        if(!UserAuthority.isAdmin(request)){
            return false;
        }
        if (id < 0) {
            return false;
        } else return surveyService.removeById(id);
    }

    /**
     *
     * @param survey
     * @param request
     * @return 更新的结果
     */
    @PostMapping("/update")
    public boolean updateSurvey(@RequestBody ModifySurveyRequest survey, HttpServletRequest request){
        if(survey == null || !UserAuthority.isAdmin(request)){
            return false;
        }
        else{
            return surveyService.updateFrontSurvey(survey);
        }
    }

    @PostMapping("/create")
    public boolean addSurvey(@RequestBody AddSurveyRequest addSurveyRequest, HttpServletRequest request){
        if(!UserAuthority.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if(addSurveyRequest!=null){
            surveyService.addSurvey(addSurveyRequest, request);
        }
        return true;
    }

    @GetMapping("/getSurveyById")
    public AddSurveyRequest getSurveyById( Integer id){
        if(id<=0)return null;
        else return surveyService.getSurveyById(id);
    }

}
