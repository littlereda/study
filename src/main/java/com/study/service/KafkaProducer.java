package com.study.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/3/8 17:49
 */
@Service
@Slf4j
public class KafkaProducer {
    private static final String topic = "test";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishToTopic(String message) {
        System.out.println("publish to topic " + topic);
        ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);
        send.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("===Producing message success");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.info("===Producing message failed");
            }

        });
    }
}
