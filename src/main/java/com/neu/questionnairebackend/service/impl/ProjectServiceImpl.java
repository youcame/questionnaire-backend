package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.model.domain.Project;
import com.neu.questionnairebackend.service.ProjectService;
import com.neu.questionnairebackend.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

/**
* @author HUANG
* @description 针对表【project】的数据库操作Service实现
* @createDate 2023-06-08 19:33:05
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService{

}




