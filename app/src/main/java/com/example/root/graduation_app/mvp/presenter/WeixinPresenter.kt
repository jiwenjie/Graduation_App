package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.mvp.constract.WeixinContract
import com.example.root.graduation_app.mvp.model.WeixinModel
import com.example.root.graduation_app.utils.Constants

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
      mView?.showLoading()
      addSubscription(
          mModel.getWeixinChoiceList(page, pageStrip, key)
              .subscribe({
                 mView?.dismissLoading()
                 LogUtils.e(it.result)
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