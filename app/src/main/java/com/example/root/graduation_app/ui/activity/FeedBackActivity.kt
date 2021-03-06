package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.utils.PhoneUserUtils
import com.example.root.graduation_app.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_feed_back.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/20
 *  desc:用户反馈页面
 *  version:1.0
 */
class FeedBackActivity : BaseActivity() {

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, FeedBackActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {

      activity_feed_back_toolbar.setNavigationOnClickListener {
         finish()
      }

      activity_feed_back_contentText.addTextChangedListener(object : SimpleTextWatcher() {
         override fun afterTextChanged(s: Editable?) {
            confirmChange(s)
         }
      })

      activity_feed_back_confirmText.setOnClickListener {
         showProgress(null)
         PhoneUserUtils.feedBack(App.getLoginUser()?.userid!!, activity_feed_back_contentText.text.toString().trim(), object : PhoneUserUtils.feedOnClickListener {
            override fun onSuccess(msg: String) {
               dismissProgress()
               feedSuccess(msg)
            }

            override fun onFailed(error: String) {
               dismissProgress()
               ToastUtils.showToast(this@FeedBackActivity, error)
            }
         })
      }
   }

   /**
    * 提交反馈成功界面
    */
   fun feedSuccess(msg: String) {
      ToastUtils.showToast(this@FeedBackActivity, msg)
      AlertDialog.Builder(this)
              .setMessage(msg)
              .setNegativeButton("我知道了", null)
              .show()
      activity_feed_back_contentText.text = null
      activity_feed_back_confirmText.isEnabled = false
      activity_feed_back_confirmText.background = ContextCompat.getDrawable(this@FeedBackActivity, R.drawable.login_btn_no_press)
   }

   private fun confirmChange(s: Editable?) {
      if (s.isNullOrEmpty()) {
         activity_feed_back_confirmText.isEnabled = false
         activity_feed_back_confirmText.background = ContextCompat.getDrawable(this@FeedBackActivity, R.drawable.login_btn_no_press)
      } else {
         activity_feed_back_confirmText.isEnabled = true
         activity_feed_back_confirmText.background = ContextCompat.getDrawable(this@FeedBackActivity, R.drawable.login_btn_is_press)
      }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_feed_back
}