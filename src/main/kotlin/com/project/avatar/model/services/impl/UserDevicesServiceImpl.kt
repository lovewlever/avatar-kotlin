package com.project.avatar.model.services.impl

import com.project.avatar.common.Result
import com.project.avatar.common.ResultCommon
import com.project.avatar.model.dao.data.UserDevicesData
import com.project.avatar.model.services.UserDevicesService
import org.springframework.stereotype.Service

@Service
class UserDevicesServiceImpl:UserDevicesService {


    override fun findDeviceByUserId(userId: Int): Result<UserDevicesData> {
        return ResultCommon.generateSuccess()
    }

    override fun findDevicesByUserId(userId: Int): Result<UserDevicesData> {
        return ResultCommon.generateSuccess()
    }
}