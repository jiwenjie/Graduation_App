package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.KnowledgeTreeContract
import com.example.root.graduation_app.mvp.model.KnowledgeTreeModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
class KnowledgeTreePresenter(view: KnowledgeTreeContract.View)
    : BaseMvpPresenter<KnowledgeTreeContract.View>(view), KnowledgeTreeContract.Presenter {

    private val mModel by lazy { KnowledgeTreeModel() }

    override fun requestKnowledgeTree() {
        mView?.showLoading()
        addSubscription(
            mModel.requestKnowledgeTree()
                .subscribe({
                    mView?.dismissLoading()
                    mView?.setKnowledgeTree(it.data)
                }, {
                    mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                })
        )
    }
}