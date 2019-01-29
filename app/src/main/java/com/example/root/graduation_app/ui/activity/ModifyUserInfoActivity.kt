package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_modify_user_info.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/29
 *  desc:修改用户信息的活动
 *  version:1.0
 */
class ModifyUserInfoActivity : BaseActivity() {

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, ModifyUserInfoActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@ModifyUserInfoActivity,
              ContextCompat.getColor(this@ModifyUserInfoActivity, R.color.colorPrimary), 0)
      initEvent()
   }

   private fun initEvent() {
      activity_modify_user_info_return.setOnClickListener {
         finish()
      }

      save.setOnClickListener {
         // 保存按钮
      }

      userAvatar.setOnClickListener {
         // 点击拍照或者选择相册（选择头像图片）

      }

      bgImageCamera.setOnClickListener {
         // 选择封面图片，拍照，你的相册
      }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_modify_user_info
}