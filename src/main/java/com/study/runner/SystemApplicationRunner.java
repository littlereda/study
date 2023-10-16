package com.study.runner;

import com.study.job.UdpMessageHandleJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author hbc
 * @description: 初始化项目，启动线程
 * @date 2023/7/18 15:03
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SystemApplicationRunner implements ApplicationRunner {

    private final UdpMessageHandleJob udpMessageHandleJob;
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        udpMessageHandleJob.start();
    }
}
