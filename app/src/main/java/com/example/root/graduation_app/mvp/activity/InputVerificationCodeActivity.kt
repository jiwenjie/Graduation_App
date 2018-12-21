package com.example.root.graduation_app.mvp.activity

import android.os.Bundle
import android.widget.Toast
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.widget.VerifyCodeView
import kotlinx.android.synthetic.main.activity_input_verification_code.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/20
 *  desc:输入验证码的界面
 *  version:1.0
 */
class InputVerificationCodeActivity : BaseActivity() {


    override fun loadData() {

    }

    override fun getLayoutId(): Int = R.layout.activity_input_verification_code

    override fun initActivity(savedInstanceState: Bundle?) {
        verify_code_view.setInputCompleteListener(object : VerifyCodeView.InputCompleteListener {
            override fun inputComplete() {

            }

            override fun invalidContent() {
            }
        })
    }
}