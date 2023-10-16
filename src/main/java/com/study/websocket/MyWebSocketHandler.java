package com.study.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author hbc
 * @description: 自定义websocket处理程序
 * @date 2023/7/18 15:14
 */
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 当WebSocket连接建立成功时调用
        System.out.println("WebSocket连接已建立");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 处理接收到的WebSocket消息
        String payload = message.getPayload();
        System.out.println("接收到消息：" + payload);

        // 发送回复消息给客户端
        String replyMessage = "收到消息：" + payload;
        session.sendMessage(new TextMessage(replyMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 当WebSocket连接关闭时调用
        System.out.println("WebSocket连接已关闭");
    }
}
