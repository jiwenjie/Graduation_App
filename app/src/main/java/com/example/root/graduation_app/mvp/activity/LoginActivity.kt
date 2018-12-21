package com.example.root.graduation_app.mvp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_login.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:登陆页面
 *  version:1.0
 */
class LoginActivity : BaseActivity() {

   private var user: LoginUser? = null
   private var inputPhone: String? = null
   private var inputPass: String? = null

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_login

   /**
    * 判断本地存储的登陆对象值是否与输入的相同，相同则显示 Hello!xxx欢迎回来
    * 否则显示 欢迎使用
    */
   override fun initActivity(savedInstanceState: Bundle?) {
      user = intent.getSerializableExtra("user") as LoginUser?
      inputPhone = activity_login_phone.text.toString()
//      inputPass = activity_login_password.text.toString()
      isLocalUserPhone()
      initView()
      initEvent()
   }

   private fun initView() {
      /** 监听用户对手机号的输入 **/
      activity_login_phone.addTextChangedListener(object : SimpleTextWatcher() {
         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            compareInputAntLocalUser()
         }
      })
      /** 监听用户对密码的输入 **/
      activity_login_password.addTextChangedListener(object : SimpleTextWatcher() {
         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            judgeTextBtnChange()
         }
      })
   }

   /**
    * 比较用户输入和本地保存的值是否相同
    */
   private fun compareInputAntLocalUser() {
      inputPhone = activity_login_phone.text.toString()
      if (inputPhone!!.length < 11) return
      isLocalUserPhone()
   }

   @SuppressLint("SetTextI18n")
   private fun isLocalUserPhone() {
      val displayName: String

      inputPass = activity_login_password.text.toString()
      if (!inputPass.isNullOrEmpty()) {
         activity_login_btn_active.isEnabled = true
         activity_login_btn_active.alpha = 1f
         activity_login_btn_active.setBackgroundResource(R.drawable.login_btn_is_press)
      } else {
         activity_login_btn_active.isEnabled = false
         activity_login_btn_active.alpha = 0.7f
         activity_login_btn_active.setBackgroundResource(R.drawable.login_btn_no_press)
      }

      if (TextUtils.equals(inputPhone, user?.userphone)) {
         // 如果当前值和用户输入的相同
         activity_login_icon.setBackgroundResource(R.drawable.shape_circle_blue)
         displayName = user?.username!!.substring(1, user?.username!!.length - 1)  // 去掉名称的第一个
         activity_login_icon.text = displayName
         activity_login_welcome.text = "Hello!$displayName\n欢迎回来"
      } else {
         activity_login_icon.setBackgroundResource(R.drawable.login_icon_left)
         activity_login_icon.text = null
         activity_login_welcome.text = "欢迎使用"
      }
   }

   /**
    * 根据用户的输入判断登陆按钮是否变色
    */
   private fun judgeTextBtnChange() {
      inputPhone = activity_login_phone.text.toString()
      inputPass = activity_login_password.text.toString()
      if (!inputPhone.isNullOrEmpty() && !inputPass.isNullOrEmpty()) {
         activity_login_btn_active.isEnabled = true
         activity_login_btn_active.alpha = 1f
         activity_login_btn_active.setBackgroundResource(R.drawable.login_btn_is_press)
      } else {
         activity_login_btn_active.isEnabled = false
         activity_login_btn_active.alpha = 0.7f
         activity_login_btn_active.setBackgroundResource(R.drawable.login_btn_no_press)
      }
   }

   @SuppressLint("PrivateResource")
   private fun initEvent() {
      activity_login_register.setOnClickListener {
         // 点击注册
         ToastUtils.showToast(applicationContext, "点击注册")
         startActivity(Intent(this, RegisterActivity::class.java))
      }

      activity_login_more.setOnClickListener {
         // 更多的点击事件
         ToastUtils.showToast(applicationContext, "点击更多")
      }

      activity_login_btn_active.setOnClickListener {
         // 登陆的点击事件

         /**
          * 此处调用接口访问后台，验证成功在跳转，否则给出错误提示
          */

         startActivity(Intent(this@LoginActivity, MainActivity::class.java))
         finish()
         overridePendingTransition(R.anim.abc_popup_enter, R.anim.abc_popup_exit)
      }
   }
}