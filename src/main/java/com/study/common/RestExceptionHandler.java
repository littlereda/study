package com.study.common;

import com.study.enums.ReturnCode;
import com.study.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author hbc
 * @version 1.0
 * @description 全局异常处理
 * @date 2023/3/6 11:36
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultData exception(Exception e){
        log.info("全局异常处理，message={},exception={}",e.getMessage(),e);
        return ResultData.fail(ReturnCode.RC999.getCode(),ReturnCode.RC999.getMessage());
    }

    @ExceptionHandler(ClassCastException.class)
    public ResultData exception(ClassCastException e){
        log.info("全局异常处理，message={},exception={}",e.getMessage(),e);
        return ResultData.fail(ReturnCode.RC999.getCode(),ReturnCode.RC999.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResultData exception(BusinessException e){
        log.info("全局异常处理，code={},message={}",e.getErrorCode(),e.getErrorMsg());
        return ResultData.fail(e.getErrorCode(),e.getErrorMsg());
    }
}
