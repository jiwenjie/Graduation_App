package com.example.root.graduation_app.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/11
 *  desc:网易新闻有关的 bean 类文件
 *  version:1.0
 */
data class WangyiNewsListBean(@SerializedName("T1348647909107")val newsList: ArrayList<WangyiNewsItemBean>)

data class WangyiNewsItemBean(val docid: String,
                              val title: String,
                              val digest: String,
                              val imgsrc: String,
                              val source: String,
                              val ptime: String,
                              val tag: String,
                              val url: String): Serializable





















