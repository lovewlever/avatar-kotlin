package com.project.avatar.model.dao.mapper

import com.project.avatar.model.dao.data.EmoticonPackageData

interface EmoticonPackageMapper {

    fun findEmoticonPackageByPage(currPage:Int, pageSize:Int):MutableList<EmoticonPackageData>

    fun findTotalCount():Int

    fun saveEmoticon(emoticonPackageData: EmoticonPackageData)
}