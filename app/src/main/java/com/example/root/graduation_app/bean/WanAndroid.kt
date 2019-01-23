package com.example.root.graduation_app.bean

import android.support.v4.app.INotificationSideChannel
import java.io.Serializable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/21
 *  desc:
 *  version:1.0
 */

/**
"data":[
{
"children":[

],
"courseId":13,
"id":408,
"name":"鸿洋",
"order":190000,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":409,
"name":"郭霖",
"order":190001,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":410,
"name":"玉刚说",
"order":190002,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":411,
"name":"承香墨影",
"order":190003,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":413,
"name":"Android群英传",
"order":190004,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":414,
"name":"code小生",
"order":190005,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":415,
"name":"谷歌开发者",
"order":190006,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":416,
"name":"奇卓社",
"order":190007,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":417,
"name":"美团技术团队",
"order":190008,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":420,
"name":"GcsSloop",
"order":190009,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
},
{
"children":[

],
"courseId":13,
"id":421,
"name":"互联网侦察",
"order":190010,
"parentChapterId":407,
"userControlSetTop":false,
"visible":1
}
],
"errorCode":0,
"errorMsg":""
}
 **/

data class WanAndroidBaseBean<T> (
    val errorCode: Int,
    val errorMsg: String,
    val data: T
) : Serializable

data class WanAndroidListBean<T> (
        val errorCode: Int,
        val errorMsg: String,
        val data: ArrayList<T>
) : Serializable

data class WanAndroidPublicItemBean(
    val children: Any,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Long,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Serializable


data class WanAndroidJson<T>(
    val curPage: Int,
    val datas: ArrayList<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
) : Serializable

data class WanAndroidItem(
    val apkLink: String,
    val author: String,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: Int,
    val link: String,
    val niceDate: String,
    val origin: String,
    val projectLink: String,
    val publishTime: Long,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: ArrayList<WanandroidTag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int
) : Serializable

data class WanandroidTag(
    val name: String,
    val url: String
): Serializable
