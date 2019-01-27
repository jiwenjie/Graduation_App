package com.example.root.graduation_app.mvp.presenter

import android.content.Context
import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.KnowledgeContract
import com.example.root.graduation_app.mvp.model.KnowledgeModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
class KnowledgePresenter(view: KnowledgeContract.View)
   : BaseMvpPresenter<KnowledgeContract.View>(view), KnowledgeContract.Presenter {

   private val mModel by lazy { KnowledgeModel() }

   override fun requestKnowledgeList(page: Int, cid: Int) {
      if (page == 0) mView?.showLoading()
      addSubscription(
              mModel.requestKnowledgeList(page, cid)
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.setKnowledgeList(it.data)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}