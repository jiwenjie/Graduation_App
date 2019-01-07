package com.example.root.graduation_app.utils

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/17
 *  desc:常量存储
 *  version:1.0
 */
object Constants {


   const val ZHIHU_BASE_URL = "http://news-at.zhihu.com/"   // 知乎接口的 host 地址
   const val DOUBAN_BASE_URL = "Https://api.douban.com/"   // 豆瓣接口的 host 地址
   const val WEIXIN_BASE_URL = "http://v.juhe.cn/"         // 微信精选的 host 地址
   const val WANGYI_BASE_URL = "http://c.m.163.com/"       // 网易新闻的 host 地址

   const val SHARE_LOGIN_USER_NAME = "share_loginUser"

   const val LOCAL_URL = "http://" + "ip" + "8080/"

   const val GITHUB_URL = "https://github.com/jiwenjie/Graduation_App"

   /**
    * 网易接口
    * http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html  头条
   http://c.3g.163.com/nc/article/list/T1467284926140/0-20.html 精选
   http://c.3g.163.com/nc/article/list/T1348648517839/0-20.html   娱乐
   http://c.m.163.com/nc/auto/list/5bmz6aG25bGx/0-20.html 汽车
   http://c.m.163.com/nc/auto/list/6YOR5bee/0-20.html   http://c.m.163.com/nc/auto/list/6YOR5bee/20-20.html   
   http://c.3g.163.com/nc/article/list/T1348649079062/0-20.html  运动
   http://c.3g.163.com/nc/article/local/5bmz6aG25bGx/0-20.html 平顶山
   http://c.m.163.com/nc/article/list/T1444270454635/0-20.html 漫画
    http://c.m.163.com/nc/article/list/T1444270454635/20-20.html  更多
   http://h5.manhua.163.com/reader/section/4560963294790090531/4560963294790090535  漫画详情
   http://h5.manhua.163.com/reader/section/4317076104890059052/4701909430260109003 
    */

   /**
    * @GET("nc/article/headline/T1348647909107/{id}-20.html")
   Observable<NewsList> getNews(@Path("id") int id );

   @GET("nc/article/{id}/full.html")
   Observable<String> getNewsDetail(@Path("id") String id);
    */
}