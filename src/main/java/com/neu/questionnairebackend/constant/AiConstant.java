package com.neu.questionnairebackend.constant;

public interface AiConstant {
    String OPENAI_KEY = "sk-AVKSCEh1ms8OY80bD315135eC85a42D39573FfD126Ff7f8f";
    String MODEL = "gpt-3.5-turbo";
    String PROMPT = "数据分析师与评价者";
    String REQUIRE_CONTENT = "我会给你某个问卷信息的填写情况,请你直接给出结论,不要给出无法分析的结论。\\n"+
            "下面我会以固定的格式给你数据：对于每一条数据，我会给你问题、选项以及用户填写的情况...“，"+
            "请注意，不要过度复述我给你的数据，要告诉我从哪些数据中可以看出什么。下面是用户填写的数据:\\n。";
}
