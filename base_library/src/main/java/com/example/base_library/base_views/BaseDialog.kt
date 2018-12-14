package com.example.base_library.base_views

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.example.base_library.R

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:
 *  version:1.0
 */
abstract class BaseDialog(context: Context, themeId: Int = R.style.AlertDialogStyle):
        Dialog(context, themeId) {

    protected val mContext = context
    private val mInflater = LayoutInflater.from(mContext)
    protected val mDialogView: View

    private var mProportion: Double = 0.0

    init {
        mDialogView = mInflater.inflate(this.getLayoutId(), null, false)
        this.initDialogView()
        this.setContentView(mDialogView)
        this.setDialogHeight(WindowManager.LayoutParams.WRAP_CONTENT)
        if (mProportion > 0 && mProportion <= 1) this.setDialogWidth((mContext.resources.displayMetrics.widthPixels * mProportion).toInt())
    }

    fun setProportion(proportion: Double) {
        this.mProportion = proportion
    }

    open fun setDialogHeight(height: Int) {
        val lp = window!!.attributes
        lp.height = height
        window!!.attributes = lp
    }

    open fun setDialogWidth(width: Int) {
        val lp = window!!.attributes
        lp.width = width
        window!!.attributes = lp
    }

    abstract fun getLayoutId(): Int

    protected open fun initDialogView() {}
}










