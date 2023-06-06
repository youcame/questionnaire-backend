package com.neu.questionnairebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.neu.questionnairebackend.model.domain.Option;
import com.neu.questionnairebackend.service.OptionService;
import com.neu.questionnairebackend.mapper.OptionMapper;
import org.springframework.stereotype.Service;

/**
* @author HUANG
* @description 针对表【option】的数据库操作Service实现
* @createDate 2023-06-06 15:58:09
*/
@Service
public class OptionServiceImpl extends ServiceImpl<OptionMapper, Option>
    implements OptionService{

}




