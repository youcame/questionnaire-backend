package com.neu.questionnairebackend.model.domain.request;

import lombok.Data;

@Data
public class ModifySurveyRequest {
    private static final long serialVersionUID = 1L;
    private int id;
    private String surveyName;
    private String description;
    private int surveyType;
    private int surveyStatus;
    private int totalTimes;
    private String canFinishTime;
}
