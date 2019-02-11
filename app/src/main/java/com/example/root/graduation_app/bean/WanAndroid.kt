package com.example.root.graduation_app.bean
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

data class WanAndroidBaseBean<T>(
        var errorCode: Int,
        var errorMsg: String,
        var data: T
) : Serializable

data class WanAndroidListBean<T>(
        var errorCode: Int,
        var errorMsg: String,
        var data: ArrayList<T>
) : Serializable

//知识体系
data class KnowledgeTreeBody(
        var children: ArrayList<WanAndroidPublicItemBean>,
        var courseId: Int,
        var id: Int,
        var name: String,
        var order: Int,
        var parentChapterId: Int,
        var visible: Int
) : Serializable

data class WanAndroidPublicItemBean(
        var children: ArrayList<Any>,
        var courseId: Int,
        var id: Int,
        var name: String,
        var order: Long,
        var parentChapterId: Int,
        var userControlSetTop: Boolean,
        var visible: Int
) : Serializable

data class WanAndroidJson<T>(
        var curPage: Int,
        var datas: ArrayList<T>,
        var offset: Int,
        var over: Boolean,
        var pageCount: Int,
        var size: Int,
        var total: Int
) : Serializable

data class WanAndroidItem(
        var apkLink: String,
        var author: String,
        var chapterId: Int,
        var chapterName: String,
        var collect: Boolean,
        var courseId: Int,
        var desc: String,
        var envelopePic: String,
        var fresh: Boolean,
        var id: Int,
        var link: String,
        var niceDate: String,
        var origin: String,
        var projectLink: String,
        var publishTime: Long,
        var superChapterId: Int,
        var superChapterName: String,
        var tags: ArrayList<WanandroidTag>,
        var title: String,
        var type: Int,
        var userId: Int,
        var visible: Int,
        var zan: Int
) : Serializable

data class WanandroidTag(
        var name: String,
        var url: String
) : Serializable


data class WanandroidHotkeyword(
        var id: Int,
        var link: String,
        var name: String,
        var order: Int,
        var visible: Int
) : Serializable
