package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.model.domain.Project;
import com.neu.questionnairebackend.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ProjectController projectController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProjectList() {
        List<Project> projectList = new ArrayList<>();
        // 添加测试数据到projectList

        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("projectName", "projectName");
        queryWrapper.like("projectDescription", "projectDescription");

        when(projectService.list(queryWrapper)).thenReturn(projectList);

        List<Project> result = projectController.getProjectList("projectName", "projectDescription", request);

        assertEquals(projectList, result);
    }

    @Test
    public void testDeleteProject() {
        when(UserAuthority.isAdmin(request)).thenReturn(true);
        when(projectService.removeById(any())).thenReturn(true);

        boolean result = projectController.deleteProject(12345, request);

        assertTrue(result);
    }

    @Test
    public void testUpdateProject() {
        Project project = new Project();
        // 设置project的属性值

        when(UserAuthority.isAdmin(request)).thenReturn(true);
        when(projectService.updateById(any())).thenReturn(true);

        boolean result = projectController.updateProject(project, request);

        assertTrue(result);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();
        // 设置project的属性值

        when(UserAuthority.isAdmin(request)).thenReturn(true);
        when(projectService.createProject(any(), any(), any(), any())).thenReturn(12345);

        Integer result = projectController.createProject(project, request);

        assertEquals(Integer.valueOf(12345), result);
    }

    @Test
    public void testFindProjectById() {
        Project project = new Project();
        // 设置project的属性值

        when(projectService.getById(any())).thenReturn(project);

        Project result = projectController.findProjectById(12345);

        assertEquals(project, result);
    }
}
