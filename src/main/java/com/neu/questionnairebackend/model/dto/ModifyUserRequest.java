package com.neu.questionnairebackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModifyUserRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String username;
    private String phone;
    private String email;
    private int userStatus;
    private int userRole;
    private int gender;
    private String avatarUrl;
}
