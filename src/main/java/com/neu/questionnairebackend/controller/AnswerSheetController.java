package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public AnswerRequest getAnswerById(int id,Integer userId) {
        if (id <= 0) return null;
        else if (userId == null || userId <= 0 ){
            return answersheetService.getAnswerById(id);
        }
        else {
            return answersheetService.getAnswerById(id);
        }
    }

    @GetMapping("/getAnswers")
    public List<Answersheet> getAnswers(Integer surveyId){
        return answersheetService.getAllAnswers(surveyId);
    }

}
