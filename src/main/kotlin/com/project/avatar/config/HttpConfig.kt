package com.project.avatar.config

import com.project.avatar.common.RequestMappingCommon
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

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*")
        super.addCorsMappings(registry)
    }

    override fun registerWebSocketHandlers(p0: WebSocketHandlerRegistry) {
        p0.addHandler(SpringWebSocketHandler(),"/wss/socket.server")
        p0.addHandler(SpringWebSocketHandler(),"/wss/socketJs.server").withSockJS()
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoginVerificationInterceptor())
                .addPathPatterns("/ff")
                //.addPathPatterns(RequestMappingCommon.FIND_EMOTICON_PACKAGE_LIKE)
        super.addInterceptors(registry)
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