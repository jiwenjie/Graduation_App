package com.example.root.graduation_app.bean

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc:
 *  version:1.0
 */
data class GankIdDayBean(val error: Boolean,
                         val results: ArrayList<GankItemBean>)

data class GankItemBean(val _id: String,
                        val createdAt: String,
                        val desc: String,
                        val images: ArrayList<String>,
                        val publishedAt: String,
                        val source: String,
                        val type: String,
                        val url: String,
                        val used: Boolean,
                        val who: String)

