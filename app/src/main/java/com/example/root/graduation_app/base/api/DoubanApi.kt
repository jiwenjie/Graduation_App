package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.DoubanBookBean
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc: 豆瓣的 api
 *  version:1.0
 */
interface DoubanApi {

   /**
    * 根据tag获取图书
    * @param tag   搜索关键字
    * @param start 从多少开始，如从"0"开始
    * @param count 一次请求的数目 最多100
    */
   //https://api.douban.com/v2/book/search?tag=%E6%96%87%E5%8C%96&start=0&count=30

   @GET("v2/book/search")
   fun getBookListWithTag(@Query("tag") tag: String,
                          @Query("start") start: Int,
                          @Query("count") count: Int): Observable<DoubanBookBean>

   @GET("v2/book/{id}")
   fun getBookDetail(@Path("id") id: String): Observable<DoubanBookItemDetail>

}