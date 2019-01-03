package com.example.root.graduation_app.mvp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.utils.Constants
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
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, ProfileActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_profile

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setTranslucentForCoordinatorLayout(this@ProfileActivity, 0)
//      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) (common_toolbar.layoutParams as Toolbar.LayoutParams).to = ScreenUtils.dip2px(this, 24f)
      initEvent()
   }

   private fun initEvent() {
      activity_profile_btn.setOnClickListener {
         // click it go to github
         GithubActivity.runActivity(this@ProfileActivity)
      }
   }

//   override fun needTransparentStatus(): Boolean {
//      return true
//   }
}