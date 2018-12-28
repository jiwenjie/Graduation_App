package com.example.root.graduation_app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.root.graduation_app.utils.UnCaught
import com.pgyersdk.crash.PgyCrashManager

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:
 *  version:1.0
 */
class App : Application() {

   override fun onCreate() {
      super.onCreate()
      UnCaught.getInstance().init(this)
      PgyCrashManager.register() //推荐使用，注册 蒲公英
      contextInstance = this.applicationContext
   }

   companion object {
      @SuppressLint("StaticFieldLeak")
      lateinit var contextInstance: Context
   }
}