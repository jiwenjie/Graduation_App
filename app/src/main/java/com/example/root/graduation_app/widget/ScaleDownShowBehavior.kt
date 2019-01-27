package com.example.root.graduation_app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:FAB 行为控制器
 *  version:1.0
 */
class ScaleDownShowBehavior(context: Context, attrs: AttributeSet) : FloatingActionButton.Behavior(context, attrs) {

   override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                    child: FloatingActionButton,
                                    directTargetChild: View,
                                    target: View,
                                    axes: Int,
                                    type: Int): Boolean {
      return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
              super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
   }

   @SuppressLint("RestrictedApi")
   override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
      super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
      if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
         child.visibility = View.INVISIBLE
      } else if (dyConsumed < 0 && child.visibility != View.VISIBLE) {
         child.show()
      }
   }
}