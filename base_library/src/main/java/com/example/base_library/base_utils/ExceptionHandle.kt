package com.example.base_library.base_utils

import com.google.gson.JsonParseException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:异常处理类
 *  version:1.0
 */
class ExceptionHandle {

   companion object {
      var errorCode = ErrorStatus.UNKNOWN_ERROR
      var errorMsg = "请求失败，请稍后再试"

      fun handleException(e: Throwable): String {
         e.printStackTrace()
         if (e is SocketTimeoutException) { // connect timeout
            LogUtils.e("网络连接异常：" + e.message)
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
         } else if (e is ConnectException) {
            // 视为网络错误
            LogUtils.e("网络连接异常：" + e.message)
            errorMsg = "网络连接异常"
            errorCode = ErrorStatus.NETWORK_ERROR
         } else if (e is JsonParseException
                     || e is JSONException
                     || e is ParseException) {
            // parse error
            LogUtils.e("数据解析异常：" + e.message)
            errorCode = ErrorStatus.SERVER_ERROR
            errorMsg = "数据解析异常"
         } else if (e is UnknownHostException) {
            LogUtils.e("网络连接异常：" + e.message)
            errorCode = ErrorStatus.NETWORK_ERROR
            errorMsg = "网络连接异常"
         } else if (e is IllegalArgumentException) {
            errorMsg = "参数错误"
            errorCode = ErrorStatus.SERVER_ERROR
         } else {
            // 未知错误
            try {
               LogUtils.e("错误：" + e.message)
            } catch (e: Exception) {
               LogUtils.e("未知错误 Debug 调试")
            }
            errorMsg = "未知错误，可能抛锚了吧"
            errorCode = ErrorStatus.UNKNOWN_ERROR
         }
         return errorMsg
      }
   }

}































