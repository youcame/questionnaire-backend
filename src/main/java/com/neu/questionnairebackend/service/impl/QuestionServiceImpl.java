package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.model.domain.Question;
import com.neu.questionnairebackend.service.QuestionService;
import com.neu.questionnairebackend.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author HUANG
* @description 针对表【question】的数据库操作Service实现
* @createDate 2023-06-07 16:33:36
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




