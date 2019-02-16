package com.example.base_library.base_views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.base_library.R
import com.example.base_library.base_utils.LogUtils
import com.jaeger.library.StatusBarUtil

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:fragment 基类
 *  version:1.0
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        LogUtils.e("onCreateView()")
        return inflater.inflate(getLayoutId(), container, false)
    }

    /**
     * 多种状态的 View 切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtils.e("onViewCreated()")
        initFragment(savedInstanceState)
        StatusBarUtil.setColor(activity, ContextCompat.getColor(activity!!, R.color.colorPrimary), 0)
        //多种状态切换的view 重试点击事件
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
        loadData()
        setListener()
        handleRxBus()
    }

    abstract fun loadData()

    protected abstract fun getLayoutId(): Int

    protected abstract fun initFragment(savedInstanceState: Bundle?)

    protected open fun setListener() {}

    protected open fun handleRxBus() {}

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        loadData()
    }

    fun startActivity(clazz: Class<*>) = activity!!.startActivity(Intent(activity, clazz))
}