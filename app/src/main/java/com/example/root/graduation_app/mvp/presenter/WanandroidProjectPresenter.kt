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
class WanandroidProjectPresenter(view: WanandroidContract.WanandroidProjectView): BaseMvpPresenter<WanandroidContract.WanandroidProjectView>(view),
      WanandroidContract.WanandroidProjectPresenter {

   private val mModel by lazy { WanandroidModel() }

   override fun getProjectList(page: Int) {
      mView?.showLoading()
      addSubscription(
              mModel.getProjectList(page)
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.displayLatestProject(it.data.datas)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}