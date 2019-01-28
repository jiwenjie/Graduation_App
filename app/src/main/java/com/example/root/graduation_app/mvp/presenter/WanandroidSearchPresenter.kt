package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.mvp.constract.WanandroidContract
import com.example.root.graduation_app.mvp.model.WanandroidModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/23
 *  desc:get hot word presenter
 *  version:1.0
 */
class WanandroidSearchPresenter(view: WanandroidContract.WanandroidSearchView):
    BaseMvpPresenter<WanandroidContract.WanandroidSearchView>(view), WanandroidContract.WanandroidSearchPresenter {

    private val mModel by lazy { WanandroidModel() }

    override fun getHotword() {
        addSubscription(
            mModel.getHotSearchData()
                .subscribe({
                    mView?.displayHotword(it.data)
                }, {
                    LogUtils.e(it)
                })
        )
    }

    /**
     * 在某个公号下搜索结果
     */
    override fun searchArticleInPublicAddress(publicId: Int, pageNum: Int, searchKey: String) {
        if (pageNum == 1) mView?.showLoading()
        addSubscription(
            mModel.searchArticleInPublicAddress(publicId, pageNum, searchKey)
                .subscribe({
                    mView?.dismissLoading()
                    mView?.displaySearchResult(it.data.datas)
                }, {
                    mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                })
        )
    }

    /**
     * 在全部公号下获取搜索结果
     */
    override fun searchArticleAll(page: Int, key: String) {
        if (page == 0) mView?.showLoading()
        addSubscription(
            mModel.getSearchResult(page, key)
                .subscribe({
                    mView?.dismissLoading()
                    mView?.displaySearchResult(it.data.datas)
                }, {
                    mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                })
        )
    }
}