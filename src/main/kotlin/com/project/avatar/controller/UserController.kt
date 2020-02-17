package com.project.avatar.controller

import com.project.avatar.common.*
import com.project.avatar.model.dao.data.UserDevicesData
import com.project.avatar.model.dao.data.UserInfo
import com.project.avatar.model.services.UserDevicesService
import com.project.avatar.model.services.UserService
import nl.bitwalker.useragentutils.UserAgent
import org.apache.logging.log4j.LogManager
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

    private val log = LogManager.getLogger(UserController::class.simpleName)

    @Resource
    lateinit var userService: UserService

    @Resource
    lateinit var userDevicesService: UserDevicesService
    var codeResult = 0

    /**
     * 获取验证码
     */
    @RequestMapping(value = [RequestMappingCommon.GET_VERIFY_CODE], produces = ["image/jpg"])
    fun getVerifyCode(request: HttpServletRequest): ByteArray {
        log.debug("进入获取验证码流程")

        val verifyCode = VerifyCode()

        val outputStream = ByteArrayOutputStream()

        ImageIO.write(verifyCode.image, "jpg", outputStream)

        codeResult = verifyCode.value

        log.debug("进入获取验证码流程-以流的方式返回生成验证码图片")
        return outputStream.toByteArray()
    }


    /**
     * 登录
     */
    @RequestMapping(RequestMappingCommon.LOGIN)
    fun login(@RequestParam(defaultValue = "") account: String,
              request: HttpServletRequest, pwd: String, verifyCode: Int): Result<UserInfo> {
        log.debug("进入登录流程-传入参数：$account-$pwd-$verifyCode")
        if (verifyCode != codeResult) {
            log.debug("登录流程-判断验证码：验证码错误（$verifyCode != ${codeResult}）")
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

        log.debug("登录流程-进入Service层判断密码与用户并返回结果给前端")
        return userService.login(email, phone, userName, pwd, userAgent)
    }


    /**
     * 注册
     */
    @RequestMapping(RequestMappingCommon.REGISTERED)
    fun registered(account: String, pwd: String, verifyCode: Int, request: HttpServletRequest): Result<String> {
        log.debug("进入注册流程-传入参数：$account-$pwd-$verifyCode")

        //判断验证码
        if (verifyCode != codeResult) {
            log.debug("注册流程-判断验证码：验证码错误（$verifyCode != ${codeResult}）")
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
        if (registeredUser.code != 1) {
            log.debug("注册流程-保存用户信息：保存用户信息失败（${registeredUser.msg}）")
            return registeredUser
        }

        log.debug("注册流程-开始查询保存的用户id")
        userService.findUserInfoByOther(userInfo.uEmail!!, userInfo.uPhone!!, userInfo.uName!!)?.let { uInfo ->
            log.debug("注册流程-查询到保存的用户并处理用户设备信息，注册时间等")
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

            log.debug("注册流程-保存注册用户的设备信息，注册时间等，并返回信息给前端")
            return userDevicesService.saveDevices(userDevicesData)

        } ?: let {
            log.debug("注册流程-查询保存的用户信息错误！")
            return ResultCommon.generateSuccess(msg = "注册成功，err！")
        }
    }


}