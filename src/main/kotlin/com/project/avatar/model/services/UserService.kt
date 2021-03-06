package com.project.avatar.model.services

import com.project.avatar.common.Result
import com.project.avatar.model.dao.data.UserInfo

interface UserService {

    fun login(email:String,phone:String,userName:String,pwd:String,identities:String): Result<UserInfo>

    fun getUserInfoById(id:Int):Result<UserInfo>

    fun registeredUser(userInfo: UserInfo):Result<String>

    fun findUserInfoByOther(email:String,phone:String,userName:String):UserInfo?
}