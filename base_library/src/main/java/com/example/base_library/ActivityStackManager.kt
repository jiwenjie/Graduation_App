package com.example.base_library

import android.app.Activity

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:活动栈的管理
 *  version:1.0
 */
object ActivityStackManager {

    private val activities = ArrayList<Activity>()

    /**
     * onCreate 调用
     */
    @JvmStatic
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    /**
     * onDestroy 调用
     */
    @JvmStatic
    fun removeActivity(activity: Activity) {
        if (activities.contains(activity)) {
            activities.remove(activity)
            activity.finish()
        }
    }

    /**
     * 获取栈顶 Activity
     */
    @JvmStatic
    fun getTopActivity(): Activity? = if (activities.isEmpty()) null else activities[activities.size - 1]

    /**
     * 关闭所有的 Activity
     */
    @JvmStatic
    fun finishAll() {
        for (a in activities) {
            if (!a.isFinishing) a.finish()
        }
    }

}


