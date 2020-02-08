package com.project.avatar.model.services

import com.project.avatar.common.Result
import com.project.avatar.model.dao.data.AuthQQData
import com.project.avatar.model.dao.data.AuthWxData

interface AuthService {

    fun authQQ(authQQData: AuthQQData): Result<String>

    fun authWx(authWxData: AuthWxData):Result<String>
}