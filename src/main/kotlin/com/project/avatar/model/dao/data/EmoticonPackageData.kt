package com.project.avatar.model.dao.data

import java.io.Serializable

class EmoticonPackageData : Serializable {

    var id: Long? = 0
    var filePath: String? = ""
    var addTime: String? = ""
    var fileName: String? = ""
    var fileSize: String? = ""
    var label: String? = ""
    var describeX: String? = ""
    var userId: Long? = 0
    var userEntity: UserInfo?  = null

    companion object {
        private const val serialVersionUID = -3279737447120264708L
    }
}
