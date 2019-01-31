package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_profile.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/28
 *  desc:
 *  version:1.0
 */
class ProfileActivity : BaseActivity() {

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, ProfileActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_profile

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@ProfileActivity,
              ContextCompat.getColor(this@ProfileActivity, R.color.colorPrimary), 0)
      progressBarLayout.visibility = View.GONE
      loadErrorView.visibility = View.VISIBLE
   }
}