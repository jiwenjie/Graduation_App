package com.example.base_library.base_views

import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.base_library.ActivityStackManager
import com.example.base_library.R
import com.jaeger.library.StatusBarUtil

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:注意还有权限的动态申请未添加，往后需要添加
 *  version:1.0
 */
abstract class BaseActivity : AppCompatActivity() {

   /**
    * 多种状态的 View 切换
    */
   protected var mLayoutStatusView: MultipleStatusView? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      if (needTransparentStatus()) transparentStatusBar()
      setContentView(getLayoutId())

      StatusBarUtil.setTransparent(this)
//      StatusBarUtil.setColor(this, ContextCompat.getColor(applicationContext, R.color.colorPrimary), 0)
      ActivityStackManager.addActivity(this)

      initActivity(savedInstanceState)
//      mLayoutStatusView?.setOnRetryClickListener { loadData() }
        mLayoutStatusView?.setOnClickListener {
            loadData()
        }
      loadData()
      setListener()
      handleRxBus()
   }

   override fun onDestroy() {
      super.onDestroy()
      ActivityStackManager.removeActivity(this)
   }

   protected abstract fun loadData()

   protected abstract fun getLayoutId(): Int

   protected open fun needTransparentStatus(): Boolean = false

   protected abstract fun initActivity(savedInstanceState: Bundle?)

   protected open fun setListener() {}

   protected open fun handleRxBus() {}

   open fun transparentStatusBar() {
      window.decorView.systemUiVisibility =
              View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
              View.SYSTEM_UI_FLAG_LAYOUT_STABLE
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) colorSetTargetLollipop() // 判断版本是否为 5.0 之上在调用
      supportActionBar?.hide()
   }

   open fun fullScreen() {
      window.decorView.systemUiVisibility =
              View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
              View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
              View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
              View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
              View.SYSTEM_UI_FLAG_FULLSCREEN or
              View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) colorSetTargetLollipop()
      supportActionBar?.hide()
   }

   @TargetApi(Build.VERSION_CODES.LOLLIPOP)  // 这两个属性只有 5.0 之后才有
   fun colorSetTargetLollipop() {
      window.navigationBarColor = Color.TRANSPARENT
      window.statusBarColor = Color.TRANSPARENT
   }
}