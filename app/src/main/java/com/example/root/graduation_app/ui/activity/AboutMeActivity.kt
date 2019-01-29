package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.method.LinkMovementMethod
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_about_me.*
import kotlinx.android.synthetic.main.common_toolbar_layout_return.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/28
 *  desc:关于我的介绍 activity
 *  version:1.0
 */
@Suppress("DEPRECATION")
class AboutMeActivity : BaseActivity() {

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, AboutMeActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.colorPrimary), 0)

      common_toolbar.setBackgroundColor(ContextCompat.getColor(this@AboutMeActivity, R.color.colorPrimary))
      common_toolbar_title.text = "关于我们"

      about_content.run {
         text = Html.fromHtml(getString(R.string.about_content))
         movementMethod = LinkMovementMethod.getInstance()
      }

      val versionStr = getString(R.string.app_name) + " V" + packageManager?.getPackageInfo(packageName, 0)?.versionName
      about_version.text = versionStr

      initEvent()
   }

   private fun initEvent() {
      common_toolbar_return.setOnClickListener {
         finish()
      }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_about_me
}