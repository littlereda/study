package com.study.common;

import com.study.enums.ReturnCode;
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
public class ExceptionHandler {

    @ExceptionHandler
    public ResultData exception(Exception e){
        log.info("全局异常处理，exception={}",e.getMessage(),e);
        return ResultData.fail(ReturnCode.RC999.getCode(),ReturnCode.RC999.getMessage());
    }
}
