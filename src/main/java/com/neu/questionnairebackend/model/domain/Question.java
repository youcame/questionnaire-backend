package com.neu.questionnairebackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import lombok.Data;

import javax.annotation.Resource;

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
    private Integer id;

    /**
     * 对应的问卷id
     */
    private Integer surveyId;

    /**
     * 问题描述
     */
    private String questionDescription;

    /**
     * 类型 0-单选，1-多选
     */
    private Integer questionType;

    /**
     * 一共被回答的次数，用于统计
     */
    private Integer totalTimes;

    /**
     * 用户的答案
     */
    private String ans;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



}