package com.study.controller;

import com.study.domain.vo.MessageVo;
import com.study.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/3/6 10:50
 */
@RestController
@RequestMapping("/result")
public class ResultController {

    @GetMapping("/getStr")
    public String getStr() {
//        int i = 9/0;
        throw new BusinessException(66,"自定义异常");
//        System.out.println("xingbuxing");
//        return "行不行啊";
    }

    @PostMapping("/getObject")
    public MessageVo getObject() {
        MessageVo messageVo = new MessageVo();
        messageVo.setMessage("行不行啊");
//        throw new BusinessException(66,"自定义异常");
        return messageVo;
    }
}
