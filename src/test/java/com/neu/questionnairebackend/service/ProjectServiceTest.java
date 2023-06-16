package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.Project;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {
    @Resource
    ProjectService projectService;
    Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Test
    void updateById() {
        Project project = new Project();
        project.setId(5);
        boolean b = projectService.updateById(project);
        logger.info("更新项目的结果为: {}", b);
    }

    @Test
    void createProject() {
        Project project = new Project();
        project.setId(5);
        int admin = projectService.createProject("1", "1", "admin", 1L);
        logger.info("新建项目的id为: {}", admin);
    }
}