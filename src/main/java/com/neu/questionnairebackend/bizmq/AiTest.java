package com.neu.questionnairebackend.bizmq;


import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.neu.questionnairebackend.constant.AiConstant;

public class AiTest {
    public static void main(String[] args) {
        String question = "[你对问卷给出几分]:[1人选择了'5分'][1人选择了'0分']\\n";
        String jsonString = "{"
                + "\"model\": \"" + AiConstant.MODEL + "\","
                + "\"messages\": ["
                + "{"
                + "    \"role\": \"system\","
                + "    \"content\": \"" + AiConstant.PROMPT + "\""
                + "},"
                + "{"
                + "    \"role\": \"user\","
                + "    \"content\": \"" + AiConstant.REQUIRE_CONTENT+question + "\""
                + "}"
                + "]"
                + "}";

        String url = "https://d2.xiamoai.top/v1/chat/completions";
        String result = HttpRequest.post(url)
                .header("Authorization", "Bearer " + AiConstant.OPENAI_KEY)
                .header("Content-Type", "application/json")
                .body(jsonString)
                .execute()
                .body();
        System.out.println(result);
    }
}
