package com.example.root.graduation_app.bean

import java.io.Serializable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/17
 *  desc:登陆用户的 bean
 *  version:1.0
 */
class LoginUser : Serializable {

   var userid: String? = null
   var userphone: String? = null
   var username: String? = null
   var password: String? = null
   var continuesigndays: Int? = null         // indicate(表示, 表明) how many days have singed
   var signintoday: Boolean? = null  // 表示今天是否签到
   var signintime: String? = null      // 表示今天签到时间
   var signintotaldays: Int? = null     // 表示一共签到了几天
   var logintime: String? = null     // 表示第一次登陆时间 any day's the first login time
   var logouttime: String? = null     // 表示登出时间
   var totaltime: Long? = null        // 使用 App 的总时间
   var avatarpath: String? = null      // 用户头像在服务器存储的地址
//   var signout: Boolean? = null     // 标记是否退出账号

   var collectioncount: String? = null // 收藏了几篇文章

}

