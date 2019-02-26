package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
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

        activity_feed_back_contentText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                confirmChange(s)
            }
        })

        activity_feed_back_confirmText.setOnClickListener {
            PhoneUserUtils.feedBack(App.getLoginUser()?.userid!!, activity_feed_back_contentText.text.toString().trim(), object : PhoneUserUtils.feedOnClickListener {
                override fun onSuccess(msg: String) {
                    ToastUtils.showToast(this@FeedBackActivity, msg)
                    activity_feed_back_contentText.text = null
                }

                override fun onFailed(error: String) {
                    ToastUtils.showToast(this@FeedBackActivity, error)
                }
            })
        }
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