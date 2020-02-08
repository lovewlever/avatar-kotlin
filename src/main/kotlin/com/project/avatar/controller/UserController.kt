package com.project.avatar.controller

import com.project.avatar.common.RequestMappingCommon
import com.project.avatar.common.Result
import com.project.avatar.common.ResultCommon
import com.project.avatar.model.dao.data.UserInfo
import com.project.avatar.model.services.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
class UserController {

    @Resource
    lateinit var userService: UserService


    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.LOGIN)
    fun login(email:String,phone:String,userName:String,pwd:String):Result<UserInfo> {
        return userService.login(email,phone,userName,pwd)
    }


    /**
     * 注册
     */
    @RequestMapping(RequestMappingCommon.REGISTERED)
    fun registered(@Validated userInfo: UserInfo) : Result<String> {
        return userService.registeredUser(userInfo)
    }


}