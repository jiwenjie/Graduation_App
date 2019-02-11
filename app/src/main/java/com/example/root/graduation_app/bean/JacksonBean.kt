package com.example.root.graduation_app.bean

import java.io.Serializable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:
 *  version:1.0
 */
data class BaseJacksonList<T>(
        var result: String,
        var data: ArrayList<T>,
        var code: Int,
        var msg: String
) : Serializable

data class BaseJackson<T>(
        var result: String,
        var data: T,
        var code: Int,
        var msg: String
) : Serializable

data class TodoBean(
        var id: Int,
        var title: String,
        var content: String,
        var createtime: String,
        var complete: Boolean
) : Serializable