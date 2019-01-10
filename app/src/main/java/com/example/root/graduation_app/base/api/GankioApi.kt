package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.GankIdDayBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc: 干货集中营数据
 *  version:1.0
 */
interface GankioApi {

   /**
    * 每日数据： http://gank.io/api/day/年/月/日
    * eg:http://gank.io/api/day/2015/08/06
    */
   @GET("/api/day/{year}/{month}/{day}")
   fun getGankIoDay(@Path("year") year: String,
                    @Path("month") month: String,
                    @Path("day") day: String): Observable<GankIdDayBean>


}