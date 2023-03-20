package com.study.common;

import com.study.enums.ReturnCode;
import lombok.Data;

/**
 * @author hbc
 * @version 1.0
 * @description 返回值封装
 * @date 2023/3/6 10:31
 */
@Data
public class ResultData<T> {
    private int status;
    private T data;
    private String message;

    public static <T> ResultData<T> success(T data){
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC100.getCode());
        resultData.setData(data);
        resultData.setMessage(ReturnCode.RC100.getMessage());
        return resultData;
    }

    public static <T> ResultData<T> fail(int code,String message){
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

}
