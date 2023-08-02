package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.common.BaseResponse;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.common.ResultUtil;
import com.neu.questionnairebackend.exception.BusinessException;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.model.dto.ModifyUserRequest;
import com.neu.questionnairebackend.model.dto.UserLoginRequest;
import com.neu.questionnairebackend.model.dto.UserRegisterRequest;
import com.neu.questionnairebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "请求为空");
        }
        long result = userService.userRegister(userRegisterRequest.getUserAccount(), userRegisterRequest.getPassword(), userRegisterRequest.getCheckPassword());
        return ResultUtil.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        User user = (User)request.getSession().getAttribute(USER_LOGIN_STATE);
        if(user == null){
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        else {
            long id =user.getId();
            User currentUser = userService.getById(id);
            return ResultUtil.success(userService.getSafeUser(currentUser));
        }

    }
    @PostMapping("/login")
    public BaseResponse<User> userRegister(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        return ResultUtil.success(userService.userLogin(userLoginRequest.getUserAccount(), userLoginRequest.getPassword(), request));
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN);
        }
        return ResultUtil.success(userService.userLogout(request));
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String userAccount, String username, HttpServletRequest request) {
        if(!UserAuthority.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.like("userAccount", userAccount);
        }
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        return ResultUtil.success(userService.list(queryWrapper));
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody Long id, HttpServletRequest request) {
        if(!UserAuthority.isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"删除的id不正确");
        } else return ResultUtil.success(userService.removeById(id));
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody ModifyUserRequest user, HttpServletRequest request){
        if(user == null){
            throw new BusinessException(ErrorCode.PARAM_ERROR,"更新用户为空");
        }
        else{
            Boolean b = userService.updateFrontUser(user);
            return ResultUtil.success(b);
        }
    }

}
