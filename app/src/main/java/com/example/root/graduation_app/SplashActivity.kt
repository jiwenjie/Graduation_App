package com.example.root.graduation_app

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.ui.activity.IndexMainActivity
import com.example.root.graduation_app.ui.activity.LoginActivity
import com.example.root.graduation_app.utils.PhoneUserUtils
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable
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
   @SuppressLint("CheckResult", "PrivateResource")
   override fun loadData() {
      if (App.getLoginUser() == null) {
         Observable.timer(3200, TimeUnit.MILLISECONDS)
                 .compose(RxJavaUtils.applyObservableAsync())
                 .subscribe {
                    LoginActivity.runActivity(this@SplashActivity, null)
                    finish()
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                 }
      } else {
         PhoneUserUtils.getUpdateUser(App.getLoginUser()?.userid!!, object : PhoneUserUtils.operationListener {
            @SuppressLint("CheckResult")
            override fun success(user: LoginUser) {
               App.setLoginUser(user)
               LogUtils.e("SplashActivity用户信息：" + user.profile)
               LogUtils.e("SplashActivity头像信息：" + user.avatar)
               Observable.timer(3200, TimeUnit.MILLISECONDS)
                       .compose(RxJavaUtils.applyObservableAsync())
                       .subscribe {
                          goIndexMainActivity()
                       }
            }

            @SuppressLint("PrivateResource")
            override fun failed(error: String) {
               LogUtils.e("SplashActivity===$error")
               Observable.timer(3200, TimeUnit.MILLISECONDS)
                       .compose(RxJavaUtils.applyObservableAsync())
                       .subscribe {
                          // 此时测试直接进入首页
                          // 如果 user 不为空则不管其他，直接进入 Main 中。只有第一次安装的时候需要跳转登陆页面
                          if (App.getLoginUser()?.isSignout!!) { // 如果退出账号
                             LoginActivity.runActivity(this@SplashActivity, null)
                          } else {  // 没有退出账号
                             IndexMainActivity.runActivity(this@SplashActivity)
                          }
                          finish()
                          overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
                       }
            }
         })
      }
   }

   override fun getLayoutId(): Int = R.layout.activity_splash

   override fun initActivity(savedInstanceState: Bundle?) {
      fullScreen()
      val objAlphaIv = ObjectAnimator.ofFloat(ivSplash, "alpha", 0f, 1f)
      val animatorX = ObjectAnimator.ofFloat(ivSplash, "scaleX", 0f, 1f)
      val animatorY = ObjectAnimator.ofFloat(ivSplash, "scaleY", 0f, 1f)

      // 设置 Text 的动画
      val textScaleX = ObjectAnimator.ofFloat(activity_splash_slognText, "scaleX", 1f, 1.35f, 0.75f, 1.25f, 0.85f, 1f)
      val textScaleY = ObjectAnimator.ofFloat(activity_splash_slognText, "scaleY", 1f, 0.8f, 1.25f, 0.85f, 1.15f, 1f)

      val animatorSet = AnimatorSet()
      animatorSet.playTogether(objAlphaIv, animatorX, animatorY, textScaleX, textScaleY)
      animatorSet.duration = 2200
      animatorSet.start()
   }

   @SuppressLint("CheckResult", "PrivateResource")
   private fun getNewUserInfo() {

//      RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
//              .create(JacksonApi::class.java)
//              .getUserInfo("1550113004014")
//              .compose(RxJavaUtils.applyObservableAsync())
//              .subscribe({
//                  if (it.result == "succeed") {
//                     val user = it.data
//                     AlertDialog.Builder(this)
//                             .setTitle("测试")
//                             .setMessage(user.avatar + user.profile)
//                             .show()
//                     LogUtils.e("SplashActivityTest" + user.avatar + user.profile)
//                  } else {
//                     ToastUtils.showToast(this@SplashActivity, it.msg)
//                  }
//              }, {
//                 LogUtils.e(it.message.toString())
//              })


   }

   @SuppressLint("PrivateResource")
   private fun goIndexMainActivity() {
      IndexMainActivity.runActivity(this@SplashActivity)
      finish()
      overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
   }
}