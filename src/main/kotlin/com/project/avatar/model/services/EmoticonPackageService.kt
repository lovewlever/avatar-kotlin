package com.project.avatar.model.services

import com.project.avatar.common.Result
import com.project.avatar.model.dao.data.EmoticonPackageData

interface EmoticonPackageService {

    fun findEmoticonByPage(currPage:Int, pageSize:Int):Result<EmoticonPackageData>

    fun saveEmoticon(emoticonPackageData: EmoticonPackageData):Result<String>
}