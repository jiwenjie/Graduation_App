package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.model.DoubanModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/11
 *  desc:
 *  version:1.0
 */
class DoubanMoviePresenter(view: DoubanContract.DoubanMovieView)
   : BaseMvpPresenter<DoubanContract.DoubanMovieView>(view), DoubanContract.DoubanMoviePresenter {

   private val mModel by lazy { DoubanModel() }

   override fun getDoubanHotMovie() {
      mView?.showLoading()
      addSubscription(
              mModel.getDoubanHotMovie()
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.updateDoubanContentList(it.subjects)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }

   override fun getDoubanMovieDetail(id: String) {
      mView?.showLoading()
      addSubscription(
              mModel.getDoubanMovieDetail(id)
                      .map {
                         it.casts.forEach { castBean ->
                            castBean.type = "主演"
                         }
                         it.directors.forEach { castBean ->
                            castBean.type = "导演"
                         }
                         it
                      }
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.showDetail(it)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }

   override fun getDoubanMovieTop250(start: Int, count: Int) {
      if (start == 0) {
         mView?.showLoading()
      }
      addSubscription(
              mModel.getDoubanMovieTop250(start, count)
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.updateDoubanContentList(it.subjects)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}