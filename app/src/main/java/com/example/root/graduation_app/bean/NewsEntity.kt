package com.example.root.graduation_app.bean

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/06
 *  desc:
 *  version:1.0
 */
data class NewsEntity(
    val error_code: Int,
    val reason: String,
    val result: NewsResult
)

data class NewsResult(
    val `data`: List<NewsData>,
    val stat: String
)

data class NewsData(
    val author_name: String,
    val category: String,
    val date: String,
    val thumbnail_pic_s: String,
    val thumbnail_pic_s02: String,
    val thumbnail_pic_s03: String,
    val title: String,
    val uniquekey: String,
    val url: String
)