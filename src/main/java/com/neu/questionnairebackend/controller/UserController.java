package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.model.domain.request.UserLoginRequest;
import com.neu.questionnairebackend.model.domain.request.UserRegisterRequest;
import com.neu.questionnairebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.neu.questionnairebackend.constant.UserConstant.ADMIN_ROLE;
import static com.neu.questionnairebackend.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author HUANG
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        return userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getPassword(), userRegisterRequest.getCheckPassword());
    }

    @GetMapping("/currentUser")
    public User getCurrentUser(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        if(user == null){
            return  null;
        }
        else {
            long id =user.getId();
            User currentUser = userService.getById(id);
            return currentUser;
        }

    }
    @PostMapping("/login")
    public User userRegister(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        return userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getPassword(), request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        if(!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        return userService.list(queryWrapper);
    }

    //todo:尚未完成测试，传入参数存在问题。
    @PostMapping("/delete")
    public boolean deleteUser(long id, HttpServletRequest request) {
        if(!isAdmin(request)){
            return false;
        }
        if (id < 0) {
            return false;
        } else return userService.removeById(id);
    }
    private boolean isAdmin(HttpServletRequest request){
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        return ADMIN_ROLE == user.getUserRole();
    }
}
