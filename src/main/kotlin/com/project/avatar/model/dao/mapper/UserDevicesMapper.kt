package com.project.avatar.model.dao.mapper

import com.project.avatar.model.dao.data.UserDevicesData

interface UserDevicesMapper {

    fun queryDeviceByUserId(userId:Int):UserDevicesData

    fun queryDevicesByUserId(userId: Int):MutableList<UserDevicesData>

    fun saveDevices(userDevicesData: UserDevicesData)
}