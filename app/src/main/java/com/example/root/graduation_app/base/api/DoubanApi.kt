package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.DoubanBookBean
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import com.example.root.graduation_app.bean.DoubanMovieBean
import com.example.root.graduation_app.bean.DoubanMovieDetail
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
    * get hot movies
    */
   @GET("v2/movie/in_theaters")
   fun getDoubanHotMovie(): Observable<DoubanMovieBean>

   /**
    * get movie detail
    */
   @GET("v2/movie/subject/{id}")
   fun getDoubanMovieDetail(@Path("id") id: String): Observable<DoubanMovieDetail>


   /**
    * get hot movie top250
    * @param start 从多少开始，如从"0"开始
    * @param count 一次请求的数目，如"10"条，最多100
    */
   @GET("v2/movie/top250")
   fun getDoubanMovieTop250(@Query("start") start: Int,
                            @Query("count") count: Int): Observable<DoubanMovieBean>


   /**
    * 根据 tag 获取图书
    * @param tag   搜索关键字
    * @param start 从多少开始，如从 "0" 开始
    * @param count 一次请求的数目 最多 100
    */
   //https://api.douban.com/v2/book/search?tag=%E6%96%87%E5%8C%96&start=0&count=30

   @GET("v2/book/search")
   fun getBookListWithTag(@Query("tag") tag: String,
                          @Query("start") start: Int,
                          @Query("count") count: Int,
                          @Query("apikey") apiKey: String): Observable<DoubanBookBean>

   @GET("v2/book/{id}")
   fun getBookDetail(@Path("id") id: String, @Query("apikey") apiKey: String): Observable<DoubanBookItemDetail>

}