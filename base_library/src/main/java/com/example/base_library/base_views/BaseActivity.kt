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
import com.example.base_library.R
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

   /**
    * 多种状态的 View 切换
    */
   protected var mLayoutStatusView: MultipleStatusView? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
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

   override fun onDestroy() {
      super.onDestroy()
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
      MProgressDialog.showProgress(this@BaseActivity, msg, config)
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

   /** dynamic apply permissions related methods -- start **/

   private val REQUEST_PERMISSION = 1520

   fun reqPermissionResult(isAllGranted: Boolean, permission: Array<String>, reqCode: Int) {

   }

   /**
    * @param permissions
    * @param reqCode
    * @return true is indicate all permissions have been authorization(授权)
    * and false indicate some permissions have not been authorization
    */
   @JvmOverloads
   fun reqPermissions(permissions: Array<String>, reqCode: Int = REQUEST_PERMISSION): Boolean {
      val needReqPermissionList = ArrayList<String>()
      var hasNoAskPermission = false
      for (permission in needReqPermissionList) {
         if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            needReqPermissionList.add(permission)
            /** if user check (don't ask again next time)  */
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
               hasNoAskPermission = true
            }
         }
      }
      if (needReqPermissionList.size == 0) {
         return true
      }

      if (hasNoAskPermission) {
         if (permissions.size == 1) {
            /** if only one permission, indication need execute an action  */
            AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("当前操作所需权限已被禁止。\n\n请点击\"设置\"-\"权限\"-打开所需权限。")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("设置") { dialog, which ->
                       val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                       intent.data = Uri.parse("package:$packageName")
                       startActivity(intent)
                    }
                    .show()
            return false
         }
      }

      ActivityCompat.requestPermissions(this,
              needReqPermissionList.toTypedArray(),
              reqCode)
      return false
   }

   override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      var isAllGranted = true
      for (grantResult in grantResults) {
         if (grantResult != PackageManager.PERMISSION_GRANTED) {
            isAllGranted = false
         }
      }
      reqPermissionResult(isAllGranted, permissions, requestCode)
   }

   /** dynamic apply permissions related methods -- end **/
}