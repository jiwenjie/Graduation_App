package com.example.root.graduation_app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDexApplication
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.utils.UnCaught
import com.pgyersdk.crash.PgyCrashManager

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:
 *  version:1.0
 */
class App : MultiDexApplication() {

   override fun onCreate() {
      super.onCreate()
      UnCaught.getInstance().init(this)   // 本地 crash 捕捉
      PgyCrashManager.register()    // 新版推荐使用，注册 蒲公英
      contextInstance = this.applicationContext

      registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
         override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            LogUtils.e(activity?.localClassName + "onCreate()")
         }

         override fun onActivityStarted(activity: Activity?) {
            LogUtils.e(activity?.localClassName + "onActivityStarted()")
         }

         override fun onActivityResumed(activity: Activity?) {
            LogUtils.e(activity?.localClassName + "onActivityResumed()")
         }

         override fun onActivityPaused(activity: Activity?) {
            LogUtils.e(activity?.localClassName + "onActivityPaused()")
         }

         override fun onActivityStopped(activity: Activity?) {
            LogUtils.e(activity?.localClassName + "onActivityStopped()")
         }

         override fun onActivityDestroyed(activity: Activity?) {
            LogUtils.e(activity?.localClassName + "onActivityDestroyed()")
         }

         override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            LogUtils.e(activity?.localClassName + "onActivitySaveInstanceState()")
         }
      })
   }

   companion object {
      @SuppressLint("StaticFieldLeak")
      lateinit var contextInstance: Context
   }
}