package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/21
 *  desc:wanandroid 的接口
 *  version:1.0
 */
interface WanandroidApi {

    /**
     * 获取公众号列表
     * http://wanandroid.com/wxarticle/chapters/json    方法： GET
     */
    @GET("wxarticle/chapters/json")
    fun getPublicAddressList(): Observable<WanAndroidListBean<WanAndroidPublicItemBean>>

    /**
     * 获取某个公众号的历史数据
     * http://wanandroid.com/wxarticle/list/405/1/json
        方法：GET
        参数：
        公众号 ID：拼接在 url 中，eg:405
        公众号页码：拼接在url 中，eg:1
     */
    @GET("wxarticle/list/{publicId}/{pageNum}/json")
    fun getOnePublicAddressHistory(@Path("publicId") publicId: Int, @Path("pageNum") pageNum: Int)
            : Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

    /**
     * 在某个公众号中搜索文章
     *  http://wanandroid.com/wxarticle/list/405/1/json?k=Java
        方法：GET
        参数 ：
        k : 字符串，eg:Java
        公众号 ID：拼接在 url 中，eg:405
        公众号页码：拼接在url 中，eg:1
     */
    @GET("wxarticle/list/{publicId}/{pageNum}/json")
    fun searchArticleInPublicAddress(@Path("publicId") publicId: Int, @Path("pageNum") pageNum: Int,
                                     @Query("k") searchKey: String): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

    /**
     * 获取最新项目
     * http://wanandroid.com/article/listproject/0/json
        方法：GET
        参数：页码，拼接在连接中，从0开始。
     */
    @GET("article/listproject/{page}/json")
    fun getProjectList(@Path("page") page: Int) : Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

    /**
     * hot key for search
     * http://www.wanandroid.com//hotkey/json
     */
    @GET("/hotkey/json")
    fun getHotkeyWord()
}