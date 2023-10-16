package com.study.config;

import com.study.websocket.MyWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author hbc
 * @description: websocket配置类，配置和管理websocket连接
 * @date 2023/7/18 15:10
 */
@Configuration
@EnableWebSocket
public class WebScoketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new MyWebSocketHandler(),"/websocket").setAllowedOrigins("*");
    }
}
