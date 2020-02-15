package com.project.avatar.model.dao.mapper

import com.project.avatar.model.dao.data.EmoticonPackageData

interface EmoticonPackageMapper {

    fun findEmoticonById(int: Int):EmoticonPackageData

    fun findEmoticonPackageByPage(currPage:Int, pageSize:Int):MutableList<EmoticonPackageData>

    fun findTotalCount():Int

    fun findEmoticonRecommend(currPage:Int, pageCount:Int):MutableList<EmoticonPackageData>

    fun findEmoticonHot(currPage:Int, pageCount:Int):MutableList<EmoticonPackageData>

    fun findEmoticonClass(currPage:Int, pageCount:Int):MutableList<EmoticonPackageData>?

    fun searchEmoticonByPage(keyWord:String,pageCount:Int, pageSize:Int):MutableList<EmoticonPackageData>

    fun saveEmoticon(emoticonPackageData: EmoticonPackageData)

    fun isLike(userId:Int,emoId:Int):Boolean

    fun clickLike(userId: Int,emoId: Int)
}