package com.neu.questionnairebackend.constant;

public interface AiConstant {
    String OPENAI_KEY = "sk-AVKSCEh1ms8OY80bD315135eC85a42D39573FfD126Ff7f8f";
    String MODEL = "gpt-3.5-turbo";
    String PROMPT = "数据分析师与评价者";
    String REQUIRE_CONTENT = "我会给你某个问卷信息的填写情况,请你直接给出结论,不要给出无法分析的结论,不要有其他多余的介绍。\\n"+
            "下面我会以固定的格式给你数据，每一行的格式为:”[问题]:[具体的回答情况1]...“，注意一个问题可能会有很多个回答。下面每一行代表一个数据:\\n。请你在结尾做一个总结，以”这说明了“结尾";
}
