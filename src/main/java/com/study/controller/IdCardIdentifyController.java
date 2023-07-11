package com.study.controller;

import com.study.service.IdCardIdentifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/4/18 14:59
 */
@RestController
@RequestMapping("id")
public class IdCardIdentifyController {
//    @Autowired
//    private IdCardIdentifyService idCardIdentifyService;

    @PostMapping("/identify")
    public void IdCardIdentifyC(){
//        idCardIdentifyService.IdCardIdentify();
    }
}
