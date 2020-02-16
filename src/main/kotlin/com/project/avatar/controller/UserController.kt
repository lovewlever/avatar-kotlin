package com.project.avatar.controller

import com.project.avatar.common.*
import com.project.avatar.model.dao.data.UserDevicesData
import com.project.avatar.model.dao.data.UserInfo
import com.project.avatar.model.services.UserDevicesService
import com.project.avatar.model.services.UserService
import nl.bitwalker.useragentutils.UserAgent
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.*
import javax.annotation.Resource
import javax.imageio.ImageIO
import javax.imageio.stream.ImageOutputStreamImpl
import javax.servlet.http.HttpServletRequest

@RestController
class UserController {

    @Resource
    lateinit var userService: UserService

    @Resource
    lateinit var userDevicesService: UserDevicesService
    var codeResult = 0

    /**
     * 获取验证码
     */
    @RequestMapping(value = [RequestMappingCommon.GET_VERIFY_CODE],produces = ["image/jpg"])
    fun getVerifyCode(request: HttpServletRequest):ByteArray {

        val verifyCode = VerifyCode()

        val outputStream = ByteArrayOutputStream()

        ImageIO.write(verifyCode.image,"jpg",outputStream)

        codeResult = verifyCode.value

        return outputStream.toByteArray()
    }


    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.LOGIN)
    fun login(@RequestParam(defaultValue = "") account: String,
              request: HttpServletRequest, pwd: String,verifyCode:Int): Result<UserInfo> {
        if (verifyCode != codeResult)
        {
            return ResultCommon.generateError(msg = "验证码错误！")
        }

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
    fun registered( account: String, pwd: String, verifyCode:Int, request: HttpServletRequest): Result<String> {

        if (verifyCode != codeResult)
        {
            return ResultCommon.generateError(msg = "验证码错误！")
        }

        val userInfo = UserInfo()
        val userDevicesData = UserDevicesData()


        userInfo.uPwd = pwd
        when {
            PatternCommon.PATTERN_PHONE.matches(account) -> userInfo.uPhone = account
            PatternCommon.PATTERN_EMAIL.matches(account) -> userInfo.uEmail = account
            else -> userInfo.uName = account
        }

        val registeredUser = userService.registeredUser(userInfo)
        if (registeredUser.code != 1)
        {
            return registeredUser
        }

        userService.findUserInfoByOther(userInfo.uEmail!!, userInfo.uPhone!!, userInfo.uName!!)?.let { uInfo ->
            val regTime = System.currentTimeMillis()
            val header = request.getHeader("User-Agent")
            val agentString = UserAgent.parseUserAgentString(header)
            userDevicesData.run {
                bName = agentString.browser.name
                bVersion = agentString.browserVersion.version
                bType = agentString.browser.browserType.getName()
                dIsMobile = if (agentString.operatingSystem.isMobileDevice) "Y" else "N"
                dManufacturer = agentString.operatingSystem.manufacturer.getName()
                dName = agentString.operatingSystem.getName()
                dType = agentString.operatingSystem.deviceType.getName()
                registerTime = regTime.toString()
                userId = uInfo.id
            }

            return userDevicesService.saveDevices(userDevicesData)

        }?:let {
            return ResultCommon.generateSuccess(msg = "注册成功，err！")
        }
    }


}