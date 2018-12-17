package com.example.root.graduation_app

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.test.TestActivity
import com.example.root.graduation_app.test.TestOneActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.concurrent.TimeUnit

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc: App 引导页
 *  version:1.0
 */
class SplashActivity : BaseActivity() {

    /**
     * 判断本地是否有保存登陆用户的信息，有就直接跳转首页，没有就跳转登陆页面
     */

    override fun loadData() {

    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    @SuppressLint("CheckResult", "PrivateResource")
    override fun initActivity(savedInstanceState: Bundle?) {
        fullScreen()

        val objAlphaIv = ObjectAnimator.ofFloat(ivSplash, "alpha", 0f, 1f)
        val animatorX = ObjectAnimator.ofFloat(ivSplash, "scaleX", 0f, 1f)
        val animatorY = ObjectAnimator.ofFloat(ivSplash, "scaleY", 0f, 1f)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objAlphaIv, animatorX, animatorY)
        animatorSet.duration = 1500
        animatorSet.start()

        Observable.timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                startActivity(Intent(this, TestActivity::class.java))
                finish()
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            }
    }

    override fun needTransparentStatus(): Boolean {
        return true
    }
}