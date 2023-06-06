package com.neu.questionnairebackend.Authority;

import com.neu.questionnairebackend.model.domain.User;

import javax.servlet.http.HttpServletRequest;

import static com.neu.questionnairebackend.constant.UserConstant.*;

public class UserAuthority {
    public static final boolean isAdmin(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        return ADMIN_ROLE == user.getUserRole();
    }

    public static final boolean isDefault(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        return DEFAULT_ROLE == user.getUserRole();
    }


}
