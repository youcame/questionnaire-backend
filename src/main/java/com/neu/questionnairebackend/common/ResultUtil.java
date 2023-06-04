package com.neu.questionnairebackend.common;

public class ResultUtil {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<T>(0,data,"ok");
    }

    public static <T> BaseResponse<T> failed(T data){
        return new BaseResponse<T>(1,data,"error");
    }
}
