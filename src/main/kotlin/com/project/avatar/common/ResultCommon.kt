package com.project.avatar.common

class ResultCommon {

    companion object {

        fun <T> generateData(code: Int = 1, curPage: Int = 1, tolPage: Int = 1, msg: String? = "success", data: MutableList<T> = ArrayList()): Result<T> {
            return Result(code = code, curPage = curPage, tolPage = tolPage, data = data, msg = msg)
        }

        fun <T> generateData(code: Int = 1, curPage: Int = 1, tolPage: Int = 1, msg: String? = "success", data: T): Result<T> {
            val list = ArrayList<T>()
            list.add(data)
            return Result(code = code, curPage = curPage, tolPage = tolPage, data = list, msg = msg)
        }

        fun <T> generateError(code: Int = -1, msg: String? = "error"): Result<T> {
            return Result(code = code, msg = msg)
        }

        fun <T> generateSuccess(code: Int = 1, msg: String? = "success"): Result<T> {
            return Result(code = code, msg = msg)
        }

    }

}