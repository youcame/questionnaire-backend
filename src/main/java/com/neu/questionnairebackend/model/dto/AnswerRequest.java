package com.neu.questionnairebackend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnswerRequest {
    private int id;
    private String surveyName;
    private String surveyDescription;
    private List<QuestionDTO> questions;

    @Data
    public static class QuestionDTO {
        private int id;
        private String questionDescription;
        private List<OptionDTO> options;
        private List<String> userAnswer; // 修改为 List<String>

        @Data
        public static class OptionDTO {
            private int id;
            private String label;
        }
    }
}