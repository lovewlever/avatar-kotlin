package com.project.avatar.config

import com.project.avatar.interceptor.LoginVerificationInterceptor
import com.project.avatar.websocket.SpringWebSocketHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.servlet.view.InternalResourceViewResolver
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.view.JstlView
import org.springframework.web.servlet.ViewResolver

@EnableWebSocket
@Configuration
class HttpConfig:WebMvcConfigurer,WebSocketConfigurer
{


    override fun registerWebSocketHandlers(p0: WebSocketHandlerRegistry) {
        p0.addHandler(SpringWebSocketHandler(),"/wss/socket.server")
        p0.addHandler(SpringWebSocketHandler(),"/wss/socketJs.server").withSockJS()
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(LoginVerificationInterceptor())
    }

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer) {
        super.configureDefaultServletHandling(configurer)
        configurer.enable()
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/asserts/")
                .addResourceLocations("classpath:/public/")
        super.addResourceHandlers(registry)
    }



}