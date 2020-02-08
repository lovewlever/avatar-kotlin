package com.project.avatar.common

object RequestMappingCommon {

    const val BASE_FILE_PATH = "/home/avatar"//上传文件时保存的位置
    val PATTERN_IMG = Regex("^(bmp|jpg|png|tif|gif|pcx|tga|exif|fpx|svg|psd|cdr|pcd|dxf|ufo|eps|ai|raw|WMF|webp)$")

    const val LOGIN = "/login"//登录
    const val REGISTERED = "/registered"//注册
    const val GET_USER_INFO = "/getUserInfo"//获取用户信息

    const val AUTH_QQ = "/authQQ"//qq认证
    const val FIND_EMOTICON_PACKAGE = "/find_emoticon_package"//查询表情包
    const val UPLOAD_EMOTICON_PACKAGE = "/upload_emoticon_package"//查询表情包
}