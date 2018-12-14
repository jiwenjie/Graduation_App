package com.example.base_library.base_mvp

import android.os.Bundle
import android.view.View
import com.example.base_library.base_views.BaseFragment

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:
 *  version:1.0
 */
abstract class BaseMvpFragment<V : BaseMvpViewImpl, P : BaseMvpPresenter<V>> : BaseFragment() {

    protected lateinit var mPresenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter = initPresenter()
        lifecycle.addObserver(mPresenter)
    }

    abstract fun initPresenter(): P
}