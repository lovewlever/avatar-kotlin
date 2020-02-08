package com.project.avatar.model.services.impl

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
    override fun login(email:String,phone:String,userName:String,pwd:String): Result<UserInfo> {
        userMapper.findUserInfoByOther(email,phone,userName).let {
            return if (pwd === it.uPwd) {
                it.uPwd = ""
                ResultCommon.generateData(data = it)
            } else {
                ResultCommon.generateError(msg = "找不到此用户或密码错误！")
            }
        }

    }

}