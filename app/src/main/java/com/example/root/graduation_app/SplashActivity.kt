package com.example.root.graduation_app

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.base_library.base_utils.SharePreferencesUtil
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.mvp.activity.LoginActivity
import com.example.root.graduation_app.mvp.activity.MainActivity
import com.example.root.graduation_app.utils.Constants
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
        animatorSet.duration = 2000
        animatorSet.start()

        val user = getLoginUser()

        Observable.timer(4, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val intent: Intent
                if (user != null) {     // 说明已经注册过，则直接跳转登陆页面之后在登陆
                    intent = if (user.isSignOut!!) {     // 说明有本地账号但是已经点击过退出登陆，即此时未登录
                        Intent(this@SplashActivity, LoginActivity::class.java)

                    } else {       // 说明有本地账号 即此时已登录
                        Intent(this@SplashActivity, MainActivity::class.java)
                    }
                    intent.putExtra("user", user)
                    startActivity(intent)
                } else {        // 说明是第一次打开，还未注册，所以去登陆页面注册
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }

                finish()
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            }
    }

    /**
     * 这里遇到一个问题，就是 as 后面需要加上 ?
     */
    private fun getLoginUser(): LoginUser? {
        return SharePreferencesUtil.getAny(applicationContext, Constants.SHARE_LOGIN_USER_NAME, LoginUser::class.java) as LoginUser?
    }
}