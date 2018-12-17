package com.example.base_library.base_mvp

import android.os.Bundle
import com.example.base_library.base_views.BaseActivity

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:
 *  version:1.0
 */
abstract class BaseMvpActivity<V : BaseMvpViewImpl, P : BaseMvpPresenter<V>> : BaseActivity() {

    protected lateinit var mPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        super.onCreate(savedInstanceState)
        /* 注册 lifecycle */
        lifecycle.addObserver(mPresenter)
    }

    abstract fun initPresenter(): P
}