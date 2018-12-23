package com.example.root.graduation_app.utils

import java.util.regex.Matcher
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
}