package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.common.BaseResponse;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.common.ResultUtil;
import com.neu.questionnairebackend.exception.BusinessException;
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
    public BaseResponse<List<Project>> getProjectList(String projectName, String projectDescription,String createBy, HttpServletRequest request) {
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(projectName)) {
            queryWrapper.like("projectName", projectName);
        }
        if (StringUtils.isNotBlank(projectDescription)) {
            queryWrapper.like("projectDescription", projectDescription);
        }
        if(StringUtils.isNotBlank(createBy)){
            queryWrapper.like("createBy", createBy);
        }
        List<Project> list = projectService.list(queryWrapper);
        return ResultUtil.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteProject(@RequestBody Integer id, HttpServletRequest request) {
        if (!UserAuthority.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH,"没权限");
        }
        if (id < 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"删除id为空");
        } else {
            boolean b = projectService.removeById(id);
            return ResultUtil.success(b);
        }
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> updateProject(@RequestBody Project project, HttpServletRequest request) {
        if (project == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"项目为空");
        } else {
            boolean b = projectService.updateById(project);
            return ResultUtil.success(b);
        }
    }

    @PostMapping("/create")
    public BaseResponse<Integer> createProject(@RequestBody Project project, HttpServletRequest request) {
        if (project == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR,"项目为空");
        }
        int result = projectService.createProject(project.getProjectName(),
                project.getProjectDescription(), UserAuthority.getCurrentUserAccount(request), project.getUserId());
        return ResultUtil.success(result);
    }

    @PostMapping("/findProject")
    public BaseResponse<Project> findProjectById(@RequestBody Integer id){
        if(id == null||id<=0) {
            System.out.println(id);
            throw new BusinessException(ErrorCode.PARAM_ERROR,"传入参数出错");
        }
        Project byId = projectService.getById(id);
        return ResultUtil.success(byId);
    }

}
