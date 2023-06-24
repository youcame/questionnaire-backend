package com.neu.questionnairebackend.controller;

import com.neu.questionnairebackend.mapper.AnswersheetMapper;
import com.neu.questionnairebackend.model.dto.AnswerRequest;
import com.neu.questionnairebackend.service.AnswersheetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
        return answersheetService.getAnswerById(id);
    }
}
