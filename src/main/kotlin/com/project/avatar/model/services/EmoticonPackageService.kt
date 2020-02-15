package com.project.avatar.model.services

import com.project.avatar.common.Result
import com.project.avatar.model.dao.data.EmoticonPackageData

interface EmoticonPackageService {

    fun findEmoticonById(emoId:Int):EmoticonPackageData

    fun findEmoticonByPage(currPage:Int, pageSize:Int):Result<EmoticonPackageData>

    fun findEmoticonRecommend(currPage:Int, pageSize:Int):Result<EmoticonPackageData>

    fun findEmoticonHot(currPage:Int, pageSize:Int):Result<EmoticonPackageData>

    fun findEmoticonClass(currPage:Int, pageSize:Int):String

    fun searchEmoticonByPage(keyWord:String,currPage:Int, pageSize:Int):Result<EmoticonPackageData>

    fun saveEmoticon(emoticonPackageData: EmoticonPackageData):Result<String>

    fun isLike(userId:Int,emoId:Int):Boolean
}