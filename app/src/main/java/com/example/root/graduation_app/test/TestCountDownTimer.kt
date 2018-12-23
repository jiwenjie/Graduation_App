package com.example.root.graduation_app.test

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.widget.Button

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/22
 * desc:
 * version:1.0
 */
class TestCountDownTimer : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      val button = Button(applicationContext)

      val countDownTimer = object : CountDownTimer(10000, 1000) {
         override fun onTick(millisUntilFinished: Long) {
            //让按钮不能被点击
            button.isEnabled = false
            //设置显示倒计时时间
            button.text = (millisUntilFinished / 1000).toString() + "秒"
         }

         override fun onFinish() {
            //倒计时结束之后恢复text显示
            button.text = "开始倒计时"
            //让按钮重新可以点击
            button.isEnabled = true
         }
      }
      //开始倒计时
      countDownTimer.start()
   }
}
