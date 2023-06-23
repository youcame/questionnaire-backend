package com.neu.questionnairebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.mapper.UserMapper;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.model.dto.ModifyUserRequest;
import com.neu.questionnairebackend.service.UserService;
import com.neu.questionnairebackend.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserRegister_Success() {
        // Arrange
        String userAccount = "testUser";
        String password = "password";
        String checkPassword = "password";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        when(userMapper.selectCount(queryWrapper)).thenReturn(0L);
        when(userMapper.selectCount(any(QueryWrapper.class))).thenReturn(0L);

        long userId = userService.userRegister(userAccount, password, checkPassword);
        assertEquals(1L, userId);
        verify(userMapper, times(1)).selectCount(queryWrapper);
        verify(userMapper, times(1)).selectCount(any(QueryWrapper.class));
        verify(userMapper, times(1)).insert(any(User.class));
    }

    @Test
    void testUserRegister_UserAccountAlreadyExists() {
        String userAccount = "testUser";
        String password = "password";
        String checkPassword = "password";
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        when(userMapper.selectCount(queryWrapper)).thenReturn(1L);

        long userId = userService.userRegister(userAccount, password, checkPassword);
        assertEquals(-3L, userId);
        verify(userMapper, times(1)).selectCount(queryWrapper);
        verify(userMapper, never()).selectCount(any(QueryWrapper.class));
        verify(userMapper, never()).insert(any(User.class));
    }

    @Test
    void testUserRegister_PasswordMismatch() {
        // Arrange
        String userAccount = "testUser";
        String password = "password";
        String checkPassword = "mismatch";

        // Act
        long userId = userService.userRegister(userAccount, password, checkPassword);

        // Assert
        assertEquals(-2L, userId);
        verify(userMapper, never()).selectCount(any(QueryWrapper.class));
        verify(userMapper, never()).insert(any(User.class));
    }

    @Test
    void testUserLogin_Success() {
        String userAccount = "testUser";
        String password = "password";
        HttpServletRequest request = mock(HttpServletRequest.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("password", "encryptedPassword");
        User user = new User();
        when(userMapper.selectOne(queryWrapper)).thenReturn(user);
        User result = userService.userLogin(userAccount, password, request);
        assertNotNull(result);
        verify(request, times(1)).getSession();
        verify(request.getSession(), times(1)).setAttribute(eq("USER_LOGIN_STATE"), any(User.class));
    }

    @Test
    void testUserLogin_InvalidCredentials() {
        String userAccount = "testUser";
        String password = "password";
        HttpServletRequest request = mock(HttpServletRequest.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("password", "encryptedPassword");
        when(userMapper.selectOne(queryWrapper)).thenReturn(null);
        User result = userService.userLogin(userAccount, password, request);
        assertNull(result);
        verify(request, never()).getSession();
        verify(request.getSession(), never()).setAttribute(eq("USER_LOGIN_STATE"), any(User.class));
    }

    @Test
    void testUpdateFrontUser_Success() {
        ModifyUserRequest user = new ModifyUserRequest();
        user.setId(1L);
        user.setUserRole(1);
        user.setAvatarUrl("avatar.jpg");
        user.setUserStatus(1);
        user.setEmail("test@test.com");
        user.setPhone("123456789");
        user.setUsername("testUser");
        user.setGender(0);
        User existingUser = new User();
        when(userMapper.selectById(user.getId())).thenReturn(existingUser);
        when(userMapper.updateById(existingUser)).thenReturn(1);
        boolean result = userService.updateFrontUser(user);

        assertTrue(result);
        verify(userMapper, times(1)).selectById(user.getId());
        verify(userMapper, times(1)).updateById(existingUser);
        assertEquals(user.getUserRole(), existingUser.getUserRole());
        assertEquals(user.getAvatarUrl(), existingUser.getAvatarUrl());
        assertEquals(user.getUserStatus(), existingUser.getUserStatus());
        assertEquals(user.getEmail(), existingUser.getEmail());
        assertEquals(user.getPhone(), existingUser.getPhone());
        assertEquals(user.getUsername(), existingUser.getUsername());
        assertEquals(user.getGender(), existingUser.getGender());
    }

    @Test
    void testUpdateFrontUser_UserNotFound() {
        ModifyUserRequest user = new ModifyUserRequest();
        user.setId(1L);
        when(userMapper.selectById(user.getId())).thenReturn(null);
        boolean result = userService.updateFrontUser(user);
        assertFalse(result);
        verify(userMapper, times(1)).selectById(user.getId());
        verify(userMapper, never()).updateById(any(User.class));
    }

    @Test
    void testUpdateFrontUser_UserUpdateFailed() {
        ModifyUserRequest user = new ModifyUserRequest();
        user.setId(1L);
        User existingUser = new User();
        when(userMapper.selectById(user.getId())).thenReturn(existingUser);
        when(userMapper.updateById(existingUser)).thenReturn(0);
        boolean result = userService.updateFrontUser(user);
        assertFalse(result);
        verify(userMapper, times(1)).selectById(user.getId());
        verify(userMapper, times(1)).updateById(existingUser);
    }

    @Test
    void testUserLogout() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        int result = userService.userLogout(request);
        assertEquals(1, result);
        verify(request, times(1)).removeAttribute("USER_LOGIN_STATE");
    }


}
