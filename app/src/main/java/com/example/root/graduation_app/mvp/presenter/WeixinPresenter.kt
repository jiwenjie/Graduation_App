package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.WeixinContract
import com.example.root.graduation_app.mvp.model.WeixinModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/09
 *  desc:
 *  version:1.0
 */
class WeixinPresenter(view: WeixinContract.WeixinView): BaseMvpPresenter<WeixinContract.WeixinView>(view),
         WeixinContract.WeixinPresenter {

   private val mModel by lazy { WeixinModel() }

   override fun loadLatestList(page: Int, pageStrip: Int, key: String) {
      if (page == 1) {
         mView?.showLoading()
      }
      addSubscription(
          mModel.getWeixinChoiceList(page, pageStrip, key)
              .subscribe({
                 mView?.dismissLoading()
                 if (it.error_code == "0") {
                    mView?.updateContentList(it.result.list)
                 } else {
                    mView?.showNetWorkError()
                 }
              }, {
                 mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
              }))
   }

}