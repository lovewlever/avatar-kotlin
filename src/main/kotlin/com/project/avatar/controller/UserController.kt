package com.project.avatar.controller

import com.project.avatar.common.PatternCommon
import com.project.avatar.common.RequestMappingCommon
import com.project.avatar.common.Result
import com.project.avatar.model.dao.data.UserInfo
import com.project.avatar.model.services.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@RestController
class UserController {

    @Resource
    lateinit var userService: UserService


    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.LOGIN)
    fun login(@RequestParam(defaultValue = "")account:String,
              request:HttpServletRequest, pwd:String):Result<UserInfo> {
        val userAgent = request.getHeader("User-Agent")
        var email = ""
        var phone = ""
        var userName = ""
        when {
            PatternCommon.PATTERN_PHONE.matches(account) -> phone = account
            PatternCommon.PATTERN_EMAIL.matches(account) -> email = account
            else -> userName = account
        }

        return userService.login(email, phone, userName, pwd,userAgent)
    }


    /**
     * 注册
     */
    @RequestMapping(RequestMappingCommon.REGISTERED)
    fun registered(@Validated userInfo: UserInfo) : Result<String> {
        return userService.registeredUser(userInfo)
    }


}