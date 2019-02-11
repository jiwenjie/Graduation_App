package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/28
 *  desc:自己搭建的后台接口部分
 *  version:1.0
 */
interface JacksonApi {

    /**
     * 登陆方法
     */
    @GET("login")
    fun login(@Part("username") username: String, @Part("password") password: String)

    /**
     * 上传头像的接口
     */
    @Multipart
    @POST("phoneUser/modifyAvatar")
    fun uploadAvatar(@Part imgs: MultipartBody.Part): Observable<ResponseBody>

    /**
     * 获取收藏列表
     */
    @POST("getCollect")
    fun getCollectData(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    )
            : Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

    /**
     * 获取todo列表
     */
    @GET("getTodoList")
    fun getTodoListData(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("complete") complete: Boolean
    )
            : Observable<BaseJacksonList<TodoBean>>

    /**
     * 获取任务详情
     */
    @GET("")
    fun getTaskDetail(@Query("id") id: Int): Observable<BaseJackson<TodoBean>>

    /**
     * 新建任务
     */
    @POST("")
    fun createNewTask(
        @Part("title") title: String,
        @Part("content") content: String,
        @Part("time") time: String
    ): Observable<BaseJackson<String>>

    /**
     * 改变任务的状态
     */
    @POST("")
    fun changeStatus(@Part("id") id: Int, @Part("complete") complete: Boolean)
            : Observable<BaseJackson<String>>
}