package com.example.root.graduation_app.widget

import android.annotation.SuppressLint
import android.content.Context
import com.example.root.graduation_app.utils.SharePreferencesUtil
import com.example.base_library.base_views.BaseDialog
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.utils.ConstantConfig
import kotlinx.android.synthetic.main.dialog_sign_up.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/31
 *  desc:签到的 dialog
 *  version:1.0
 */
class SignDialog(context: Context) : BaseDialog(context) {

//   private var typeNo: Int? = null    // 0 表示第一次登陆，1 表示退出后再次登陆，2 表示未退出只是再次打开 APP
//
//   init {
//      typeNo = type
//   }

   override fun getLayoutId(): Int = R.layout.dialog_sign_up

   @SuppressLint("SetTextI18n")
   override fun initDialogView() {

      val user = SharePreferencesUtil.getAny(mContext, ConstantConfig.SHARE_LOGIN_USER_NAME) as LoginUser
      dialog_contextText.text = "${user.username}您好：\n我们欢迎您的使用，这里表示感谢。\n" +
              "您已经连续签到${user.continuesigndays}天。\n" +
              "一共签到了${user.signintotaldays}天。" + "在使用我们软件学习的过程中，您一共收藏了${user.collectioncount}篇技术文章，" +
              "祝您越来越好。我们也会继续努力不断提升改进。"
//      when (typeNo) {
//         0 -> {
//            dialog_contextText.text = ""
//         }
//         1 -> {
//            dialog_contextText.text = ""
//         }
//         2 -> {
//            dialog_contextText.text = ""
//         }
//         else -> {
//            dialog_contextText.text = ""
//         }
//      }

      dialog_textBtn.setOnClickListener {
         dismiss()
      }
   }
}