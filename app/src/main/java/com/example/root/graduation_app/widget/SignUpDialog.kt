package com.example.root.graduation_app.widget

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import kotlinx.android.synthetic.main.dialog_sign_tips.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/03/01
 *  desc:
 *  version:1.0
 */
class SignUpDialog(context: Context) : Dialog(context, R.style.AlertDialogStyle) {

   protected val mContext = context

   @SuppressLint("SetTextI18n")
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.dialog_sign_tips)

      val user = App.getLoginUser()
      if (user != null) {
         val tipPart: String? = if (Integer.parseInt(user.collectioncount) > 0) {
            "在使用我们软件学习的过程中，您一共收藏了${user.collectioncount}篇技术文章，"
         } else {
            "您还没有收藏文章，快去学习吧。记住博观而约取，厚积而勃发，"
         }

         dialog_contextText.text = "${user.username}：\n您好，我们欢迎您的使用，这里表示感谢。\n" +
                 "您已经连续签到${user.continuesigndays}天。\n" +
                 "一共签到了${user.signintotaldays}天。" + tipPart +
                 "祝您越来越好。我们也会继续努力不断提升改进。"
      } else {
         dialog_contextText.text = "我们团队感谢您的使用，希望你能够学有所得，学有所想。\n" +
                 "坚持是恒久的享受，愿我们都能不忘初心，终生学习"
      }

      dialog_textBtn.setOnClickListener {
         dismiss()
      }
   }
}