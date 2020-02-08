package com.project.avatar.common

import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

class SaveFileCommon {

    companion object {

        fun saveFile(files: Array<MultipartFile>,callBack:(saveFileInfoData: SaveFileInfoData) -> Unit)
        {
            val filePath = File("${RequestMappingCommon.BASE_FILE_PATH}/image/")
            if (!filePath.exists()) filePath.mkdirs()

            files.forEach {
                val infoData = SaveFileInfoData()
                val name = it.originalFilename
                val nameSuffix = name?.substring(name.lastIndexOf("."),name.length)
                if (RequestMappingCommon.PATTERN_IMG.matches(nameSuffix.toString()))
                {
                    try {
                        val saveFile = File(filePath,"${System.currentTimeMillis()}_${UUID.randomUUID()}.jpg")
                        it.transferTo(saveFile)
                        infoData.run {
                            this.code = 1
                            this.filePath = saveFile.path
                            this.addTime = System.currentTimeMillis().toString()
                            this.fileName = it.name
                            this.fileSize = it.size.toString()
                        }
                        callBack(infoData)
                    } catch (e:Exception)
                    {
                        infoData.run {
                            this.code = -1
                            this.notSaved = it.name
                        }
                        callBack(infoData)
                    }
                } else
                {
                    infoData.run {
                        this.code = -1
                        this.notSaved = it.name
                    }
                    callBack(infoData)
                }
            }

        }
    }

}