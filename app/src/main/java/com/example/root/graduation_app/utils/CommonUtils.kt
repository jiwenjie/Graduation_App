package com.example.root.graduation_app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanCastsBean
import com.zhouwei.blurlibrary.EasyBlur
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/23
 *  desc:一些公共的方法封装
 *  version:1.0
 */
object CommonUtils {

   /**
    * 因为在输入号码的时候增加了空格，所以获取使用的时候需要把空格格式化
    */
   fun formatPhoneNum(phoneNum: String?): String? {
      val regex = "(\\+86)|[^0-9]"
      val pattern = Pattern.compile(regex)
      val matcher = pattern.matcher(phoneNum)
      return matcher.replaceAll("")
   }

   /**
    * 前端过滤下数字是十一位但不是手机号码的
    */
   fun isMobileNO(mobileNum: String): Boolean {
      /**
       * 判断字符串是否符合手机号码格式
       * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
       * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
       * 电信号段: 133,149,153,170,173,177,180,181,189
       * @param str
       * @return 待检测的字符串
       */
      // "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
      val telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$"
      return if (TextUtils.isEmpty(mobileNum))
         false
      else
         mobileNum.matches(telRegex.toRegex())
   }

   /**
    * 检查用户昵称是否合法的正式表达式。
    */
   const val NICK_NAME_REG_EXP = "^[\u4E00-\u9FA5A-Za-z0-9_\\-]+$"

   /**
    * 拼接 movie 中 bean 的名字
    */
   fun splicingString(authers: ArrayList<String>): String {
      var name = ""
      return when {
         authers.isEmpty() -> name
         authers.size == 1 -> authers[0]
         else -> {
            name = authers[0]

            authers.forEach {
               name = " / $it"
            }
            name
         }
      }
   }


   /**
    * 格式化list为字符串
    *
    * @param list 类型list
    * @return 字符串 A/B/C..
    */
   fun splicingAuthor(authers: ArrayList<DoubanCastsBean>): String {
      var name = ""
      return when {
         authers.isEmpty() -> name
         authers.size == 1 -> authers[0].name
         else -> {
            name = authers[0].name

            authers.forEach {
               name = " / ${it.name}"
            }
            name
         }
      }
   }

   /**
    * 添加公共的格式化日期时间的方法
    */
   fun getFormatDateTime(date: Date): String {
       val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
       return dateFormat.format(date)
   }

   /**
    * 检查 String 是否为 null
    */
   fun isEmpty(value: String?): Boolean {
      return !(value != null && !"".equals(value.trim { it <= ' ' }, ignoreCase = true)
              && !"null".equals(value.trim { it <= ' ' }, ignoreCase = true))
   }

   /**
    * 展示虚化图片
    */
   fun displayBlurImg(context: Context, url: String, imageView: ImageView) {
      Glide.with(context)
         .load(url)
         .apply(RequestOptions.getRequestOptions())
         .transition(DrawableTransitionOptions().crossFade(300))
         .thumbnail(0.5f)   // 缩略图
         .into(getTarget(context, imageView))
   }

   /**
    * 展示网络加载图片
    */
   fun displayImg(context: Context, url: String, imageView: ImageView) {
      Glide.with(context)
              .load(url)
              .apply(RequestOptions.getRequestOptions())
              .transition(DrawableTransitionOptions().crossFade(300))
              .thumbnail(0.5f)   // 缩略图
              .into(imageView)
   }

   fun displayImgAsBitmap(context: Context, url: String, imageView: ImageView) {
      Glide.with(context)
              .asBitmap()
              .load(url)
              .apply(RequestOptions.getRequestOptions())
              .transition(BitmapTransitionOptions().crossFade(300))
              .thumbnail(0.5f)   // 缩略图
              .into(imageView)
   }

   @SuppressLint("CheckResult")
   fun displayBgAsBitmap(context: Context, url: String, imageView: ImageView) {
      Glide.with(context)
         .asBitmap()
         .load(url)
         .apply(RequestOptions.getRequestOptions())
         .transition(BitmapTransitionOptions().crossFade(300))
         .thumbnail(0.5f)   // 缩略图
         .listener(object : RequestListener<Bitmap> {
            override fun onLoadFailed(
               e: GlideException?,
               model: Any?,
               target: Target<Bitmap>?,
               isFirstResource: Boolean
            ): Boolean {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResourceReady(
               resource: Bitmap?,
               model: Any?,
               target: Target<Bitmap>?,
               dataSource: DataSource?,
               isFirstResource: Boolean
            ): Boolean {
               TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
         })
//         .into(imageView)
   }

   fun displayImgAsGif(context: Context, url: String, imageView: ImageView) {
      Glide.with(context)
              .asGif()
              .load(url)
              .apply(RequestOptions.getRequestOptions())
              .transition(DrawableTransitionOptions().crossFade(300))
              .thumbnail(0.5f)   // 缩略图
              .into(imageView)
   }

   /**
    * 获取图片的 target,
    * when you not want to set image to imageView, use it
    */
   private fun getTarget(context: Context, imageView: ImageView): Target<Drawable> {
      return object : SimpleTarget<Drawable>() {
         override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            val bitmapDrawable = resource as BitmapDrawable
            val bitmapSource = bitmapDrawable.bitmap    // 获取 bitmap
            val blurBitmap = EasyBlur.with(context)  // 得到模糊的 bitmap
               .bitmap(bitmapSource)
               .radius(20)
               .blur()
            imageView.setImageBitmap(blurBitmap)
         }

         override fun onLoadFailed(errorDrawable: Drawable?) {
            super.onLoadFailed(errorDrawable)
            val source = BitmapFactory.decodeResource(context.resources, R.drawable.load_pic_error)
            val errorBitmap = EasyBlur.with(context)  // 得到模糊的 bitmap
               .bitmap(source)
               .radius(20)
               .blur()
            imageView.setImageBitmap(errorBitmap)
         }
      }
   }

   /**
    * 隐藏软键盘
    */
   fun hideKeyboard(context: Context, view: View) {
      val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
   }
}