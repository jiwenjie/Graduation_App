package com.example.root.graduation_app.widget

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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

   private var animComplete = false    // 标志动画是否完成，默认 false

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
      if (dyConsumed > 0 && !animComplete) {
         startAnimation(child, 1f, 0f)
         animComplete = true
      } else if (dyConsumed < 0 && animComplete) {
         startAnimation(child, 0f, 1f)
         animComplete = false
      }
   }

   private fun startAnimation(child: FloatingActionButton, startX: Float, endY: Float) {
      val animObjX = ObjectAnimator.ofFloat(child, "scaleX", startX, endY)
      val animObjY = ObjectAnimator.ofFloat(child, "scaleY", startX, endY)
      val animatorSet = AnimatorSet()
      animatorSet.playTogether(animObjX, animObjY)
      animatorSet.duration = 200
      animatorSet.start()
   }
}