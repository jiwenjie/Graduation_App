package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.WeixinChoiceListBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/11
 *  desc:
 *  version:1.0
 */
interface WeixinApi {

   @GET("query")
   fun getWeixinChoiceList(@Query("pno") page: Int, @Query("ps") ps: Int,
                           @Query("key") key: String)
           : Observable<WeixinChoiceListBean>

}















