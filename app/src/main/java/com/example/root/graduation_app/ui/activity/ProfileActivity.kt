package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.R.id.activity_profile_btn
import com.example.root.graduation_app.utils.Constants
import com.example.root.graduation_app.utils.StatusBarUtils
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*

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
      StatusBarUtils.darkMode(this)
      StatusBarUtils.setPaddingSmart(this, common_toolbar)
      mLayoutStatusView = activity_profile_multipleStatusView

      mLayoutStatusView?.showEmpty()
      initEvent()
   }

   private fun initEvent() {
      activity_profile_btn.setOnClickListener {
         // click it go to github
         CommonWebViewActivity.runActivity(this@ProfileActivity, "github", Constants.GITHUB_URL)
//         GithubActivity.runActivity(this@ProfileActivity)
      }
   }
}