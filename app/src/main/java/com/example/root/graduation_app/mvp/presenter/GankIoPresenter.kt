package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.mvp.constract.GankIoContract
import com.example.root.graduation_app.mvp.model.GankIoModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/17
 *  desc:gankIo 的 presenter
 *  version:1.0
 */
class GankIoPresenter(view: GankIoContract.GankIoView): BaseMvpPresenter<GankIoContract.GankIoView>(view),
        GankIoContract.GankIoPresenter {

    private val mModel by lazy { GankIoModel() }

    override fun getGankIoDayMobile(type: String, prePage: Int, page: Int) {
        if (page == 1) {
            mView?.showLoading()
        }
        addSubscription(
            mModel.getGankIoDayMobile(type, prePage, page)
                .subscribe({
                    mView?.dismissLoading()
                    LogUtils.e("GGGG" + it.results)
                    mView?.updateViewList(it.results)
                }, {
                    mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                })
        )
    }
}