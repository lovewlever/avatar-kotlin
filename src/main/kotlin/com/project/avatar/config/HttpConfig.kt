package com.project.avatar.config

import com.project.avatar.interceptor.LoginVerificationInterceptor
import com.project.avatar.websocket.SpringWebSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@EnableWebMvc
@EnableWebSocket
@Configuration
class HttpConfig:WebMvcConfigurationSupport(), WebSocketConfigurer
{
    override fun registerWebSocketHandlers(p0: WebSocketHandlerRegistry) {
        p0.addHandler(SpringWebSocketHandler(),"/wss/socket.server")
        p0.addHandler(SpringWebSocketHandler(),"/wss/socketJs.server").withSockJS()
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(LoginVerificationInterceptor())
    }

}