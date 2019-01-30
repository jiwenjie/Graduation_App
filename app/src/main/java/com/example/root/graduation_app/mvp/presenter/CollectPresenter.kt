package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.CollectContract
import com.example.root.graduation_app.mvp.model.CollectModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/28
 *  desc:
 *  version:1.0
 */
class CollectPresenter(view: CollectContract.View)
   : BaseMvpPresenter<CollectContract.View>(view), CollectContract.Presenter {

   private val mModel by lazy { CollectModel() }

   override fun requestCollectData(page: Int, limit: Int) {
      mView?.showLoading()
      addSubscription(
              mModel.requestCollectData(page, limit)
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.displayCollectData(it.data.datas)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}