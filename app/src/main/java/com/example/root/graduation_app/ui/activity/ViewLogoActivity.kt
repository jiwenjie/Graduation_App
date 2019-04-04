package com.example.root.graduation_app.ui.activity

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.utils.FileUtil
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_view_logo.*
import java.io.FileOutputStream
import java.io.IOException

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/26
 *  desc:
 *  version:1.0
 */
class ViewLogoActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_view_logo)
      StatusBarUtil.setColor(this@ViewLogoActivity,
              ContextCompat.getColor(this@ViewLogoActivity, R.color.alpha_70_black), 0)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         logoImg.transitionName = getString(R.string.transition_view_logo)
      }

      logoImg.setOnLongClickListener {
         // 先弹出对话框之后提示保存图片到本地
         AlertDialog.Builder(this)
                 .setMessage(getString(R.string.saveImageLocal))
                 .setNegativeButton("取消", null)
                 .setPositiveButton("确认") { whitch, i ->
                    saveImage(logoImg)
                 }
                 .show()
         true
      }
   }

   /**
    * 把图片保存到本地的方法
    */
   private fun saveImage(view: View?) {
      val bitmap = getViewBitmap(view)

      //存储
      var outStream: FileOutputStream? = null
      val file = FileUtil.createCameraFile("saveLocal")
      if (file!!.isDirectory) {//如果是目录不允许保存
         return
      }
      try {
         outStream = FileOutputStream(file)
         bitmap?.compress(Bitmap.CompressFormat.PNG, 100, outStream)
         outStream.flush()
         ToastUtils.showToast(this@ViewLogoActivity, "保存成功")
      } catch (e: IOException) {
         e.printStackTrace()
      } finally {
         try {
            bitmap?.recycle()
            if (outStream != null) {
               outStream.close()
            }
         } catch (e: IOException) {
            e.printStackTrace()
         }
      }
   }

   /**
    * 某些机型直接获取会为null,在这里处理一下防止国内某些机型返回null
    */
   private fun getViewBitmap(view: View?): Bitmap? {
      if (view == null) {
         return null
      }
      val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
      val canvas = Canvas(bitmap)
      view.draw(canvas)
      return bitmap
   }

   override fun onBackPressed() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         finishAfterTransition()
      }
      super.onBackPressed()
   }
}