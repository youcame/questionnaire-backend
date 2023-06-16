package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.controller.UserController;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.model.domain.request.ModifyUserRequest;
import com.neu.questionnairebackend.model.domain.request.UserLoginRequest;
import com.neu.questionnairebackend.model.domain.request.UserRegisterRequest;
import com.neu.questionnairebackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserRegister() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        // 设置userRegisterRequest的属性值

        when(userService.userRegister(any(), any(), any())).thenReturn(12345L);

        Long result = userController.userRegister(userRegisterRequest);

        assertEquals(Long.valueOf(12345), result);
    }

    @Test
    public void testGetCurrentUser_withValidSession() {
        User user = new User();
        // 设置user的属性值

        when(request.getSession().getAttribute(any())).thenReturn(user);
        when(userService.getById(any())).thenReturn(user);
        when(userService.getSafeUser(any())).thenReturn(user);

        User result = userController.getCurrentUser(request);

        assertEquals(user, result);
    }

    @Test
    public void testGetCurrentUser_withInvalidSession() {
        when(request.getSession().getAttribute(any())).thenReturn(null);

        User result = userController.getCurrentUser(request);

        assertNull(result);
    }

    @Test
    public void testUserLogin() {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        // 设置userLoginRequest的属性值

        User user = new User();
        // 设置user的属性值

        when(userService.userLogin(any(), any(), any())).thenReturn(user);

        User result = userController.userRegister(userLoginRequest, request);

        assertEquals(user, result);
    }

    @Test
    public void testUserLogout() {
        when(userService.userLogout(any())).thenReturn(1);

        Integer result = userController.userLogout(request);

        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    public void testSearchUsers_withAdminUser() {
        List<User> userList = new ArrayList<>();
        // 添加测试数据到userList

        when(UserAuthority.isAdmin(request)).thenReturn(true);
        when(userService.list(any())).thenReturn(userList);

        List<User> result = userController.searchUsers("userAccount", "username", request);

        assertEquals(userList, result);
    }

    @Test
    public void testSearchUsers_withNonAdminUser() {
        when(UserAuthority.isAdmin(request)).thenReturn(false);

        List<User> result = userController.searchUsers("userAccount", "username", request);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteUser_withAdminUser() {
        when(UserAuthority.isAdmin(request)).thenReturn(true);
        when(userService.removeById(any())).thenReturn(true);

        boolean result = userController.deleteUser(12345L, request);

        assertTrue(result);
    }

    @Test
    public void testDeleteUser_withNonAdminUser() {
        when(UserAuthority.isAdmin(request)).thenReturn(false);

        boolean result = userController.deleteUser(12345L, request);

        assertFalse(result);
    }

    @Test
    public void testUpdateUser() {
        ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
        // 设置modifyUserRequest的属性值

        when(userService.updateFrontUser(any())).thenReturn(true);

        boolean result = userController.updateUser(modifyUserRequest, request);

        assertTrue(result);
    }

    // 编写其他测试方法，类似上面的示例

}
