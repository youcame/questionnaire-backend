package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.model.domain.Project;
import com.neu.questionnairebackend.model.domain.request.UserRegisterRequest;
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
    public List<Project> getProjectList(String projectName, String projectDescription, HttpServletRequest request) {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(projectName)) {
            queryWrapper.like("projectName", projectName);
        }
        if (StringUtils.isNotBlank(projectDescription)) {
            queryWrapper.like("projectDescription", projectDescription);
        }
        return projectService.list(queryWrapper);
    }

    @PostMapping("/delete")
    public boolean deleteProject(@RequestBody Integer id, HttpServletRequest request) {
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

    @PostMapping("/create")
    public Integer createProject(@RequestBody Project project, HttpServletRequest request) {
        if (project == null) {
            return null;
        }
        if(!UserAuthority.isAdmin(request)){
            return null;
        }
        int result = projectService.createProject(project.getProjectName(),
                project.getProjectDescription(), UserAuthority.getCurrentUserAccount(request), project.getUserId());
        return result;
    }

    @PostMapping("/findProject")
    public Project findProjectById(@RequestBody Integer id){
        if(id == null||id<=0) {
            System.out.println(id);
            return null;
        }
        return projectService.getById(id);
    }

}
