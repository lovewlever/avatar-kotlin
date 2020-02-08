package com.project.avatar.common

data class Result<T> (var code:Int = -1,
                 var curPage:Int = 1,
                 var tolPage:Int = 1,
                 var msg:String? = "",
                 var data:MutableList<T> = ArrayList())
