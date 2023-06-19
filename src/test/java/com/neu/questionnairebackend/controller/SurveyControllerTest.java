package com.neu.questionnairebackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.neu.questionnairebackend.Authority.UserAuthority;
import com.neu.questionnairebackend.model.domain.Survey;
import com.neu.questionnairebackend.model.dto.ModifySurveyRequest;
import com.neu.questionnairebackend.service.SurveyService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SurveyControllerTest {

    @Mock
    private SurveyService surveyService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private SurveyController surveyController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetSurveyList() {
        List<Survey> surveyList = new ArrayList<>();
        // 添加测试数据到surveyList

        QueryWrapper<Survey> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("surveyName", "surveyName");
        queryWrapper.like("surveyType", "surveyType");

        when(surveyService.list(queryWrapper)).thenReturn(surveyList);

        List<Survey> result = surveyController.getSurveyList("surveyName", "surveyType", request);

        assertEquals(surveyList, result);
    }

    @Test
    public void testDeleteSurvey() {
        when(UserAuthority.isAdmin(request)).thenReturn(true);
        when(surveyService.removeById(any())).thenReturn(true);

        boolean result = surveyController.deleteSurvey(12345, request);

        assertTrue(result);
    }

    @Test
    public void testUpdateSurvey() {
        ModifySurveyRequest survey = new ModifySurveyRequest();
        // 设置survey的属性值

        when(UserAuthority.isAdmin(request)).thenReturn(true);
        when(surveyService.updateFrontSurvey(any())).thenReturn(true);

        boolean result = surveyController.updateUser(survey, request);

        assertTrue(result);
    }

    // 编写其他测试方法，类似上面的示例

}