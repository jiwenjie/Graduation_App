package com.example.base_library.base_utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.Toast

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:
 *  version:1.0
 */
object ToastUtils {

   private var mToast: Toast? = null
   private var mViewToast: Toast? = null

   @SuppressLint("ShowToast")
   @JvmStatic
   @JvmOverloads
   fun showToast(context: Context, msg: String,
                 length: Int = Toast.LENGTH_SHORT) {
      if (mToast == null) {
         mToast = Toast.makeText(context, msg, length)
      } else {
         mToast!!.setText(msg)
      }
      mToast!!.show()
   }

   @JvmStatic
   @JvmOverloads
   fun showCustomViewToast(context: Context, view: View,
                           gravity: Int = Gravity.CENTER,
                           offsetX: Int = 0, offsetY: Int = 0) {
      if (mViewToast == null) {
         mViewToast = Toast(context)
      }
      mViewToast!!.view = view
      mViewToast!!.setGravity(gravity, offsetX, offsetY)
      mViewToast!!.show()
   }

   @JvmStatic
   fun cancelToast() {
      if (mToast != null) {
         mToast!!.cancel()
         mToast = null
      }
      if (mViewToast != null) {
         mViewToast!!.cancel()
         mViewToast = null
      }
   }
}












