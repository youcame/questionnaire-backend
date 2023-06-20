package com.neu.questionnairebackend.service;

import com.neu.questionnairebackend.model.domain.Choices;
import com.neu.questionnairebackend.model.domain.Project;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author HUANG
* @description 针对表【project】的数据库操作Service
* @createDate 2023-06-08 19:33:05
*/
public interface ProjectService extends IService<Project> {

    public boolean updateById(Project project, HttpServletRequest request);

    public int createProject(String projectName, String projectDescription, String creator, Long userId);

    public List<Choices> getChoices(int id);
}
