package com.example.root.graduation_app.mvp.presenter

import android.text.TextUtils
import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.mvp.constract.WangyiContract
import com.example.root.graduation_app.mvp.model.WangyiModel
import com.example.root.graduation_app.utils.CommonUtils

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/08
 *  desc:
 *  version:1.0
 */
class WangyiPresenter(view: WangyiContract.WangyiView) : BaseMvpPresenter<WangyiContract.WangyiView>(view),
    WangyiContract.WangyiPresenter {

    private val mModel by lazy { WangyiModel() }

    override fun loadLatestList(currentIndex: Int) {
        if (currentIndex == 0) {
            mView?.showLoading()
        }
        addSubscription(
            mModel.getNewsList(currentIndex)
                .map {
//                    it.newsList.forEach {  item ->
////                        if (item.url == null || item.url == "") {
////                            it.newsList.remove(item)
////                        }
////                    }
                    it
                }
                .subscribe({

                    mView?.dismissLoading()
//                    it.newsList.forEach { itemBean ->
//                        if (itemBean.url.isNullOrEmpty()) {
//                            it.newsList.remove(itemBean)
//                        }
//                    }
                    LogUtils.e("PPPPP" + it.newsList)
                    mView?.updateContentList(it.newsList)
                }, {
                    mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                })
        )
    }
}

