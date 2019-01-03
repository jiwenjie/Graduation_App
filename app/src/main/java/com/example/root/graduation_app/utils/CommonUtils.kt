package com.example.root.graduation_app.utils

import android.text.TextUtils
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
}