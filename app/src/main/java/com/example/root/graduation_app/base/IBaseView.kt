package com.example.root.graduation_app.base

import com.example.base_library.base_mvp.BaseMvpViewImpl

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/11/06
 *  desc:
 *  version:1.0
 */
interface IBaseView : BaseMvpViewImpl {

    fun showLoading()

    fun dismissLoading()
}






