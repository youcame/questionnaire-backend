package com.neu.questionnairebackend.service;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.mapper.UserMapper;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.model.domain.request.ModifyUserRequest;
import org.junit.jupiter.api.Test;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
public class UserServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    Logger log = LoggerFactory.getLogger(UserServiceTest.class);
    /**
     * 添加用户
     */
    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("");
        user.setPassword("");
        user.setUserAccount("");
        user.setAvatarUrl("");
        user.setPhone("");
        user.setEmail("");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean result = userService.save(user);
        log.info("搜索的结果为: {}", result);
    }
    /**
     * 用户注册
     */
    @Test
    void userRegister() {
        String userAccount = "commonUsers";
        String password = "12345678";
        String checkPassword = "12345678";
        long l = userService.userRegister(userAccount, password, checkPassword);
        log.info("注册得到的用户id为: {}", l);
    }

    /**
     * 用户更新
     */
    @Test
    void userUpdate(){
        ModifyUserRequest user = new ModifyUserRequest();
        user.setUsername("冰雪灬独舞");
        user.setAvatarUrl("123");
        user.setGender(0);
        user.setPhone("123");
        user.setEmail("123");
        user.setUserStatus(0);
        user.setId(4L);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",4L);
        User user1 =  userMapper.selectOne(queryWrapper);
        boolean b = userService.updateFrontUser(user);
        log.info("更新的结果为: {}", b);
    }

    @Test
    void deleteUser(){
        User user = new User();
        user.setId(5L);
        boolean b = userService.removeById(0L);
        log.info("删除的结果为: {}", b);
    }
}