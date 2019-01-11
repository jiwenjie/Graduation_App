package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.model.DoubanModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc:
 *  version:1.0
 */
class DoubanBookPresenter(view: DoubanContract.DoubanBookView) : BaseMvpPresenter<DoubanContract.DoubanBookView>(view),
        DoubanContract.DoubanBookPresenter {

   private val mModel by lazy { DoubanModel() }

   override fun loadBookList(tag: String, start: Int, count: Int) {
      mView?.showLoading()
      addSubscription(
              mModel.getBookListWithTag(tag, start, count)
                      .subscribe({
                         mView?.dismissLoading()
                         LogUtils.e(it.books)
                         mView?.updateDoubanContentList(it.books)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}