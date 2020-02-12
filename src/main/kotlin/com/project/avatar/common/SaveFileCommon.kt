package com.project.avatar.common

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

/**
 * 保存文件
 */
class SaveFileCommon {

    companion object {

        inline fun saveFile(files: Array<MultipartFile>, callBack:(saveFileInfoData: SaveFileInfoData) -> Unit = {})
        {
            val filePath = File("${RequestMappingCommon.BASE_FILE_PATH}/image/")
            if (!filePath.exists()) filePath.mkdirs()

            files.forEach {
                val infoData = SaveFileInfoData()
                val name = it.originalFilename
                val nameSuffix = name?.substring(name.lastIndexOf("."),name.length)
                if (PatternCommon.PATTERN_IMG.matches(nameSuffix.toString()))
                {
                    try {
                        val saveFile = File(filePath,"${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg")
                        it.transferTo(saveFile)
                        infoData.run {
                            code = 1
                            this.filePath = saveFile.path
                            addTime = System.currentTimeMillis().toString()
                            fileName = it.name
                            fileSize = it.size.toString()
                        }
                        callBack(infoData)
                    } catch (e:Exception)
                    {
                        infoData.run {
                            code = -1
                            notSaved = it.name
                        }
                        callBack(infoData)
                    }
                } else
                {
                    infoData.run {
                        code = -1
                        notSaved = it.name
                    }
                    callBack(infoData)
                }
            }

        }
    }

}