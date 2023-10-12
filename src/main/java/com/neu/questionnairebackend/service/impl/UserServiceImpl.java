package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.exception.BusinessException;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.model.dto.ModifyUserRequest;
import com.neu.questionnairebackend.service.UserService;
import com.neu.questionnairebackend.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.neu.questionnairebackend.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author HUANG
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-05-29 21:33:13
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    /**
     * 盐值
     */
    private static final String SALT = "abcd";
    @Resource
    private UserMapper userMapper;

    /**
     * @param userAccount
     * @param password
     * @param checkPassword
     * @return 新用户id
     */

    //todo：修改自定义异常
    @Override
    public long userRegister(String userAccount, String password, String checkPassword) {

        // 1.校验
        //校验输入是否合法
        if (StringUtils.isAnyBlank(userAccount, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数不能为空哦~");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户名至少需要四位哦~");
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "用户密码至少有8位哦~");
        }
        //账户不能包含特殊字符
        String validPattern = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号不能包含特殊字符哦~");
        }
        //检验两次密码是否相同
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"两次密码不相同");
        }
        //检验是否有重复账户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.ACCOUNT_SAME, "账户已经被注册过了~");
        }

        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
        System.out.println(encryptPassword);

        //3.插入数据
        queryWrapper = new QueryWrapper<>();
        count = userMapper.selectCount(queryWrapper);
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPassword(encryptPassword);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean result = this.save(user);
        if (!result) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"");
        }
        //todo:这里注册得到的id存在问题
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账号长度过短");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"密码长度过短");
        }
        //账户不能包含特殊字符
        String validPattern = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"账户包含特殊字符");
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户为空");
        }
        User safeUser = this.getSafeUser(user);
        //记录登录态,传入的是一个user数据！！！
        request.getSession().setAttribute(USER_LOGIN_STATE,safeUser);
        return safeUser;
    }

    @Override
    public User getSafeUser(User user){
        if(user == null){
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(0);
        safeUser.setCreateTime(new Date());
        safeUser.setUserRole(user.getUserRole());
        return safeUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    @Override
    public boolean updateFrontUser(ModifyUserRequest user) {
        long id = user.getId();
        User changedUser = this.getById(id);
        changedUser.setUserRole(user.getUserRole());
        changedUser.setAvatarUrl(user.getAvatarUrl());
        changedUser.setUserStatus(user.getUserStatus());
        changedUser.setEmail(user.getEmail());
        changedUser.setPhone(user.getPhone());
        changedUser.setUsername(user.getUsername());
        changedUser.setGender(user.getGender());
        return this.updateById(changedUser);
    }


}




