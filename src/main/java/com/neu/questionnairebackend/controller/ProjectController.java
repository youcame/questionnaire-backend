package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.model.domain.Project;
import com.neu.questionnairebackend.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 项目接口
 */
@RestController
@Slf4j
@RequestMapping("/project")
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @GetMapping("/search")
    public List<Project> getProjectList(String projectName, HttpServletRequest request) {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(projectName)) {
            queryWrapper.like("projectName", projectName);
        }
        return projectService.list(queryWrapper);
    }

    @PostMapping("/delete")
    public boolean deleteProject(int id, HttpServletRequest request) {
        if (!UserAuthority.isAdmin(request)) {
            return false;
        }
        if (id < 0) {
            return false;
        } else return projectService.removeById(id);
    }

    @PostMapping("/update")
    public boolean updateUser(@RequestBody Project project, HttpServletRequest request) {
        if (project == null || !UserAuthority.isAdmin(request)) {
            return false;
        } else {
            return projectService.updateById(project);
        }
    }

}
