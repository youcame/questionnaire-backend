package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.mapper.UserMapper;
import com.neu.questionnairebackend.model.domain.Project;
import com.neu.questionnairebackend.model.domain.User;
import com.neu.questionnairebackend.service.ProjectService;
import com.neu.questionnairebackend.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
* @author HUANG
* @description 针对表【project】的数据库操作Service实现
* @createDate 2023-06-08 19:33:05
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService{

    @Resource
    private UserMapper userMapper;
    @Resource
    private ProjectMapper projectMapper;
    @Override
    public boolean updateById(Project project, HttpServletRequest request) {
        project.setUpdateTime(new Date());
        project.setUpdateBy(UserAuthority.getCurrentUserName(request));
        return this.updateById(project);
    }

    @Override
    public int createProject(String projectName, String projectDescription, String creator, Long userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", creator);
        Long result = userMapper.selectCount(queryWrapper);
        if(result<=0)return -1;
        Project project = new Project();
        QueryWrapper<Project> queryWrapper1 = new QueryWrapper<>();
        Long result1 = projectMapper.selectCount(queryWrapper1);
        project.setCreateBy(creator);
        project.setProjectName(projectName);
        project.setProjectDescription(projectDescription);
        project.setCreateTime(new Date());
        project.setUpdateTime(new Date());
        project.setId((int)(result1+1));
        project.setUserId(userId);
        this.save(project);
        return 1;
    }


}




