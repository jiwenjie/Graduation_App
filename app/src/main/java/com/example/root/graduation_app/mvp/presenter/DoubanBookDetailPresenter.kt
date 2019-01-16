package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.model.DoubanModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/16
 *  desc:
 *  version:1.0
 */
class DoubanBookDetailPresenter(view: DoubanContract.DoubanBookDetailView)
   : BaseMvpPresenter<DoubanContract.DoubanBookDetailView>(view), DoubanContract.DoubanBookDetailPresenter {

   private val mModel by lazy { DoubanModel() }

   override fun getBookDetail(id: String) {
      mView?.showLoading()
      addSubscription(
              mModel.getBookDetail(id)
                      .subscribe({
                         mView?.dismissLoading()
                         LogUtils.e(it.title)
                         mView?.getBookDetail(it)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}