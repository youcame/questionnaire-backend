package com.neu.questionnairebackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * 题目id
     */
    @TableId(type = IdType.AUTO)
    private Object id;

    /**
     * 对应的问卷id
     */
    private Integer surveyId;

    /**
     * 问题描述
     */
    private String description;

    /**
     * 类型 0-单选，1-多选
     */
    private Integer questionType;

    /**
     * 一共被回答的次数，用于统计
     */
    private Date totalTimes;

    /**
     * 用户的答案
     */
    private String ans;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}