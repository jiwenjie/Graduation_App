package com.example.root.graduation_app.bean

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.xml.transform.Templates


/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc: douban book bean
 *  version:1.0
 */

/**
 * {
"rating":{
"max":10,
"numRaters":35915,
"average":"8.2",
"min":0
},
"subtitle":"日本文化的类型",
"author":[
"（美）鲁思・本尼迪克特"
],
"pubdate":"1990-6",
"tags":[
{
"count":10720,
"name":"日本文化",
"title":"日本文化"
},
{
"count":8096,
"name":"日本",
"title":"日本"
},
{
"count":5545,
"name":"社会学",
"title":"社会学"
},
{
"count":5093,
"name":"菊与刀",
"title":"菊与刀"
},
{
"count":4136,
"name":"文化",
"title":"文化"
},
{
"count":4012,
"name":"人类学",
"title":"人类学"
},
{
"count":2773,
"name":"历史",
"title":"历史"
},
{
"count":2332,
"name":"社会",
"title":"社会"
}
],
"origin_title":"The Chrysanthemum and the Sword",
"image":"https://img3.doubanio.com/view/subject/m/public/s1074166.jpg",
"binding":"平装",
"translator":[
"吕万和",
"熊达云",
"王智新"
],
"catalog":"第一章 任务——研究日本
第二章 战争中的日本人
第三章 各得其所，各安其分
第四章 明治维新
第五章 历史和社会的负恩者
第六章 报恩于万一
第七章 “情义最难接受”
第八章 洗刷污名
第九章 人情的世界
第十章 道德的困境
第十一章 自我修养
第十二章 儿童学习
第十三章 投降后的日本人",
"pages":"218",
"images":{
"small":"https://img3.doubanio.com/view/subject/s/public/s1074166.jpg",
"large":"https://img3.doubanio.com/view/subject/l/public/s1074166.jpg",
"medium":"https://img3.doubanio.com/view/subject/m/public/s1074166.jpg"
},
"alt":"https://book.douban.com/subject/1022238/",
"id":"1022238",
"publisher":"商务印书馆",
"isbn10":"7100012937",
"isbn13":"9787100012935",
"title":"菊与刀",
"url":"https://api.douban.com/v2/book/1022238",
"alt_title":"The Chrysanthemum and the Sword",
"author_intro":"鲁思·本尼迪克特(Ruth Benedict)1887年生于纽约。原姓富尔顿(Fulton)，其祖先曾参加美国独立战争。她本人大学时期主修英国文学。1919年入哥伦比亚大学研究人类学，是Franz Boas的学生，1923年获博士学位。1927年研究印第安部落的文化，写成《文化的类型》(Patterns of Culture，1934年出版)一书。1940年著《种族：科学与政治》(Race：Science and Politics)，批判种族歧视。第二次世界大战期间从事对罗马尼亚、荷兰、德国、泰国等国民族性的研究，而以对日本的研究，即《菊与刀》一书成就最大。战后，她继续在哥伦比亚大学参加“当代文化研究”，于1948年9月，病逝。",
"summary":"“菊”本是日本皇室家徽，“刀”是武士道文化的象征。美国人类学家鲁思・本尼迪克特用《菊与刀》来揭示日本人的矛盾性格亦即日本文化的双重性(如爱美而黩武、尚礼而好斗、喜新而顽固、服从而不驯等)……",
"series":{
"id":"2281",
"title":"日本丛书"
},
"price":"16.00"
},
 */

/** 书籍部分的 bean **/
data class DoubanBookBean(
        val count: Int,
        val start: Int,
        val total: Int,
        val books: ArrayList<DoubanBookItemDetail>): Serializable

data class DoubanBookItemDetail(
        val rating: DoubanRating,
        val subtitle: String,
        val author: ArrayList<String>,
        val pubdate: String,
        val tags: ArrayList<DoubanTags>,
        val origin_title: String,
        val image: String,
        val binding: String,
        val translator: ArrayList<String>,
        val catalog: String,
        val pages: String,
        val images: DoubanImg,
        val alt: String,
        val id: String,
        val publisher: String,
        val isbn10: String,
        val isbn13: String,
        val title: String,
        val url: String,
        val alt_title: String,
        val author_intro: String,
        val summary: String,
        val series: DoubanSeries,
        val price: String): Serializable

data class DoubanRating(
        val max: Int,
        val numRaters: Int,
        val average: String,
        val min: Int): Serializable

data class DoubanTags(
        val count: Long,
        val name: String,
        val title: String): Serializable

data class DoubanImg(
        val small: String,
        val large: String,
        val medium: String): Serializable

data class DoubanSeries(
        val id: String,
        val title: String): Serializable


/** 豆瓣电影的 bean **/
data class DoubanMovieBean(
        val count: Int,
        val start: Int,
        val total: Int,
        val subjects: ArrayList<DoubanSubjectBean>,
        val title: String): Serializable

data class DoubanSubjectBean(
        val rating: DoubanMovieRating,
        val genres: ArrayList<String>,
        val title: String,
        val casts: ArrayList<DoubanCastsBean>,
        val collect_count: Int,
        val original_title: String,
        val subtype: String,
        val directors: ArrayList<DoubanCastsBean>,
        val year: String,
        val images: DoubanImg,
        val alt: String,
        val id: String) : Serializable

data class DoubanMovieRating(
        val max: Int,
        val average: Double,
        val stars: String,
        val min: Int): Serializable

data class DoubanCastsBean(
        val alt: String,
        // 导演或演员
        @SerializedName("type")
        var type: String,
        val avatars: DoubanAvatars,
        val name: String,
        val id: String): Serializable

data class DoubanAvatars(
        val small: String,
        val large: String,
        val medium: String): Serializable

/** 豆瓣书籍详情 **/
data class DoubanMovieDetail(
        val rating: DoubanMovieRating,
        val reviews_count: Int,
        val wish_count: Int,
        val douban_site: String,
        val year: String,
        val images: DoubanImg,
        val alt: String,
        val id: String,
        val mobile_url: String,
        val title: String,
        val do_count: Any,
        val share_url: String,
        val seasons_count: Any,
        val schedule_url: String,
        val episodes_count: Any,
        val countries: ArrayList<String>,
        val genres: ArrayList<String>,
        val collect_count: Int,
        val casts: ArrayList<DoubanCastsBean>,
        val current_season: Any,
        val original_title: String,
        val summary: String,
        val subtype: String,
        val directors: ArrayList<DoubanCastsBean>,
        val comments_count: Int,
        val ratings_count: Int,
        val aka: ArrayList<String>): Serializable
