package com.example.root.graduation_app.utils

import android.content.Context
import androidx.core.content.edit
import com.alibaba.fastjson.JSON
import com.example.root.graduation_app.App
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:
 *  version:1.0
 */
object SharePreferencesUtil {

   /** SharePreference 的存储关键字 **/
   private const val SHARED_PREFERENCES_NAME = "graduation_app_files"

   private fun getUserKey(): String? {
      return App.getLoginUser()?.userid + "-" + App.getLoginUser()?.userphone
   }

   @JvmStatic
   fun saveString(context: Context, key: String, value: String) {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit {
         putString(key, value)
         apply()
      }
   }

   @JvmStatic
   fun getString(context: Context, key: String, default: String = ""): String {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      return sp.getString(key, default) ?: ""
   }

   @JvmStatic
   fun saveInteger(context: Context, key: String, value: Int) {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit {
         putInt(key, value)
         apply()
      }
   }

   @JvmStatic
   fun getInteger(context: Context, key: String, default: Int = 0): Int {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      return sp.getInt(key, default)
   }

   @JvmStatic
   fun saveLong(context: Context, key: String, value: Long) {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit {
         putLong(key, value)
         apply()
      }
   }

   @JvmStatic
   fun getLong(context: Context, key: String, default: Long = 0L): Long {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      return sp.getLong(key, default)
   }

   @JvmStatic
   fun saveFloat(context: Context, key: String, value: Float) {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit { putFloat(key, value) }
   }

   @JvmStatic
   fun getFloat(context: Context, key: String, default: Float = 0F): Float {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      return sp.getFloat(key, default)
   }

   @JvmStatic
   fun saveBoolean(context: Context, key: String, value: Boolean) {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit {
         putBoolean(key, value)
         apply()
      }
   }

   @JvmStatic
   fun getBoolean(context: Context, key: String, default: Boolean = false): Boolean {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      return sp.getBoolean(key, default)
   }

   @JvmStatic
   fun saveStringSet(context: Context, key: String, value: Set<String>) {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit {
         putStringSet(key, value)
         apply()
      }
   }

   @JvmStatic
   fun getStringSet(context: Context, key: String): Set<String>? {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      return sp.getStringSet(key, null)
   }

   /**
    * 保存任何类型，所以可以传递任何类型对象
    */
   @JvmStatic
   fun saveAny(context: Context, key: String, user: LoginUser?) {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit {
         putString(key, JSON.toJSONString(user))
         commit()
      }
   }

   /**
    * 获取任何的 bean 类型，所以需要传你要获取的类型，
    * `object` 就是获取的值转化的类型
    */
   @JvmStatic
   fun getAny(context: Context, key: String): LoginUser? {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      val stringAny = sp.getString(key, "")
      if (stringAny.isNullOrEmpty()) return null
      return JSON.parseObject(stringAny, LoginUser::class.java)
   }

   /**
    * 存储取出列表数据
    */
   @JvmStatic
   fun saveHomeTreeList(context: Context, key: String, beanList: List<WanAndroidPublicItemBean>) {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit {
         putString(key, JSON.toJSONString(beanList))
         apply()
      }
   }

   @JvmStatic
   fun getHomeTreeList(context: Context, key: String): MutableList<WanAndroidPublicItemBean>? {
      val sp = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      val data = sp.getString(key, "")
      return if (data.isNullOrEmpty()) {
         null
      } else {
         JSON.parseArray(data, WanAndroidPublicItemBean::class.java)
      }
   }

   /**
    * 保存用户头像或背景的路径, type 传递参数是头像或者背景
    */
   @JvmStatic
   fun saveUserImgPath(type: String, path: String) {
      val sp = App.contextInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      sp.edit {
         putString(getUserKey() + type, path)
         apply()
      }
   }

   /**
    * 读取用户头像或背景的路径, type 传递参数是头像或者背景
    */
   @JvmStatic
   fun getUserImgPath(type: String): String? {
      val sp = App.contextInstance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
      val path = sp.getString(getUserKey() + type, "")
      if (path.isNullOrEmpty()) return null
      return path
   }
}