package com.neu.questionnairebackend.model.dto;

import lombok.Data;

import java.util.List;

/**
 * 传给前端的某个用户填写的问卷答案信息以及答案的统计功能
 */
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
        /**
         * 答案统计信息
         */
        private String statistics;
        @Data
        public static class OptionDTO {
            private int id;
            private String label;
        }
    }
}