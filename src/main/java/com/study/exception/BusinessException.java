package com.study.exception;

/**
 * @author hbc
 * @version 1.0
 * @description 自定义异常
 * @date 2023/3/6 11:49
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected Integer errorCode;

    protected String errorMsg;

    public BusinessException(){
        super();
    }

    public BusinessException(Integer errorCode,String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getErrorCode(){
        return this.errorCode;
    }

    public String getErrorMsg(){
        return this.errorMsg;
    }
}
