package com.example.root.graduation_app.test

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/16
 *  desc:
 *  version:1.0
 */
class TestActivity : BaseActivity() {

    override fun loadData() {
        ToastUtils.showToast(applicationContext, "测试登陆")
    }

    override fun getLayoutId(): Int = R.layout.activity_test

    @SuppressLint("CheckResult")
    override fun initActivity(savedInstanceState: Bundle?) {
//        btn_test.setOnClickListener {
//            startActivity(Intent(applicationContext, TestOneActivity::class.java))
//        }
//       StatusUtils.darkMode(this)
//      StatusUtils.setPaddingSmart(this, ttoo)
//       mLayoutStatusView = multipleStatusView
//       mLayoutStatusView?.showNoNetwork()

    }

//   override fun needTransparentStatus(): Boolean {
////      ic_return true
////   }
}