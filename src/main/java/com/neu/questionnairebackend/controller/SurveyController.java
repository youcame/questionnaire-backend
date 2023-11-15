package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.common.BaseResponse;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.common.ResultUtil;
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
    public BaseResponse<List<Survey>> getSurveyList(String surveyName,String description, String surveyType, Integer projectId,HttpServletRequest request){
        QueryWrapper<Survey> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(surveyName)){
            queryWrapper.like("surveyName", surveyName);
        }
        if(StringUtils.isNotBlank(description)){
            queryWrapper.like("description",description);
        }
        if(StringUtils.isNotBlank(surveyType)){
            queryWrapper.like("surveyType", surveyType);
        }
        if(projectId!=null){
            queryWrapper.like("projectId",projectId);
        }
        List<Survey> list = surveyService.list(queryWrapper);
        return ResultUtil.success(list);
    }

    /**
     *
     * @param id
     * @param request
     * @return 删除的结果,不注释的内容为删除所有结果；注释的内容为逻辑删除，保留问卷结果
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteSurvey(@RequestBody Integer id, HttpServletRequest request) {
        if(!UserAuthority.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "id为空");
        } else {
            boolean b = surveyService.deleteById(id);
            return ResultUtil.success(b);
        }
    }

    /**
     *
     * @param survey
     * @param request
     * @return 更新的结果
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateSurvey(@RequestBody ModifySurveyRequest survey, HttpServletRequest request){
        if(survey == null || !UserAuthority.isAdmin(request)){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"找不到问卷或者没权限");
        }
        else{
            boolean b = surveyService.updateFrontSurvey(survey);
            return ResultUtil.success(b);
        }
    }

    @PostMapping("/create")
    public BaseResponse<Boolean> addSurvey(@RequestBody AddSurveyRequest addSurveyRequest, Integer id, HttpServletRequest request){
        if(addSurveyRequest!=null && id==null){
            surveyService.addSurvey(addSurveyRequest);
        }
        if(addSurveyRequest!=null && id!=null){
            surveyService.updateSurvey(addSurveyRequest, id);
        }
        return ResultUtil.success(true);
    }

    @GetMapping("/getSurveyById")
    public BaseResponse<AddSurveyRequest> getSurveyById( Integer id){
        if(id<=0)throw new BusinessException(ErrorCode.NO_AUTH);
        else {
            AddSurveyRequest surveyById = surveyService.getSurveyById(id);
            return ResultUtil.success(surveyById);
        }
    }

}
