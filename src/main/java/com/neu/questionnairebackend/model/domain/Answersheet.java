package com.neu.questionnairebackend.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName answersheet
 */
@TableName(value ="answersheet")
@Data
public class Answersheet implements Serializable {
    /**
     * 回答id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号名
     */
    private String userAccount;

    /**
     * 问题Id
     */
    private Integer questionID;

    /**
     * 问卷Id
     */
    private Integer surveyId;

    /**
     * 选择的选项
     */
    private String selectChoices;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}