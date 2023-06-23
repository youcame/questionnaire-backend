package com.neu.questionnairebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.mapper.ChoicesMapper;
import com.neu.questionnairebackend.mapper.ProjectMapper;
import com.neu.questionnairebackend.mapper.UserMapper;
import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.domain.Project;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.service.ProjectService;
import com.neu.questionnairebackend.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ChoicesMapper choicesMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateById() {
        Project project = new Project();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(UserAuthority.getCurrentUserName(request)).thenReturn("testUser");
        when(projectMapper.updateById(project)).thenReturn(1);
        boolean result = projectService.updateById(project, request);
        assertTrue(result);
        verify(projectMapper, times(1)).updateById(project);
        assertEquals("testUser", project.getUpdateBy());
    }

    @Test
    void testCreateProject_UserAccountNotFound() {
        String projectName = "Test Project";
        String projectDescription = "This is a test project";
        String creator = "testUser";
        Long userId = 1L;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", creator);
        when(userMapper.selectCount(queryWrapper)).thenReturn(0L);
        int result = projectService.createProject(projectName, projectDescription, creator, userId);
        assertEquals(-1, result);
        verify(userMapper, times(1)).selectCount(queryWrapper);
        verify(projectMapper, never()).insert(any(Project.class));
    }

    @Test
    void testCreateProject_Success() {
        String projectName = "Test Project";
        String projectDescription = "This is a test project";
        String creator = "testUser";
        Long userId = 1L;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", creator);
        when(userMapper.selectCount(queryWrapper)).thenReturn(1L);
        when(projectMapper.selectCount(any(QueryWrapper.class))).thenReturn(0L);
        when(projectMapper.insert(any(Project.class))).thenReturn(1);
        int result = projectService.createProject(projectName, projectDescription, creator, userId);
        assertEquals(1, result);
        verify(userMapper, times(1)).selectCount(queryWrapper);
        verify(projectMapper, times(1)).selectCount(any(QueryWrapper.class));
        verify(projectMapper, times(1)).insert(any(Project.class));
    }

    @Test
    void testGetChoices() {
        int id = 1;
        QueryWrapper<Choices> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("questionId", id);
        List<Choices> choicesList = new ArrayList<>();
        when(choicesMapper.selectList(queryWrapper)).thenReturn(choicesList);
        List<Choices> result = projectService.getChoices(id);
        assertEquals(choicesList, result);
        verify(choicesMapper, times(1)).selectList(queryWrapper);
    }

}