package com.project.avatar.model.dao.data

import javax.validation.constraints.NotBlank

class UserInfo {
    var id: Int? = 0
    var token: String? = ""
    var uName: String? = ""
    @NotBlank(message = "密码不能为空")
    var uPwd: String? = ""
    var uPhone:String? = ""
    var uEmail: String? = ""
    var uGender:Int? = 0
    var uAge:Int? = 0
    var authWxData:AuthWxData? = null
    var authQQList:MutableList<AuthQQData>? = null
}
