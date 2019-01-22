package com.example.root.graduation_app.base.api

import com.example.root.graduation_app.bean.GankIoListBean
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
   @GET("day/{year}/{month}/{day}")
   fun getGankIoDay(@Path("year") year: String,
                    @Path("month") month: String,
                    @Path("day") day: String): Observable<GankIoListBean>

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     *
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{pre_page}/{page}")
    fun getGankIoCustomList(
        @Path("type") type: String, @Path("pre_page")
        pre_page: Int, @Path("page") page: Int
    ): Observable<GankIoListBean>
}