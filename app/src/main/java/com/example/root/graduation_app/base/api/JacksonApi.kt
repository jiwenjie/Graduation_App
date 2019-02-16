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
    * 注册的接口（分两部分，先注册手机号。之后在注册用户名）
    */
   @FormUrlEncoded
   @POST("phoneUser/registerPhone")
   fun registerUserPhoneNum(@Field("userphone") userphone: String): Observable<BaseJackson<String>>

   /**
    * 第二部分，写入用户名和密码
    */
   @FormUrlEncoded
   @POST("phoneUser/registerNameAndPass")
   fun registerUserNameAndPass(@Field("userid") userId: String,
                               @Field("username") username: String,
                               @Field("password") password: String): Observable<BaseJackson<LoginUser>>

   /**
    * 登录方法 by userPhone
    */
   @FormUrlEncoded
   @POST("phoneUser/loginByPhone")
   fun loginByUserPhone(@Field("userphone") userphone: String, @Field("password") password: String)
           : Observable<BaseJackson<LoginUser>>

   /**
    * 登录 by userName
    */
   @FormUrlEncoded
   @POST("phoneUser/loginByName")
   fun loginByName(@Field("username") username: String, @Field("password") password: String)
           : Observable<BaseJackson<LoginUser>>

   /**
    * 上传头像的接口
    */
   @Multipart
   @POST("phoneUser/modifyAvatar")
   fun uploadAvatar(@Part("userid") userid: String, @Part imgs: MultipartBody.Part): Observable<BaseJackson<LoginUser>>

   /**
    * 更改用户的基本信息（昵称和简介）
    */
   @FormUrlEncoded
   @POST("phoneUser/changeUserInfo")
   fun changeNickNameAndDescription(@Field("userid") userid: String,
                                    @Field("nickname") nickname: String,
                                    @Field("description") description: String): Observable<BaseJackson<String>>

   /**
    * 获取用户的所有信息
    */
   @FormUrlEncoded
   @POST("phoneUser/getUserInfo")
   fun getUserInfo(@Field("userid") userId: String): Observable<BaseJackson<LoginUser>>

   /**
    * 签到调用的接口
    */
   @FormUrlEncoded
   @POST("phoneUser/userSignUp")
   fun userSignUp(@Field("userid") userid: String, @Field("signtime") signtime: String)
           : Observable<BaseJackson<LoginUser>>

   /**
    * 获取todo列表
    */
   @FormUrlEncoded
   @POST("todoTask/getListDataAboutTask")
   fun getTodoListData(
           @Field("userid") userId: String,
           @Field("page") page: Int,
           @Field("limit") limit: Int,
           @Field("complete") complete: Boolean): Observable<BaseJacksonList<TodoBean>>

   /**
    * 获取任务详情
    */
   @FormUrlEncoded
   @POST("todoTask/getDetailById")
   fun getTaskDetail(@Field("userid") userid: String, @Field("todoid") id: String): Observable<BaseJackson<TodoBean>>

   /**
    * 新建任务
    */
   @FormUrlEncoded
   @POST("todoTask/createNewTask")
   fun createNewTask(
           @Field("userid") userid: String,
           @Field("title") title: String,
           @Field("content") content: String
   ): Observable<BaseJackson<String>>

   /**
    * 改变任务的状态
    */
   @FormUrlEncoded
   @POST("todoTask/changeStatus")
   fun changeStatus(@Field("userid") userid: String, @Field("todoid") id: String)
           : Observable<BaseJackson<String>>

   /**
    * 获取收藏列表
    */
   @FormUrlEncoded
   @POST("collect/getListCollectArticle")
   fun getCollectData(
           @Field("userid") userid: String,
           @Field("page") page: Int,
           @Field("limit") limit: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

   /**
    * 收藏文章
    */
   @FormUrlEncoded
   @POST("collect/collectArticle")
   fun collectArticle(@Field("phoneuserid") phoneUserId: String,
                      @Field("apklink") apklink: String,
                      @Field("author") author: String,
                      @Field("chapterid") chapterid: Int,
                      @Field("chaptername") chaptername: String,
                      @Field("collect") collect: Boolean,
                      @Field("courseid") courseid: Int,
                      @Field("desc") desc: String,
                      @Field("envelopepic") envelopepic: String,
                      @Field("fresh") fresh: Boolean,
                      @Field("id") id: Int,
                      @Field("link") link: String,
                      @Field("nicedate") nicedate: String,
                      @Field("origin") origin: String,
                      @Field("projectlink") projectlink: String,
                      @Field("publishtime") publishtime: Long,
                      @Field("superchapterid") superchapterid: Int,
                      @Field("superchaptername") superchaptername: String,
                      @Field("title") title: String,
                      @Field("type") type: Int,
                      @Field("userid") userid: Int,
                      @Field("visible") visible: Int,
                      @Field("zan") zan: Int): Observable<BaseJackson<String>>

   /**
    * 取消收藏
    */
   @FormUrlEncoded
   @POST("collect/cancelCollectArticle")
   fun cancelCollect(@Field("userid") userId: String, @Field("id") articleId: Int): Observable<BaseJackson<String>>

}