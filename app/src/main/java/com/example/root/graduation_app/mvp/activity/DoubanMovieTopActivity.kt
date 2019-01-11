package com.example.root.graduation_app.mvp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.base_library.base_views.BaseActivity

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/11
 *  desc:douban 电影 top 250 的活动
 *  version:1.0
 */
class DoubanMovieTopActivity : BaseActivity() {

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, DoubanMovieTopActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
   }
}




