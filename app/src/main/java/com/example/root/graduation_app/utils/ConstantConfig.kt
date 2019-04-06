package com.example.root.graduation_app.utils

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/17
 *  desc:常量存储
 *  version:1.0
 */
object ConstantConfig {

   const val JU_HE_APP_KEY = "e293423adc192ebb71523aa3b854b472"   // 聚合数据 key, 获取微信数据时需要传参数
   const val DOUBAN_APIKEY = "0df993c66c0c636e29ecbb5344252a4a"

   const val ZHIHU_BASE_URL = "http://news-at.zhihu.com/"   // 知乎接口的 host 地址
   const val DOUBAN_BASE_URL = "Https://api.douban.com/"   // 豆瓣接口的 host 地址
   const val WEIXIN_BASE_URL = "http://v.juhe.cn/weixin/"         // 微信精选的 host 地址
   const val WANGYI_BASE_URL = "http://c.m.163.com/nc/article/"       // 网易新闻的 host 地址
   const val GANKIO_BASE_URL = "http://gank.io/api/"  // 干货集中营的 host 地址
   const val WANWANDROID_URL = "https://wanandroid.com/"
   const val GITHUB_URL = "https://github.com/jiwenjie/Graduation_App"

   const val JACKSON_BASE_URL = "http://192.168.1.107:8080/"  // 自己搭建的后台 host 地址，需要根据当前的 ip 地址来调整

   const val SHARE_LOGIN_USER_NAME = "share_user"
   const val PAGE_LIMIT: Int = 20   // 分页查询的时候一次查询 20 个

   /**
    * title key
    */
   const val CONTENT_TITLE_KEY = "title"
   /**
    * content data key
    */
   const val CONTENT_DATA_KEY = "content_data"
   /**
    * id key
    */
   const val CONTENT_CID_KEY = "cid"

   /**
    *  PgyData
    */
   const val PgyAPIKey = "e9c78c8de1cd495a0065d6bfcedd371e"
   const val PgyUserKey = "458b9f2f6649d7b656503656715061a3"
   const val PgyAppKey = "eef566fce6ff5df19a67b8b3a965b403"

   /**
    * FileProvider authority
    */
   const val FILE_AUTHORITY = "com.graduation.fileprovider"

   /**
    * 保存数据的 key 值
    */
   const val KEY_HOMEFRAGMENT_TREE = "key_homefragment_tree"

   /**
    * 用户的头像标识
    */
   const val KEY_USER_AVATAR = "user_avatar"

   /**
    * 用户的背景图片标识
    */
   const val KEY_USER_BG = "user_bg"
}