package com.neu.questionnairebackend.exception;

import com.neu.questionnairebackend.common.BaseResponse;
import com.neu.questionnairebackend.common.ErrorCode;
import com.neu.questionnairebackend.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public BaseResponse businessExceptionHandler(BusinessException e){
        log.info("RunTimeException:"+e.getMessage(), e);
        return ResultUtil.error(e.getCode(),e.getMessage(), "");
    }

    @ExceptionHandler
    public BaseResponse runTimeExceptionHandler(RuntimeException e){
        log.info("RunTimeException:", e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROE,e.getMessage(), "");
    }
}
