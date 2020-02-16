package com.project.avatar.model.services.impl

import com.project.avatar.common.Result
import com.project.avatar.common.ResultCommon
import com.project.avatar.model.dao.data.UserDevicesData
import com.project.avatar.model.dao.mapper.UserDevicesMapper
import com.project.avatar.model.services.UserDevicesService
import org.springframework.stereotype.Service
import javax.annotation.Resource

@Service
class UserDevicesServiceImpl:UserDevicesService {

    @Resource
    lateinit var userDevicesMapper: UserDevicesMapper

    override fun saveDevices(userDevicesData: UserDevicesData): Result<String> {
        return try {
            userDevicesMapper.saveDevices(userDevicesData)
            ResultCommon.generateSuccess()
        } catch (e:Exception)
        {
            ResultCommon.generateError(msg = e.message)
        }

    }


    override fun findDeviceByUserId(userId: Int): Result<UserDevicesData> {
        return ResultCommon.generateSuccess()
    }

    override fun findDevicesByUserId(userId: Int): Result<UserDevicesData> {
        return ResultCommon.generateSuccess()
    }
}