package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.WanandroidContract
import com.example.root.graduation_app.mvp.model.WanandroidModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/22
 *  desc:
 *  version:1.0
 */
class WanandroidDataPresenter(view: WanandroidContract.WanandroidDataView) : BaseMvpPresenter<WanandroidContract.WanandroidDataView>(view),
        WanandroidContract.WanandroidDataPresenter {

   private val mModel by lazy { WanandroidModel() }

   override fun getOnePublicAddressHistory(publicId: Int, pageNum: Int) {
      if (pageNum == 1) mView?.showLoading()
      addSubscription(
              mModel.getOnePublicAddressHistory(publicId, pageNum)
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.displayPublicHistory(it.data.datas)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}