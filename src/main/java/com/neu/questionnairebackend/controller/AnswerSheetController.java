package com.neu.questionnairebackend.controller;

import com.neu.questionnairebackend.common.BaseResponse;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.common.ResultUtil;
import com.neu.questionnairebackend.exception.BusinessException;
import com.neu.questionnairebackend.mapper.AnswersheetMapper;
import com.neu.questionnairebackend.model.domain.Answersheet;
import com.neu.questionnairebackend.model.dto.AnswerRequest;
import com.neu.questionnairebackend.service.AnswersheetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/answer")
public class AnswerSheetController {

    @Resource
    AnswersheetService answersheetService;
    @Resource
    AnswersheetMapper answersheetMapper;

    /**
     *
     * @param id 为survey的Id
     * @param userId
     * @return  如果只有（answer）id，则只返回所有回答过的问卷列表信息；如果有（answer）Id与userId与
     *
     */
    @GetMapping("/getAnswerById")
    public BaseResponse<AnswerRequest> getAnswerById(int id, Integer userId) {
        if (id <= 0) throw new BusinessException(ErrorCode.PARAM_ERROR);
        else if (userId == null || userId <= 0 ){
            return ResultUtil.success(answersheetService.getAnswerById(id));
        }
        else {
            AnswerRequest answerById = answersheetService.getAnswerById(id);
            return ResultUtil.success(answerById);
        }
    }

    @GetMapping("/getAnswers")
    public BaseResponse<List<Answersheet>> getAnswers(Integer surveyId){
        List<Answersheet> allAnswers = answersheetService.getAllAnswers(surveyId);
        return ResultUtil.success(allAnswers);
    }

}
