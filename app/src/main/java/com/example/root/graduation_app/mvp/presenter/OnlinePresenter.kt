package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.OnlineMusicContract
import com.example.root.graduation_app.mvp.model.OnlineMusicModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/22
 *  desc:
 *  version:1.0
 */
class OnlinePresenter(view: OnlineMusicContract.View)
    : BaseMvpPresenter<OnlineMusicContract.View>(view), OnlineMusicContract.Presenter {

    private val mModel by lazy { OnlineMusicModel() }

    override fun getOnlineMusic(page: Int, limit: Int) {
        if (page == 1) mView?.showLoading()
        addSubscription(
            mModel.getOnlineMusicList(page, limit)
                .subscribe({
                  mView?.dismissLoading()
                    if (it.result == "succeed") {
                        mView?.showOnlineMusic(it.data)
                    } else {
                        mView?.showError(it.msg, it.code)
                    }
                }, {
                    mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                })
        )
    }
}