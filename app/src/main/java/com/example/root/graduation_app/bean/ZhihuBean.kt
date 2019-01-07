package com.example.root.graduation_app.bean

import com.google.gson.annotations.SerializedName

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:知乎的相关 bean
 *  version:1.0
 */
data class ZhihuDailyListBean(@SerializedName("date") val date: String,
                              @SerializedName("top_stories") val mZhihuDialyItems: ArrayList<ZhihuDailyItemBean>,
                              @SerializedName("stories") val stories: ArrayList<ZhihuDailyItemBean>)

data class ZhihuDailyItemBean(@SerializedName("images") val images: ArrayList<String>,
                              @SerializedName("type") val type: Int,
                              @SerializedName("id") val id: String,
                              @SerializedName("title") val title: String,
                              val date: String,
                              val hasFadedIn: Boolean = false)


data class ZhihuDailyDetailBean(@SerializedName("body") val body: String,
                                @SerializedName("image_source") val image_source: String,
                                @SerializedName("title") val title: String,
                                @SerializedName("image") val image: String,
                                @SerializedName("share_url") val mShareUrl: String,
                                @SerializedName("ga_prefix") val ga_prefix: String,
                                @SerializedName("type") val type: Int,
                                @SerializedName("id") val id: Int,
                                @SerializedName("js") val js: ArrayList<String>,
                                @SerializedName("css") val css: ArrayList<String>)




