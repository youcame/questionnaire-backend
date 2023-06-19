package com.neu.questionnairebackend.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddSurveyRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String surveyName;
    private String surveyDescription;
    private String surveyType;
    private String relate;
    private List<QuestionRequest> addQuestion;
    @Data
    public static class QuestionRequest implements Serializable {
        private static final long serialVersionUID = 1L;
        private String questionDescription;
        private List<OptionRequest> options;
        @Data
        public static class OptionRequest implements Serializable {
            private static final long serialVersionUID = 1L;
            private String option;
            private String destination;
        }
    }
}
