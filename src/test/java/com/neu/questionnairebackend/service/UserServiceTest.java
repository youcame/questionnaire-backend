package com.neu.questionnairebackend.service;
import java.util.Date;

import com.neu.questionnairebackend.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

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
        Assertions.assertTrue(result);
    }

    @Test
    public void testAdd(){
        Assertions.assertTrue(1 == 1);
    }

    @Test
    void userRegister() {
        String userAccount = "admin";
        String password = "12345678";
        String checkPassword = "12345678";
        long l = userService.userRegister(userAccount, password, checkPassword);
        Assertions.assertFalse(l==-1);
    }
}