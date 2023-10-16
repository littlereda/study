package com.study.job;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author hbc
 * @Date 2023/6/12 14:31
 * 实现单线程异步处理
 * @Version 1.0
 */
@Slf4j
@Component
public class UdpMessageHandleJob extends Thread {

    public static Queue<String> udpData = new ConcurrentLinkedQueue<>();

    @SneakyThrows
    @Override
    public void run() {
        while (true) {
            if (udpData.size() > 0) {

            }
//            Thread.sleep(100L);
        }
    }
}
