package com.example.root.graduation_app.widget

import android.content.Context
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MotionEvent

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/29
 *  desc:用于需要透明化显示的Toolbar。使用这种Toolbar后，即使被Toolbar盖住的区域依然可以点击，解决了被Toolbar盖住的部分明明
 * 看得到，却无法点击的问题。
 *  version:1.0
 */
class TransparentToolbar : Toolbar {

   constructor(context: Context) : super(context) {}

   constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

   constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

   override fun onTouchEvent(ev: MotionEvent): Boolean {
      return false
   }
}
