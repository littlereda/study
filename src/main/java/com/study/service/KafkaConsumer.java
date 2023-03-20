package com.study.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/3/8 17:54
 */
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "test")
    public void consumerFromTopic(String message) {
        System.out.println("监听"+message);
    }
}
