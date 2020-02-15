package com.project.avatar.interceptor

import com.google.gson.JsonParser
import com.project.avatar.common.JwtHelper
import com.project.avatar.common.ResultCommon
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.lang.Exception
import javax.security.auth.login.LoginException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginVerificationInterceptor:HandlerInterceptorAdapter() {


    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        response.contentType = "text/html,charset=utf-8"
        response.characterEncoding = "UTF-8"

        val header = request.getHeader("token")
        header?.let {
            JwtHelper.validateLogin(header)?.let {
                request.setAttribute("uid",JsonParser.parseString(JwtHelper.validateLogin(header)).asJsonObject.get("userId").asInt)
                return true
            } ?: let {
                response.writer.write(ResultCommon.toJson(ResultCommon.generateError<String>(msg = "未登录！！")))
                return false
            }
        } ?: let {
            response.writer.write(ResultCommon.toJson(ResultCommon.generateError<String>(msg = "Header token 不能为空！！")))
            return false
        }
    }

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        super.postHandle(request, response, handler, modelAndView)
        println("postHandle---->")
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        super.afterCompletion(request, response, handler, ex)
        println("afterCompletion---->")
    }


}