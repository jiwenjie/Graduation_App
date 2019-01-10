package com.example.root.graduation_app.bean

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/11
 *  desc:网易新闻部分的 bean
 *  version:1.0
 */
data class WeixinChoiceListBean(val reason: String,
                                val result: Result,
                                val error_code: String)

data class Result(val list: ArrayList<WeixinChoiceItemBean>,
                  val totalPage: String,
                  val ps: String,
                  val pno: String)

data class WeixinChoiceItemBean(val id: String,
                                val title: String,
                                val source: String,
                                val firstImg: String,
                                val mark: String,
                                val url: String)
















