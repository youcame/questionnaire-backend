package com.neu.questionnairebackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import lombok.Data;

import javax.annotation.Resource;

/**
 * 
 * @TableName choices
 */
@TableName(value ="choices")
@Data
public class Choices implements Serializable {
    /**
     * 选项id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 对应着题目表
     */
    private Integer questionId;

    /**
     * 0-未选，1-已选
     */
    private Integer isChoose;

    /**
     * 选项描述
     */
    private String description;

    /**
     * 选择的次数，用于分析
     */
    private Integer totalTimes;

    /**
     * 用于题目的跳转
     */
    private Integer destination;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}