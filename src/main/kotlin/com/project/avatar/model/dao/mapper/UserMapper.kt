package com.project.avatar.model.dao.mapper

import com.project.avatar.model.dao.data.UserInfo

interface UserMapper {

    fun getUserInfoById(id:Int): UserInfo?

    fun findUserInfoByOther(email:String,phone:String,userName:String):UserInfo?

    fun registeredUser(userInfo: UserInfo)
}