package com.example.base_library.base_utils

import android.util.Log

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:日志工具类，可以打印出类名-方法名-行数等信息
 *  version:1.0
 */
object LogUtils {

    private var className: String? = null
    private var methodName: String? = null
    private var lineNumber: Int? = null

    private fun isDebuggable(): Boolean = true

    private fun createLog(logMsg: String): String {
        return "$methodName($className:$lineNumber): $logMsg"
    }

    private fun getMethodName(throwable: Throwable) {
        className = throwable.stackTrace[1].fileName
        methodName = throwable.stackTrace[1].methodName
        lineNumber = throwable.stackTrace[1].lineNumber
    }

    @JvmStatic
    fun e(msg: Any?) {
        if (!isDebuggable()) return
        getMethodName(Throwable())
        if (msg == null)
            Log.e(className, createLog("null"))
        else when (msg) {
            is Int, Long, Float, Double, Boolean -> Log.e(className, createLog("$msg"))
            is String -> Log.e(className, createLog(msg))
            else -> Log.e(className, createLog(msg.toString()))
        }
    }

    @JvmStatic
    fun w(msg: Any?) {
        if (!isDebuggable()) return
        getMethodName(Throwable())
        if (msg == null)
            Log.w(className, createLog("null"))
        else when (msg) {
            is Int, Long, Float, Double, Boolean -> Log.w(className, createLog("$msg"))
            is String -> Log.w(className, createLog(msg))
            else -> Log.w(className, createLog(msg.toString()))
        }
    }

    @JvmStatic
    fun i(msg: Any?) {
        if (!isDebuggable()) return
        getMethodName(Throwable())
        if (msg == null)
            Log.i(className, createLog("null"))
        else when (msg) {
            is Int, Long, Float, Double, Boolean -> Log.i(className, createLog("$msg"))
            is String -> Log.i(className, createLog(msg))
            else -> Log.i(className, createLog(msg.toString()))
        }
    }

    @JvmStatic
    fun d(msg: Any?) {
        if (!isDebuggable()) return
        getMethodName(Throwable())
        if (msg == null)
            Log.d(className, createLog("null"))
        else when (msg) {
            is Int, Long, Float, Double, Boolean -> Log.d(className, createLog("$msg"))
            is String -> Log.d(className, createLog(msg))
            else -> Log.d(className, createLog(msg.toString()))
        }
    }

    @JvmStatic
    fun v(msg: Any?) {
        if (!isDebuggable()) return
        getMethodName(Throwable())
        if (msg == null)
            Log.v(className, createLog("null"))
        else when (msg) {
            is Int, Long, Float, Double, Boolean -> Log.v(className, createLog("$msg"))
            is String -> Log.v(className, createLog(msg))
            else -> Log.v(className, createLog(msg.toString()))
        }
    }
}