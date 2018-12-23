package com.example.root.graduation_app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.util.AttributeSet
import android.view.MotionEvent

import java.util.Objects

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/20
 * desc:
 * version:1.0
 */
class MyEditText : AppCompatEditText {

   private var lastTime: Long = 0

   constructor(context: Context) : super(context)

   constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

   constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

   override fun onSelectionChanged(selStart: Int, selEnd: Int) {
      super.onSelectionChanged(selStart, selEnd)
      this.setSelection(Objects.requireNonNull<Editable>(this.text).length)
   }

   @SuppressLint("ClickableViewAccessibility")
   override fun onTouchEvent(event: MotionEvent): Boolean {
      when (event.action) {
         MotionEvent.ACTION_DOWN -> {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastTime < 500) {
               lastTime = currentTime
               return true
            } else {
               lastTime = currentTime
            }
         }
      }
      return super.onTouchEvent(event)
   }
}
