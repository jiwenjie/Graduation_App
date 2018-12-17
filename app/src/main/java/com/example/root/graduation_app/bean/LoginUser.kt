package com.example.root.graduation_app.bean

import java.io.Serializable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/17
 *  desc:登陆用户的 bean
 *  version:1.0
 */
data class LoginUser(val username: String,
                     val password: String,
                     val signDays: Int,         // 表示签到了几天, 七天后清零
                     val isSignToday: Boolean,  // 表示今天是否签到
                     val signTime: String,      // 表示今天签到时间
                     val signTotalDays: Int,     // 表示一共签到了几天
                     val loginTime: String,     // 表示第一次登陆时间
                     val logoutTime: String,     // 表示登出时间
                     val totalTime: Long        // 使用 App 的总时间
                    ) : Serializable {


}