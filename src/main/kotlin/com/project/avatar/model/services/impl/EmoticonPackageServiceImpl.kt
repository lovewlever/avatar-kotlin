package com.project.avatar.model.services.impl

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.project.avatar.common.Result
import com.project.avatar.common.ResultCommon
import com.project.avatar.model.dao.data.EmoticonPackageData
import com.project.avatar.model.dao.mapper.EmoticonPackageMapper
import com.project.avatar.model.services.EmoticonPackageService
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * 表情包服务类
 */
@Service
class EmoticonPackageServiceImpl : EmoticonPackageService {


    @Resource
    lateinit var emoticonPackageMapper: EmoticonPackageMapper

    override fun isLike(userId: Int, emoId: Int): Boolean {
        val like = emoticonPackageMapper.isLike(userId, emoId)
        return like
    }


    /**
     * 通过id查询单个表情
     */
    override fun findEmoticonById(emoId: Int): EmoticonPackageData {
        val emoticon = emoticonPackageMapper.findEmoticonById(emoId)

        return emoticon
    }

    /**
     * 查询推荐
     */
    override fun findEmoticonRecommend(currPage: Int, pageSize: Int): Result<EmoticonPackageData> {
        return ResultCommon.generateSuccess()
    }

    /**
     * 查询热门
     */
    override fun findEmoticonHot(currPage: Int, pageSize: Int): Result<EmoticonPackageData> {
        return ResultCommon.generateSuccess()
    }

    /**
     * 分类查询
     */
    override fun findEmoticonClass(currPage: Int, pageSize: Int): String {

        val findTotalCount = emoticonPackageMapper.findTotalCount()
        val totalPage = Math.ceil((findTotalCount / pageSize).toDouble()).toInt()//总页数
        val curCount = (currPage - 1) * pageSize

        val list = emoticonPackageMapper.findEmoticonClass(curCount, pageSize)

        val gson = Gson()

        val data = JsonArray()

        val jsonObject = JsonObject()

        var labelString = ""

        var jsonArray = JsonArray()

        list?.forEach { epd ->
            if (epd.label != labelString) {
                jsonArray = JsonArray()
                labelString = epd.label.toString()
                jsonArray.add(JsonParser.parseString(gson.toJson(epd)))
            } else {
                jsonArray.add(JsonParser.parseString(gson.toJson(epd)))
            }
            jsonObject.add(labelString, jsonArray)
        }

        val resObj = ResultCommon.toJsonObject(ResultCommon.generateData<String>(curPage = currPage, tolPage = totalPage))

        resObj.remove("data")
        data.add(jsonObject)
        resObj.add("data", data)

        return gson.toJson(resObj)
    }

    /**
     * 搜索
     */
    override fun searchEmoticonByPage(keyWord: String, currPage: Int, pageSize: Int): Result<EmoticonPackageData> {
        return ResultCommon.generateSuccess()
    }

    /**
     * 分页查询表情包
     */
    override fun findEmoticonByPage(currPage: Int, pageSize: Int): Result<EmoticonPackageData> {
        val findTotalCount = emoticonPackageMapper.findTotalCount()
        val totalPage = Math.ceil((findTotalCount / pageSize).toDouble()).toInt()//总页数
        val curCount = (currPage - 1) * pageSize

        val findEmoticonPackageByPage = emoticonPackageMapper.findEmoticonPackageByPage(curCount, pageSize)

        return ResultCommon.generateData(curPage = currPage, tolPage = totalPage, data = findEmoticonPackageByPage)
    }

    /**
     * 保存表情
     */
    override fun saveEmoticon(emoticonPackageData: EmoticonPackageData): Result<String> {
        return try {
            emoticonPackageMapper.saveEmoticon(emoticonPackageData)
            ResultCommon.generateSuccess(msg = "保存成功！")
        } catch (e: Exception) {
            ResultCommon.generateError(msg = "保存失败！")
        }
    }
}

