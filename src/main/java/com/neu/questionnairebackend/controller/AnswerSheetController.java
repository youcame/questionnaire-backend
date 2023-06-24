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

    @GetMapping("/getAnswerById")
    public AnswerRequest getAnswerById(int id){
        if(id<=0)return null;
        return answersheetService.getAnswerById(id);
    }

    @GetMapping("/getAnswers")
    public List<Answersheet> getAnswers(){
        return answersheetService.getAllAnswers();
    }
}
