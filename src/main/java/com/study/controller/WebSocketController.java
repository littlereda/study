package com.study.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author hbc
 * @description: TODO
 * @date 2023/7/18 15:22
 */
@Controller
public class WebSocketController {

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public String sendMessage(String message) {
        // 处理接收到的消息，并返回处理结果
        String replyMessage = "处理消息：" + message;
        return replyMessage;
    }
}
