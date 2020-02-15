package com.project.avatar.common

object RequestMappingCommon {

    const val BASE_FILE_PATH = "/Users/lovewlever/Desktop"//上传文件时保存的位置

    const val LOGIN = "/login"//登录
    const val REGISTERED = "/registered"//注册
    const val GET_USER_INFO = "/getUserInfo"//获取用户信息

    const val AUTH_QQ = "/authQQ"//qq认证
    const val FIND_EMOTICON_PACKAGE = "/api/find_emoticon_package"//查询表情包
    const val FIND_EMOTICON_PACKAGE_RECOMMEND = "/api/find_emoticon_package_recommend"//查询表情包推荐
    const val FIND_EMOTICON_PACKAGE_HOT = "/api/find_emoticon_package_hot"//查询表情包热门
    const val FIND_EMOTICON_PACKAGE_CLASS = "/api/find_emoticon_package_class"//查询表情包分类
    const val FIND_EMOTICON_PACKAGE_SEARCH = "/api/find_emoticon_package_search"//搜索表情包
    const val FIND_EMOTICON_PACKAGE_LIKE = "/api/find_emoticon_package_like"//点赞
    const val UPLOAD_EMOTICON_PACKAGE = "/api/upload_emoticon_package"//上传表情包
}