package com.project.avatar.controller

import com.project.avatar.common.PatternCommon
import com.project.avatar.common.RequestMappingCommon
import com.project.avatar.common.Result
import com.project.avatar.common.VerifyCode
import com.project.avatar.model.dao.data.UserInfo
import com.project.avatar.model.services.UserService
import nl.bitwalker.useragentutils.UserAgent
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@RestController
class UserController {

    @Resource
    lateinit var userService: UserService

    @RequestMapping(RequestMappingCommon.GET_VERIFY_CODE)
    fun getVerifyCode(request: HttpServletRequest):String {
        val serName = request.serverName
        val verifyCodePath = RequestMappingCommon.BASE_FILE_PATH + "/verify/"
        val fileName = "${System.currentTimeMillis()}.jpg"
        val file = File(verifyCodePath)
        if (!file.exists()) file.mkdirs()
        val verifyCode = VerifyCode()
        val outPutStream = FileOutputStream(File(file,fileName))
        VerifyCode.output(verifyCode.image,outPutStream)

        return serName + verifyCodePath + fileName
    }


    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.LOGIN)
    fun login(@RequestParam(defaultValue = "") account: String,
              request: HttpServletRequest, pwd: String): Result<UserInfo> {
        val userAgent = request.getHeader("User-Agent")
        var email = ""
        var phone = ""
        var userName = ""
        when {
            PatternCommon.PATTERN_PHONE.matches(account) -> phone = account
            PatternCommon.PATTERN_EMAIL.matches(account) -> email = account
            else -> userName = account
        }

        return userService.login(email, phone, userName, pwd, userAgent)
    }


    /**
     * 注册
     */
    @RequestMapping(RequestMappingCommon.REGISTERED)
    fun registered(@RequestParam(defaultValue = "") account: String, pwd: String,request: HttpServletRequest): Result<String> {
        val regTime = System.currentTimeMillis()
        val header = request.getHeader("User-Agent")
        val userInfo = UserInfo()
        return userService.registeredUser(userInfo)
    }


}