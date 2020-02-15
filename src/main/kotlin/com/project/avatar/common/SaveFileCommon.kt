package com.project.avatar.common

import org.apache.logging.log4j.LogManager
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

/**
 * 保存文件
 */
class SaveFileCommon {

    companion object {

        val log = LogManager.getLogger(SaveFileCommon::class.simpleName)

        inline fun saveFile(files: Array<MultipartFile>, callBack:(saveFileInfoData: SaveFileInfoData) -> Unit = {})
        {
            log.debug("----------------保存表情到本地-START----------------")
            val filePath = File("${RequestMappingCommon.BASE_FILE_PATH}/image/")
            if (!filePath.exists()) filePath.mkdirs()

            files.forEach {
                log.debug("保存表情到本地-ForEachFile")
                val infoData = SaveFileInfoData()
                val name = it.originalFilename
                val namePrefix = name?.substring(0,name.lastIndexOf("."))
                val nameSuffix = name?.substring(name.lastIndexOf("."),name.length)
                if (PatternCommon.PATTERN_IMG.matches(nameSuffix.toString()))
                {
                    try {
                        log.debug("保存表情到本地-匹配为图片>>保存本地")
                        val saveFile = File(filePath,"${System.currentTimeMillis()}_${UUID.randomUUID()}$nameSuffix")
                        it.transferTo(saveFile)
                        infoData.run {
                            code = 1
                            this.filePath = saveFile.path
                            addTime = System.currentTimeMillis().toString()
                            fileName = namePrefix
                            fileSize = it.size.toString()
                        }
                        callBack(infoData)
                    } catch (e:Exception)
                    {
                        log.debug("保存表情到本地-匹配为图片>>Exception>>${e.message}")
                        infoData.run {
                            code = -1
                            notSaved = it.name
                        }
                        callBack(infoData)
                    }
                } else
                {
                    log.debug("保存表情到本地-匹配为图片>>非法图片")
                    infoData.run {
                        code = -1
                        notSaved = it.name
                    }
                    callBack(infoData)
                }
            }

            log.debug("----------------保存表情到本地-END----------------")

        }
    }

}