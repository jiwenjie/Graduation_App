package com.example.root.graduation_app.widget

import android.content.Context
import com.example.base_library.base_views.BaseDialog
import com.example.root.graduation_app.R
import kotlinx.android.synthetic.main.dialog_sign_up.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/31
 *  desc:签到的 dialog
 *  version:1.0
 */
class SignDialog(context: Context, type: Int) : BaseDialog(context) {

   private var typeNo: Int? = null    // 0 表示第一次登陆，1 表示退出后再次登陆，2 表示未退出只是再次打开 APP

   init {
      typeNo = type
   }

   override fun getLayoutId(): Int = R.layout.dialog_sign_up

   override fun initDialogView() {

      when (typeNo) {
         0 -> {
            dialog_contextText.text = ""
         }
         1 -> {
            dialog_contextText.text = ""
         }
         2 -> {
            dialog_contextText.text = ""
         }
         else -> {
            dialog_contextText.text = ""
         }
      }

      dialog_textBtn.setOnClickListener {
         dismiss()
      }
   }
}