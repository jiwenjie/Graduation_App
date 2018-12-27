package com.example.root.graduation_app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/26
 *  desc:
 *  version:1.0
 */
class NoScrollViewPager(context: Context, attr: AttributeSet) : ViewPager(context, attr) {

   override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
      return false
   }

   @SuppressLint("ClickableViewAccessibility")
   override fun onTouchEvent(ev: MotionEvent?): Boolean {
      return false
   }
}