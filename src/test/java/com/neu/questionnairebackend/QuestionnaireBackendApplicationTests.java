package com.neu.questionnairebackend;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class QuestionnaireBackendApplicationTests {


    @Test
    void testDigest() {
        final String SALT = "abcd";
        String newPassword = DigestUtils.md5DigestAsHex((SALT + "mypassword").getBytes(StandardCharsets.UTF_8));
        System.out.println(newPassword);
    }

    @Test
    void contextLoads() {
    }
    @Test
    void testString(){
        String a = "1,2";
        List<String> b = new ArrayList<>();
    }

}
