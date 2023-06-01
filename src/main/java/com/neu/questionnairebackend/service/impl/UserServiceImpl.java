package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.model.domain.User;
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
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (password.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }
        //账户不能包含特殊字符
        String validPattern = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            return -1;
        }
        //检验两次密码是否相同
        if (!password.equals(checkPassword)) {
            return -1;
        }
        //检验是否有重复账户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
        System.out.println(encryptPassword);

        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPassword(encryptPassword);
        boolean result = this.save(user);
        if (!result) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, password)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (password.length() < 8) {
            return null;
        }
        //账户不能包含特殊字符
        String validPattern = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.find()) {
            return null;
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("password", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("login failed,userAccount cant match password");
            return null;
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
}




