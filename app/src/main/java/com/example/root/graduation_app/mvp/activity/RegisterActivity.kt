package com.example.root.graduation_app.mvp.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_register.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/19
 *  desc:用户注册页面，这里模拟用户手机号发短信，后台判断后返回一个值当作验证码进行注册
 *  version:1.0
 */
@Suppress("PLUGIN_WARNING")
class RegisterActivity : BaseActivity() {

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_register

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@RegisterActivity, ContextCompat.getColor(applicationContext, R.color.color_white))
      initView()
   }

   private fun initView() {
      when {
         this.activity_register_inputPhone.text.toString().isEmpty() -> AlertDialog.Builder(this@RegisterActivity)
                 .setTitle("手机号码错误")
                 .setMessage("手机号码不能为空")
                 .setNegativeButton("确定", null)
                 .create()
                 .show()
         this.activity_register_inputPhone.text.toString().length != 11 -> AlertDialog.Builder(this@RegisterActivity)
                 .setTitle("手机号码错误")
                 .setMessage("手机号码位数错误")
                 .setNegativeButton("确定", null)
                 .create()
                 .show()
         else -> {
                // 进行网络请求访问数据库

         }
      }
   }
}