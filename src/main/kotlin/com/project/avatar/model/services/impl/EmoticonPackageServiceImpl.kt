package com.project.avatar.model.services.impl

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
class EmoticonPackageServiceImpl:EmoticonPackageService {


    @Resource
    lateinit var emoticonPackageMapper:EmoticonPackageMapper

    /**
     * 分页查询表情包
     */
    override fun findEmoticonByPage(currPage: Int, pageSize: Int): Result<EmoticonPackageData> {
        val findTotalCount = emoticonPackageMapper.findTotalCount()
        val totalPage = Math.ceil((findTotalCount / pageSize).toDouble()).toInt()//总页数
        val curCount = (currPage - 1) * pageSize

        val findEmoticonPackageByPage = emoticonPackageMapper.findEmoticonPackageByPage(curCount, pageSize)

        return ResultCommon.generateData(curPage = currPage,tolPage = totalPage,data = findEmoticonPackageByPage)
    }

    /**
     * 保存表情
     */
    override fun saveEmoticon(emoticonPackageData: EmoticonPackageData): Result<String> {
        return try {
            emoticonPackageMapper.saveEmoticon(emoticonPackageData)
            ResultCommon.generateSuccess(msg = "保存成功！")
        } catch (e:Exception)
        {
            ResultCommon.generateError(msg = "保存失败！")
        }
    }
}