package com.project.avatar.model.services

import com.project.avatar.common.Result
import com.project.avatar.model.dao.data.UserDevicesData

interface UserDevicesService {

    fun findDeviceByUserId(userId:Int): Result<UserDevicesData>

    fun findDevicesByUserId(userId: Int):Result<UserDevicesData>
}