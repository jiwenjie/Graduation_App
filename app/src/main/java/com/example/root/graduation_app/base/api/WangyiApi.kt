package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.WangyiNewsListBean
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/08
 *  desc:网易的 api 接口
 *  version:1.0
 */
interface WangyiApi {

    @GET("headline/T1348647909107/{id}-20.html")
    fun getNewsList(@Path("id") id: Int): Observable<WangyiNewsListBean>

    @GET("{id}/full.html")
    fun getNewsDetail(@Path("id") id: String): Observable<ResponseBody>

}