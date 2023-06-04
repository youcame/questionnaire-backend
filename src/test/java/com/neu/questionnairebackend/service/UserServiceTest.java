package com.neu.questionnairebackend.service;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.mapper.UserMapper;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.model.domain.request.ModifyUserRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@SpringBootTest
public class UserServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @Resource
    private UserService userService;
    @Resource
    private UserMapper userMapper;

    /**
     * 添加用户
     */
    @Test
    public void testAddUser(){
        User user = new User();
        user.setId(0L);
        user.setUsername("");
        user.setPassword("");
        user.setUserAccount("");
        user.setAvatarUrl("");
        user.setPhone("");
        user.setEmail("");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean result = userService.save(user);
        log.info("Result of userQuery: {}", result);
        Assertions.assertTrue(result);
    }
    /**
     * 用户注册
     */
    @Test
    void userRegister() {
        String userAccount = "commonUser";
        String password = "12345678";
        String checkPassword = "12345678";
        long l = userService.userRegister(userAccount, password, checkPassword);
        log.info("Result of userRegister: true");
        Assertions.assertFalse(l==-1);
    }

    /**
     * 用户更新
     */
    @Test
    void userUpdate(){
        ModifyUserRequest user = new ModifyUserRequest();
        user.setId(4L);
        user.setUsername("冰雪灬独舞");
        user.setAvatarUrl("123");
        user.setGender(0);
        user.setPhone("123");
        user.setEmail("123");
        user.setUserStatus(0);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",4L);
        User user1 =  userMapper.selectOne(queryWrapper);
        userService.updateFrontUser(user);
        Assertions.assertEquals("123",user1.getPhone());
    }

    @Test
    void deleteUser(){
        User user = new User();
        user.setId(0L);
        Assertions.assertTrue(userService.removeById(0L));
    }
}