package com.example.root.graduation_app.mvp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.R.id.activity_input_verification_code_return
import com.example.root.graduation_app.R.id.verify_code_view
import com.example.root.graduation_app.widget.VerifyCodeView
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_input_verification_code.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/20
 *  desc:输入验证码的界面
 *  version:1.0
 */
class InputVerificationCodeActivity : BaseActivity() {

    private var countDownTimer: CountDownTimer? = null

    override fun loadData() {

    }

    override fun getLayoutId(): Int = R.layout.activity_input_verification_code

    override fun initActivity(savedInstanceState: Bundle?) {
        StatusBarUtil.setColor(this@InputVerificationCodeActivity, ContextCompat.getColor(applicationContext, R.color.color_white))

        initView()
        initEvent()
    }

    private fun initView() {
        /** 实现倒计时 **/
        countDownTimer = object : CountDownTimer(45000, 1000) {
            override fun onFinish() {
                activity_input_verification_code_verifyCode_countDownText.isEnabled = true
                activity_input_verification_code_verifyCode_countDownText.text = "发送验证码"
                activity_input_verification_code_verifyCode_countDownText.setTextColor(ContextCompat.getColor(applicationContext, R.color.theme))
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                activity_input_verification_code_verifyCode_countDownText.isEnabled = false
                activity_input_verification_code_verifyCode_countDownText.text = String.format("%ss后重发验证码","$millisUntilFinished / 1000")
                activity_input_verification_code_verifyCode_countDownText.setTextColor(ContextCompat.getColor(applicationContext, R.color.alpha_60_black))
            }
        }
        //开始倒计时
        countDownTimer?.start()
    }

    private fun initEvent() {
        activity_input_verification_code_return.setOnClickListener {
            finish()
        }

        /** 点击调用重发验证码 **/
        activity_input_verification_code_bottomLyt.setOnClickListener {

        }

        /**
         * 验证码输入完成后的回掉
         */
        verify_code_view.setInputCompleteListener(object : VerifyCodeView.InputCompleteListener {
            override fun inputComplete() {
                // 输入完成后自动加载判断是否输入正确
//                verify_code_view.editContent
            }

            override fun invalidContent() {
            }
        })

        /**
         * 点击重发验证码
         */
        activity_input_verification_code_verifyCode_countDownText.setOnClickListener {

        }
    }
}