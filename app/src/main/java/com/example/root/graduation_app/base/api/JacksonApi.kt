package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.WanAndroidBaseBean
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanAndroidJson
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/28
 *  desc:自己搭建的后台接口部分
 *  version:1.0
 */
interface JacksonApi {

   fun login()

   @POST("getCollect")
   fun getCollectData(@Query("page") page: Int, @Query("limit") limit: Int)
           : Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

}