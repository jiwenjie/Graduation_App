package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.ZhihuDailyDetailBean
import com.example.root.graduation_app.bean.ZhihuDailyListBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:
 *  version:1.0
 */
interface ZhihuApi {

   @GET("/api/4/news/latest")
   fun getLastDailyList(): Observable<ZhihuDailyListBean>

   @GET("/api/4/news/before/{date}")
   fun getDailyListWithDate(@Path("date") date: String): Observable<ZhihuDailyListBean>

   @GET("/api/4/news/{id}")
   fun getZhihuDailyDetail(@Path("id") id: String): Observable<ZhihuDailyDetailBean>

}
