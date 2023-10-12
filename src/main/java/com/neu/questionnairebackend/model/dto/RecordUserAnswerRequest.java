package com.neu.questionnairebackend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecordUserAnswerRequest {
    private Long id;
    private String userAccount;
    private Integer surveyId;
    private List<Questions> questions;
    @Data
    public static class Questions{
        private Integer id;
        private List<Integer> ans;
    }
}
