package com.example.root.graduation_app.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.utils.SharePreferencesUtil
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils
import kotlinx.android.synthetic.main.activity_input_nickname_and_pass.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/11
 *  desc:
 *  version:1.0
 */
class InputNickNameAndPassActivity : BaseActivity() {

   private var userId: String = ""

   companion object {
      private const val KEY_USERID = "key_user_id"

      @JvmStatic
      fun runActivity(activity: Activity, userId: String) {
         val intent = Intent(activity, InputNickNameAndPassActivity::class.java)
         intent.putExtra(KEY_USERID, userId)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      userId = intent.getStringExtra(KEY_USERID)
      activity_register_completeMessage_next.setOnClickListener {
         if (validateNickname()) {  // 说明用户名输入合法
            registerNameAndPass()
         }
      }
   }

   /**
    * 调用接口注册用户名和密码
    */
   @SuppressLint("CheckResult")
   private fun registerNameAndPass() {
      RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .registerUserNameAndPass(userId, activity_registerName.text.toString().trim(), activity_registerPass.text.toString().trim())
              .compose(RxJavaUtils.applyObservableAsync())
              .subscribe({
                 if (it.result == "succeed") {
                    val user = it.data
                    App.setLoginUser(user)   // 把当前用户保存到 Application 中去
                    // 跳转主页
                    IndexMainActivity.runActivity(this@InputNickNameAndPassActivity)
                    finish()
                 } else {
                    ToastUtils.showToast(this@InputNickNameAndPassActivity, it.msg)
                 }
              }, {
                 ToastUtils.showToast(this@InputNickNameAndPassActivity, it.message.toString())
              })
   }

   override fun loadData() {

   }

   /**
    * 校验用户昵称输入是否合法
    */
   private fun validateNickname(): Boolean {
      val nickname = activity_registerName.text.toString().trim { it <= ' ' }
      if (nickname.length < 2) {
         ToastUtils.showToast(this, resources.getString(R.string.nickname_length_invalid))
         return false
      } else if (!nickname.matches(CommonUtils.NICK_NAME_REG_EXP.toRegex())) {
         ToastUtils.showToast(this, resources.getString(R.string.nickname_invalid))
         return false
      }
      if (activity_registerPass.text.toString().trim().length < 6) {
         ToastUtils.showToast(this@InputNickNameAndPassActivity, "密码长度最少为 6 位")
         return false
      }
      return true
   }

   override fun getLayoutId(): Int = R.layout.activity_input_nickname_and_pass
}