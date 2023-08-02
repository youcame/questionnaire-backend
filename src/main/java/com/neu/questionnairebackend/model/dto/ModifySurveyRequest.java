package com.neu.questionnairebackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifySurveyRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String surveyName;
    private String description;
    private int surveyType;
    private int surveyStatus;
    private int totalTimes;
    private String canFinishTime;
}
