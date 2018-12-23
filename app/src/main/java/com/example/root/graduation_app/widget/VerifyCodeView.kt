package com.example.root.graduation_app.widget

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

import com.example.root.graduation_app.R
import com.example.root.graduation_app.utils.SimpleTextWatcher

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/20
 * desc:
 * version:1.0
 */
class VerifyCodeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

   private val editText: EditText
   private val textViews: Array<TextView?>
   var editContent: String? = null
      private set

   private var inputCompleteListener: InputCompleteListener? = null

   init {
      View.inflate(context, R.layout.view_verify_code, this)

      textViews = arrayOfNulls(MAX_LENGTH)    // 创建可空数组
      textViews[0] = findViewById(R.id.tv_0)
      textViews[1] = findViewById(R.id.tv_1)
      textViews[2] = findViewById(R.id.tv_2)
      textViews[3] = findViewById(R.id.tv_3)
      editText = findViewById(R.id.edit_text_view)

      editText.isCursorVisible = false//隐藏光标
      setEditTextListener()
   }

   private fun setEditTextListener() {
      editText.addTextChangedListener(object : SimpleTextWatcher() {
         override fun afterTextChanged(s: Editable?) {
            editContent = editText.text.toString()

            if (inputCompleteListener != null) {
               if (editContent!!.length >= MAX_LENGTH) {
                  inputCompleteListener!!.inputComplete()
               } else {
                  inputCompleteListener!!.invalidContent()
               }
            }

            for (i in 0 until MAX_LENGTH) {
               if (i < editContent!!.length) {
                  textViews[i]!!.text = editContent!![i].toString()
               } else {
                  textViews[i]!!.text = ""
               }
            }
         }

      })
   }

   fun setInputCompleteListener(inputCompleteListener: InputCompleteListener) {
      this.inputCompleteListener = inputCompleteListener
   }

   interface InputCompleteListener {

      fun inputComplete()

      fun invalidContent()
   }

   companion object {
      private val MAX_LENGTH = 4
   }
}