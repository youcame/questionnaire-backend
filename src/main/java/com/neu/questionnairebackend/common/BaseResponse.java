package com.neu.questionnairebackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T>
 */
@Data
public class  BaseResponse<T> implements Serializable{
    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }
    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }
    public BaseResponse(ErrorCode errorCode){
        this.code =errorCode.getCode();
        this.data = null;
        this.message = errorCode.getMessage();
    }

    public BaseResponse(int code, String message, String description) {
        this.code = code;
        this.data = null;
        this.message = message;
        this.description = description;
    }
}
