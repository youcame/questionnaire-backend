package com.neu.questionnairebackend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录时请求
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -5373315565461769593L;
    private String userAccount;
    private String password;
}
