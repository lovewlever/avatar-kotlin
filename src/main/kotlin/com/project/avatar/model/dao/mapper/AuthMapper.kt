package com.project.avatar.model.dao.mapper

import com.project.avatar.model.dao.data.AuthQQData
import com.project.avatar.model.dao.data.AuthWxData

interface AuthMapper {

    fun findQQAuthStatus(userId:Int):Int

    fun findWXAuthStatus(userId:Int):Int

    fun authQQ(authQQData: AuthQQData)

    fun authWx(authWxData: AuthWxData)
}