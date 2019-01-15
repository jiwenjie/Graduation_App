package com.example.root.graduation_app.utils

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

//    fun getRequestOptions(): RequestOptions {
//        return RequestOptions()
//            .centerCrop()
//            .placeholder(R.drawable.placeholder_banner)
//            .error(R.mipmap.ic_error)
//    }

   fun getRequestDefaultAvatar(): RequestOptions {
      return RequestOptions()
              .placeholder(R.drawable.img_avatar)
              .circleCrop()
   }
}
















