package com.neu.questionnairebackend.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName survey
 */
@TableName(value ="survey")
@Data
public class Survey implements Serializable {
    /**
     * 问卷id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 问卷类型 0-正常问卷 1-限时问卷 2-限次问卷 3-面向群众
     */
    private Integer surveyType;

    /**
     * 问卷名称
     */
    private String surveyName;

    /**
     * 问卷描述
     */
    private String description;

    /**
     * 完成问卷用户的id
     */
    private Long finishUserId;

    /**
     * 问卷状态，0-未发布，1-发布进行中，2-已完成且结束，3-已超时未完成
     */
    private Integer surveyStatus;

    /**
     * 查看问卷的详细地址
     */
    private String url;

    /**
     * 能完成的时间（分钟）
     */
    private String canFinishTime;

    /**
     * 一共能够完成几次
     */
    private Integer totalTimes;

    /**
     * 现在完成了几次
     */
    private Integer nowTimes;

    /**
     * 能完成的用户id
     */
    private Long canFinishUserId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}