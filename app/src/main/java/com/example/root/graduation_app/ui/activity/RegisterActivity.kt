package com.example.root.graduation_app.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Editable
import com.example.base_library.RetrofitManager
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.utils.SimpleTextWatcher
import com.jaeger.library.StatusBarUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.concurrent.TimeUnit

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/19
 *  desc:user register page. simulation(模拟) user send SMS. backStage return a value as verification code
 *  version:1.0
 */
@Suppress("PLUGIN_WARNING")
class RegisterActivity : BaseActivity() {

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_register

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColorNoTranslucent(this@RegisterActivity, ContextCompat.getColor(applicationContext, R.color.color_white))

      /**
       * add listening event about phoneNum change,
       * and add space in phoneNum
       */
      activity_register_inputPhone.addTextChangedListener(object : SimpleTextWatcher() {
         @SuppressLint("SetTextI18n")
         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val length = s.toString().length
            //delete number
            if (count == 0) {
               if (length == 4) {
                  activity_login_phone.setText(s?.subSequence(0, 3))
               }
               if (length == 9) {
                  activity_login_phone.setText(s?.subSequence(0, 8))
               }
            }
            //add number
            if (count == 1) {
               if (length == 4) {
                  val part1 = s?.subSequence(0, 3).toString()
                  val part2 = s?.subSequence(3, length).toString()
                  activity_login_phone.setText("$part1 $part2")
               }
               if (length == 9){
                  val part1 = s?.subSequence(0, 8).toString()
                  val part2 = s?.subSequence(8, length).toString()
                  activity_login_phone.setText("$part1 $part2")
               }
            }

         }

         override fun afterTextChanged(s: Editable?) {
            //move the cursor to the end
            activity_login_phone.setSelection(activity_login_phone.text.toString().length)
         }
      })
      initEvent()
   }

   private fun initEvent() {
      activity_register_return.setOnClickListener {
         finish()
      }

      activity_register_btn_next.setOnClickListener {
         checkInputPhoneNum()
      }
   }

   private fun checkInputPhoneNum() {
      when {
         this.activity_register_inputPhone.text.toString().isEmpty() ->
            AlertDialog.Builder(this@RegisterActivity)
                    .setTitle("手机号码错误")
                    .setMessage("手机号码不能为空")
                    .setNegativeButton("确定", null)
                    .create()
                    .show()
         this.activity_register_inputPhone.text.toString().length != 11 ->
            AlertDialog.Builder(this@RegisterActivity)
                    .setTitle("手机号码错误")
                    .setMessage("手机号码位数错误")
                    .setNegativeButton("确定", null)
                    .create()
                    .show()
         else -> {
            // 进行网络请求访问数据库, 调用成功跳转填写验证码界面，否则给出弹窗验证码发送失败
            showProgress(null, getProgressConfig())
            Observable.timer(30, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                       dismissProgress()
                       startActivity(Intent(this@RegisterActivity, InputVerificationCodeActivity::class.java))
                    }
         }
      }
   }
}