package com.example.root.graduation_app.utils

import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.root.graduation_app.R


/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/11/16
 *  desc:
 *  version:1.0
 */
object RequestOptions {

   fun getRequestOptions(): RequestOptions {
      return RequestOptions()
              .centerCrop()
              .placeholder(R.drawable.placeholder_banner)
              .error(R.mipmap.ic_error)
   }

   fun getRequestDefaultAvatar(): RequestOptions {
      return RequestOptions()
              .placeholder(R.drawable.img_avatar)
              .circleCrop()
   }

   // 因为从之前从本地读取数据，所以不需要占位符了
   fun getAvatar(): RequestOptions {
      return RequestOptions()
              .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//              .placeholder(R.drawable.loading_bg_circle)
//              .error(R.drawable.avatar_default)
   }
}
















