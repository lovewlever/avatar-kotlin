package com.project.avatar.controller

import com.project.avatar.common.RequestMappingCommon
import com.project.avatar.common.Result
import com.project.avatar.common.ResultCommon
import com.project.avatar.model.dao.data.AuthQQData
import com.project.avatar.model.dao.data.UserInfo
import com.project.avatar.model.services.AuthService
import com.project.avatar.model.services.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

@RestController
class AuthController {

    @Resource
    lateinit var authService: AuthService


    @RequestMapping(RequestMappingCommon.AUTH_QQ)
    fun authQQ():Result<String>
    {
        val authQQData = AuthQQData()
        authQQData.qqId = "jfklsjf33245553ff"
        authQQData.qqName = "结论"
        authQQData.userId = 1
        return authService.authQQ(authQQData)
    }

}