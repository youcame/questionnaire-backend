package com.neu.questionnairebackend.common;

public enum ErrorCode {
    SUCCESS(0,"ok",""),
    PARAM_ERROR(40000,"请求参数错误",""),
    NO_LOGIN(40100,"未登录",""),
    NO_AUTH(40101,"没权限", ""),
    ACCOUNT_SAME(40101,"重复性错误", ""),
    TO_MANY_REQUEST(42900,"请求次数过多", ""),
    PARAM_NULL(40001,"请求得到的数据不存在",""),
    SYSTEM_ERROR(50000, "系统内部异常", ""),
    OPERATION_ERROR(50001, "操作失败","");
    private int code;
    private String message;
    private String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
