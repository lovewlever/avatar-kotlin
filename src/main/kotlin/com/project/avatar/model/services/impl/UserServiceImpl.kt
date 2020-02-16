package com.project.avatar.model.services.impl

import com.project.avatar.common.JwtHelper
import com.project.avatar.common.Result
import com.project.avatar.common.ResultCommon
import com.project.avatar.model.dao.data.UserInfo
import com.project.avatar.model.dao.mapper.UserMapper
import com.project.avatar.model.services.UserService
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * 用户操作服务层
 */
@Service
class UserServiceImpl:UserService {


    @Resource
    lateinit var userMapper: UserMapper


    override fun getUserInfoById(id: Int): Result<UserInfo> {
        val userInfoById = userMapper.getUserInfoById(1)
        val arrList = ArrayList<UserInfo>()
        userInfoById?.let {
            arrList.add(it)
        }
        return ResultCommon.generateData(data = arrList)
    }

    override fun findUserInfoByOther(email: String, phone: String, userName: String): UserInfo? {
        return userMapper.findUserInfoByOther(email,phone, userName)
    }

    /**
     * 注册
     */
    override fun registeredUser(userInfo: UserInfo):Result<String>
    {
        return try {
            userMapper.registeredUser(userInfo)
            ResultCommon.generateSuccess(msg = "注册成功！")
        } catch (e:Exception)
        {
            ResultCommon.generateError(msg = e.message)
        }

    }

    /**
     * 登录
     */
    override fun login(email:String,phone:String,userName:String,pwd:String,identities:String): Result<UserInfo> {
        userMapper.findUserInfoByOther(email,phone,userName)?.let { it ->
            return if (pwd == it.uPwd) {
                it.uPwd = ""
                var userName = ""
                it.uEmail?.let {
                    userName = it
                } ?: it.uName?.let {
                    userName = it
                } ?: it.uPhone?.let {
                    userName = it
                }

                val generateJWT = JwtHelper.generateJWT(it.id.toString(), userName, identities)
                it.token = generateJWT

                ResultCommon.generateData(data = it)
            } else {
                ResultCommon.generateError(msg = "密码错误！")
            }
        } ?: let {
            return ResultCommon.generateError(msg = "找不到此用户或密码错误！")
        }

    }

}