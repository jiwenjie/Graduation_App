package com.example.root.graduation_app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.bean.WanAndroidItem

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/11
 *  desc:
 *  version:1.0
 */
object PhoneUserUtils {

   /**
    * 操作接口
    */
   interface operationListener {
      fun success(user: LoginUser)

      fun failed(error: String)
   }

   @SuppressLint("CheckResult")
   @JvmStatic
   fun loginByPhone(userPhone: String, userPass: String, listener: operationListener) {
      RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .loginByUserPhone(userPhone, userPass)
              .compose(RxJavaUtils.applyObservableAsync())
              .subscribe({
                 if (it.result == "succeed") {
                    listener.success(it.data)
                 } else {
                    listener.failed(it.msg)
                 }
              }, {
                 listener.failed(it.message!!)
              })
   }

   /**
    * 通过 userid 获取最新的用户数据，即时更新
    */
   @SuppressLint("CheckResult")
   @JvmStatic
   fun getUptoDateUser(userId: String, listener: operationListener) {
      RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .getUserInfo(userId)
              .subscribe({
                 if (it.result == "succeed") {
                    listener.success(it.data)
                 } else {
                    listener.failed(it.msg)
                 }
              }, {
                 listener.failed(it.message.toString())
              })
   }

   /**
    * 有关收藏文章部分的接口
    */
   interface collectArticleInterface {
      fun success(msg: String)

      fun failed(error: String)
   }

   /**
    * 当收藏文章调用该方法
    */
   @SuppressLint("CheckResult")
   @JvmStatic
   fun collectArticle(phoneUserId: String, bean: WanAndroidItem, listener: collectArticleInterface) {
      RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .collectArticle(phoneUserId, bean.apkLink, bean.author, bean.chapterId, bean.chapterName,
                      bean.collect, bean.courseId, bean.desc, bean.envelopePic, bean.fresh, bean.id, bean.link, bean.niceDate,
                      bean.origin, bean.projectLink, bean.publishTime, bean.superChapterId, bean.superChapterName,
                      bean.title, bean.type, bean.userId, bean.visible, bean.zan)
              .compose(RxJavaUtils.applyObservableAsync())
              .subscribe({
                  if (it.result == "succeed") {
                     listener.success(it.msg)   // 此时是成功的提示
                  } else {
                     listener.failed(it.msg) // 这里是失败的信息提示
                  }
              }, {
                 listener.failed(it.message.toString()) // 这里是失败的信息提示
              })
   }

   /**
    * 取消收藏文章的时候调用该方法
    */
   @SuppressLint("CheckResult")
   @JvmStatic
   fun cancelCollect(userId: String, articleId: Int, listener: collectArticleInterface) {
      RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .cancelCollect(userId, articleId)
              .compose(RxJavaUtils.applyObservableAsync())
              .subscribe({
                  if (it.result == "succeed") {
                     listener.success(it.msg)   // 此时是成功的提示
                  } else {
                     listener.failed(it.msg) // 这里是失败的信息提示
                  }
              }, {
                 listener.failed(it.message.toString()) // 这里是失败的信息提示
              })
   }

   /**
    * 注册页面调用，输入手机号调用接口
    */
   fun registerUserPhone() {

   }

    /**
     * 加载头像
     */
    fun loadAvatar(context: Context, avatarPath: String, avatar: ImageView) {
        Glide.with(context)
            .load(ConstantConfig.JACKSON_BASE_URL + avatarPath)
            .apply(RequestOptions.getAvatar())
            .into(avatar)
    }
}