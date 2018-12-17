package com.example.root.graduation_app.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.support.annotation.FloatRange
import android.support.annotation.RequiresApi
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ScreenUtils.getStatusBarHeight

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/16
 *  desc:
 *  version:1.0
 */
class StatusUtils {

   companion object {
      private var DEFAULT_COLOR = 0
      private var DEFAULT_ALPHA = 0f//Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 0.2f : 0.3f;

      private val MIN_API = 19

      /* 设置状态栏的 darkMode，字体颜色和 icon 变黑（支持 MIUI6 以上，Flyme4 以上， Android M 以上） */
      fun darkMode(activity: Activity) {
         darkMode(activity.window, DEFAULT_COLOR, DEFAULT_ALPHA)
      }

      fun darkMode(activity: Activity, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
         darkMode(activity.window, color, alpha)
      }

      /** 设置状态栏 darkMode,字体颜色及 icon 变黑(目前支持 MIUI6 以上, Flyme4 以上, Android M 以上)  */
      @TargetApi(Build.VERSION_CODES.M)
      fun darkMode(window: Window, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
         when {
//            isFlyme4Later -> {
//               darkModeForFlyme4(window, true)
//               immersive(window, color, alpha)
//            }
//            isMIUI6Later -> {
//               darkModeForMIUI6(window, true)
//               immersive(window, color, alpha)
//            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
               darkModeForM(window, true)
               immersive(window, color, alpha)
            }
            Build.VERSION.SDK_INT >= 19 -> {
               window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
               setTranslucentView(window.decorView as ViewGroup, color, alpha)
            }
            else -> immersive(window, color, alpha)
         }
         //        if (Build.VERSION.SDK_INT >= 21) {
         //            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         //            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         //            window.setStatusBarColor(Color.TRANSPARENT);
         //        } else if (Build.VERSION.SDK_INT >= 19) {
         //            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         //        }

         //        setTranslucentView((ViewGroup) window.getDecorView(), color, alpha);
      }

      @JvmOverloads
      fun immersive(window: Window, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float = 1f) {
         if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = mixtureColor(color, alpha)

            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = systemUiVisibility
         } else if (Build.VERSION.SDK_INT >= 19) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setTranslucentView(window.decorView as ViewGroup, color, alpha)
         } else if (Build.VERSION.SDK_INT >= MIN_API && Build.VERSION.SDK_INT > 19) {
            var systemUiVisibility = window.decorView.systemUiVisibility
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.decorView.systemUiVisibility = systemUiVisibility
         }
      }

      /**
       * 创建假的透明栏
       */
      fun setTranslucentView(container: ViewGroup, color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) {
         if (Build.VERSION.SDK_INT >= 19) {
            val mixtureColor = mixtureColor(color, alpha)
            var translucentView: View? = container.findViewById(android.R.id.custom)
            if (translucentView == null && mixtureColor != 0) {
               translucentView = View(container.context)
               translucentView.id = android.R.id.custom
               val lp = ViewGroup.LayoutParams(
                       ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(container.context))
               container.addView(translucentView, lp)
            }
            if (translucentView != null) {
               translucentView.setBackgroundColor(mixtureColor)
            }
         }
      }

      fun mixtureColor(color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
         val a = if (color and -0x1000000 == 0) 0xff else color.ushr(24)
         return color and 0x00ffffff or ((a * alpha).toInt() shl 24)
      }

      /* android 6.0 设置字体颜色 */
      @RequiresApi(Build.VERSION_CODES.M)
      fun darkModeForM(window: Window, dark: Boolean) {
         //        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
         //        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         //        window.setStatusBarColor(Color.TRANSPARENT);
         var systemUiVisibility = window.decorView.systemUiVisibility
         systemUiVisibility = if (dark) {
            systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
         } else {
            systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
         }
         window.decorView.systemUiVisibility = systemUiVisibility
      }

      /**
       * 设置 Flyme4 + 的 darkMode 的时候字体颜色和 icon 变黑
       * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
       */
      fun darkModeForFlyme4(window: Window?, dark: Boolean): Boolean {
         var result = false
         if (window != null) {
            try {
               val e = window.attributes
               val darkFlag = WindowManager.LayoutParams::class.java
                       .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
               val meizuFlags = WindowManager.LayoutParams::class.java
                       .getDeclaredField("meizuFlags")
               darkFlag.isAccessible = true
               meizuFlags.isAccessible = true
               val bit = darkFlag.getInt(null)
               var value = meizuFlags.getInt(e)
               if (dark) {
                  value = value or bit
               } else {
                  value = value and bit.inv()
               }

               meizuFlags.setInt(e, value)
               window.attributes = e
               result = true
            } catch (var8: Exception) {
               LogUtils.e("StatusBar" + "darkIcon: failed")
            }
         }
         return result
      }

      /**
       * 设置MIUI6+的状态栏是否为darkMode,darkMode时候字体颜色及icon变黑
       * http://dev.xiaomi.com/doc/p=4769/
       */
      fun darkModeForMIUI6(window: Window, darkmode: Boolean): Boolean {
         val clazz = window.javaClass
         return try {
            val darkModeFlag: Int
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.invoke(window, if (darkmode) darkModeFlag else 0, darkModeFlag)
            true
         } catch (e: Exception) {
            e.printStackTrace()
            false
         }
      }



      /* 增加 View 的 paddingTop，增加的值是状态栏的高度 (智能判断，并设置高度) */
      fun setPaddingSmart(context: Context, view: View) {
         if (Build.VERSION.SDK_INT >= MIN_API) {
            val lp = view.layoutParams
            if (lp != null && lp.height > 0) {
               lp.height += getStatusBarHeight(context)  // 增高
            }
            view.setPadding(view.paddingStart, view.paddingTop + getStatusBarHeight(context),
                    view.paddingEnd, view.paddingBottom)
         }
      }

      /* 获取状态栏高度 */
      fun getStatusBarHeight(context: Context): Int {
         var result = 24
         val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
         result = if (resId > 0) {
            context.resources.getDimensionPixelSize(resId)
         } else {
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result.toFloat(), Resources.getSystem().displayMetrics).toInt()
         }
         return result
      }
   }
}