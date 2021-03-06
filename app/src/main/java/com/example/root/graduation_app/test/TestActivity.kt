package com.example.root.graduation_app.test

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.base_library.RetrofitManager
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.base.api.WangyiApi
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils

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

//        RetrofitManager.setBaseUrl(ConstantConfig.WEIXIN_BASE_URL)

        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)

        RetrofitManager.provideClient(ConstantConfig.WANGYI_BASE_URL).create(WangyiApi::class.java)
            .getNewsList(0)
            .compose(RxJavaUtils.applyObservableAsync())
            .subscribe({
                AlertDialog.Builder(this@TestActivity)
                    .setMessage("成功")
                    .setTitle("测试")
                    .create()
                    .show()
                LogUtils.e(it.newsList)
            }, {
                LogUtils.e(it.message)
            })

//        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            (toolbar.getLayoutParams() as Toolbar.LayoutParams).bottomMargin = ScreenUtils.dip2px(this, 24f)
//        }
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