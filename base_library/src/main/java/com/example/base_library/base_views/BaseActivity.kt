package com.example.base_library.base_views

import android.annotation.TargetApi
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.base_library.ActivityStackManager
import com.example.base_library.PermissionListener
import com.example.base_library.R
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.widget.MProgressDialog
import com.example.base_library.widget.config.MDialogConfig
import com.jaeger.library.StatusBarUtil
import java.util.ArrayList

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:注意还有权限的动态申请未添加，往后需要添加
 *  version:1.0
 */
abstract class BaseActivity : AppCompatActivity() {

   private var mPermissionListener: PermissionListener? = null

   /**
    * 多种状态的 View 切换
    */
   protected var mLayoutStatusView: MultipleStatusView? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      LogUtils.e("onCreate()")
      if (needTransparentStatus()) transparentStatusBar()
      setContentView(getLayoutId())
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

   override fun onStart() {
      super.onStart()
      LogUtils.e("onStart()")
   }

   override fun onResume() {
      super.onResume()
      LogUtils.e("onResume()")
   }

   override fun onPause() {
      super.onPause()
      LogUtils.e("onPause()")
   }

   override fun onStop() {
      super.onStop()
      LogUtils.e("onStop()")
   }

   override fun onDestroy() {
      super.onDestroy()
      LogUtils.e("onDestroy()")
      ActivityStackManager.removeActivity(this)
   }

   protected abstract fun initActivity(savedInstanceState: Bundle?)

   protected abstract fun loadData()

   protected abstract fun getLayoutId(): Int

   protected open fun needTransparentStatus(): Boolean = false

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

   fun showProgress(msg: String?) {
      showProgress(msg, null)
   }

   fun showProgress(msg: String?, config: MDialogConfig?) {
      MProgressDialog.showProgress(this, msg, config)
   }

   fun dismissProgress() {
      MProgressDialog.dismissProgress()
   }

   fun getProgressConfig(): MDialogConfig {
      return MDialogConfig.Builder()
              .isCanceledOnTouchOutside(false)  //点击外部是否可以取消
              //全屏背景窗体的颜色
              .setBackgroundWindowColor(ContextCompat.getColor(this, R.color.alpha_60_black))
              //View背景的圆角
              .setCornerRadius(20f)
              .build()
   }

   /* for permissions request */
   open fun onRuntimePermissionsAsk(permissions: kotlin.Array<String>, listener: PermissionListener) {
      this.mPermissionListener = listener
      val activity = ActivityStackManager.getTopActivity()
      val deniedPermissions: MutableList<String> = mutableListOf()

      permissions
              .filterNot { ContextCompat.checkSelfPermission(activity!!, it) == PackageManager.PERMISSION_GRANTED }
              .forEach { deniedPermissions.add(it) }

      if (deniedPermissions.isEmpty())
         mPermissionListener!!.onGranted()
      else
         ActivityCompat.requestPermissions(activity!!, deniedPermissions.toTypedArray(), 1)
   }

   override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      if (requestCode == 1) {
         val deniedPermissions: MutableList<String> = mutableListOf()
         if (grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
               if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                  deniedPermissions.add(permissions[i])
            }

            if (deniedPermissions.isEmpty())
               mPermissionListener!!.onGranted()
            else
               mPermissionListener!!.onDenied(deniedPermissions)
         }
      }
   }
}