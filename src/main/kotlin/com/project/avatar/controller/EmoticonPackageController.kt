package com.project.avatar.controller

import com.project.avatar.common.*
import com.project.avatar.model.dao.data.EmoticonPackageData
import com.project.avatar.model.services.EmoticonPackageService
import org.apache.logging.log4j.LogManager
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.annotation.Resource


/**
 * 表情包控制器
 */
@RestController
class EmoticonPackageController {

    private val log = LogManager.getLogger(EmoticonPackageController::class.simpleName)

    @Resource
    lateinit var emoticonPackageService: EmoticonPackageService

    /**
     * 查询表情包
     * @param currentPage
     * @param currentCount
     * @return
     */
    @RequestMapping(RequestMappingCommon.FIND_EMOTICON_PACKAGE)
    fun findEmoticonPackage(@RequestParam(name = "currentPage", defaultValue = "1") currentPage: Int, @RequestParam(name = "currentCount", defaultValue = "10") currentCount: Int): Result<EmoticonPackageData> {
        log.debug("进入查询表情包Controller")
        val byPage = emoticonPackageService.findEmoticonByPage(currentPage, currentCount)
        return byPage
    }

    /**
     * 上传表情包
     */
    @RequestMapping(RequestMappingCommon.UPLOAD_EMOTICON_PACKAGE)
    fun uploadEmoticonPackage(@RequestParam("file")files: Array<MultipartFile>,describe:String,label:String):Result<String>
    {

        when {
            describe == "" -> return ResultCommon.generateError(msg = "描述不能为空")
            label == "" -> return ResultCommon.generateError(msg = "标签不能为空")
            files.isEmpty() -> return ResultCommon.generateError(msg = "请选择文件")
            else -> {
                var notSavedFile = ""

                SaveFileCommon.saveFile(files) {
                    if (it.code == 1) {
                        val emoticonData = EmoticonPackageData()

                        emoticonData.run {
                            this.addTime = it.addTime
                            this.fileName = it.fileName
                            this.filePath = it.filePath
                            this.fileSize = it.fileSize
                            this.describeX = describe
                            this.label = label
                            this.userId = 1
                        }

                        if (emoticonPackageService.saveEmoticon(emoticonData).code != 1) {
                            notSavedFile += "${it.fileName},"
                        }

                    } else {
                        notSavedFile += "${it.fileName},"
                    }
                }

                return ResultCommon.generateSuccess(msg = "成功！未上传的文件：$notSavedFile")
            }
        }

    }

}