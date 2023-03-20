package com.study.controller;

import com.study.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/3/8 17:48
 */
@RestController
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping("send")
    public Boolean kafkaTest(@RequestParam("message") String message){
        kafkaProducer.publishToTopic(message);
        return true;
    }
}
