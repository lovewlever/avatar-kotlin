package com.project.avatar.model.services.impl

import com.project.avatar.common.Result
import com.project.avatar.common.ResultCommon
import com.project.avatar.model.dao.data.AuthQQData
import com.project.avatar.model.dao.data.AuthWxData
import com.project.avatar.model.dao.mapper.AuthMapper
import com.project.avatar.model.services.AuthService
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class AuthServiceImpl:AuthService {


    @Resource
    lateinit var authMapper: AuthMapper


    /**
     * QQ认证
     */
    override fun authQQ(authQQData: AuthQQData):Result<String> {
        val i = authMapper.findQQAuthStatus(authQQData.userId!!)
        if (i > 0)
        {
            return ResultCommon.generateSuccess(msg = "已认证")
        }

        return try {
            authMapper.authQQ(authQQData)
            ResultCommon.generateSuccess()
        } catch (e:Exception)
        {
            ResultCommon.generateError(msg = e.message)
        }

    }

    /**
     * 微信认证
     */
    override fun authWx(authWxData: AuthWxData):Result<String> {

        val i = authMapper.findWXAuthStatus(authWxData.userId!!)
        if (i > 0)
        {
            return ResultCommon.generateSuccess(msg = "已认证")
        }

        return try {
            authMapper.authWx(authWxData)
            ResultCommon.generateSuccess()
        } catch (e:Exception)
        {
            ResultCommon.generateError(msg = e.message)
        }
    }

}
