package com.study.service;


import com.aliyuncs.exceptions.ClientException;
import com.study.job.UdpMessageHandleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.dsl.Udp;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class StartUDPService implements ServletContextListener {

//    @Value("${udp.port}")
    private Integer udpPort;

    private long lastReceiveTime = 0;

//    @Bean("UnicastReceivingChannelAdapter1")
//    public UnicastReceivingChannelAdapter getUnicastReceivingChannelAdapter() {
//        UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(udpPort);
//        adapter.setOutputChannelName("udpChannel");
//        adapter.setReceiveBufferSize(2048000);
//        log.info("实例化");
//        return adapter;
//    }

    /**
     * UDP消息接收服务
     */
//    @Bean
//    public IntegrationFlow integrationFlow() {
//        log.info("UDP服务启动成功，端口号为: {}", udpPort);
//        return IntegrationFlows.from(Udp.inboundAdapter(udpPort).receiveBufferSize(2048000).poolSize(1000)).channel("udpChannel").get();
//    }

    /**
     * 转换器
     */
    @Transformer(inputChannel = "udpChannel", outputChannel = "udpFilter")
    public String transformer(@Payload byte[] payload, @Headers Map<String, Object> headers) {
        String message = new String(payload);
        return message;
    }

    /**
     * 过滤器
     */
    @Filter(inputChannel = "udpFilter", outputChannel = "udpRouter")
    public boolean filter(String message, @Headers Map<String, Object> headers) {

        return true;
    }

    /**
     * 路由分发处理器
     */
    @Router(inputChannel = "udpRouter")
    public String router(String message, @Headers Map<String, Object> headers) {

        return "udpHandle1";
    }

    /**
     * 最终处理器1
     */
    @ServiceActivator(inputChannel = "udpHandle1")
    public void udpMessageHandle(String message) throws Exception {
        // 可以进行异步处理
//        UDPDataHandleJob.udpData.offer(message);
//        udpReceiveService.parseUDPData(message);
        UdpMessageHandleJob.udpData.offer(message);
        log.info("接收udp数据:" + message + "--当前时间" + new Date());
        // 更新最后接收数据的时间
        lastReceiveTime = System.currentTimeMillis();
    }

    /**
     * 最终处理器2
     */
    @ServiceActivator(inputChannel = "udpHandle2")
    public void udpMessageHandle2(String message) throws Exception {
        log.info("UDP2:" + message);
    }

    // 定时任务，每一分钟执行一次
//    @Scheduled(fixedRate = 60000)
    public void checkDataReceived() throws ClientException {
        long currentTime = System.currentTimeMillis();

        // 检查是否超过5秒没有数据传递
        if (currentTime - lastReceiveTime > 5000) {
        }
    }
}
