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

   var userphone: String? = null
   var username: String? = null
   var password: String? = null
   var signDays: Int? = null         // indicate(表示, 表明) how many days have singed, exceed(超过) 48 hours clear
   var isSignToday: Boolean? = null  // 表示今天是否签到
   var signTime: String? = null      // 表示今天签到时间
   var signTotalDays: Int? = null     // 表示一共签到了几天
   var loginTime: String? = null     // 表示第一次登陆时间 any day's the first login time
   var logoutTime: String? = null     // 表示登出时间
   var totalTime: Long? = null        // 使用 App 的总时间
   var isSignOut: Boolean? = null     // 标记是否退出账号

}

