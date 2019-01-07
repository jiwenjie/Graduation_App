package com.example.root.graduation_app.mvp.presenter

import android.text.TextUtils
import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.ZhihuContract
import com.example.root.graduation_app.mvp.model.ZhihuModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:
 *  version:1.0
 */
class ZhihuPresenter(view: ZhihuContract.ZhihuView)
   : BaseMvpPresenter<ZhihuContract.ZhihuView>(view), ZhihuContract.ZhihuPresenter {

   private val mModel by lazy { ZhihuModel() }

   /**
    * 日期
    */
   private var mDate: String? = null

   override fun loadLatestList() {
      mView?.showLoading()
      addSubscription(mModel.getDailyList()
              .subscribe({
                 mView?.dismissLoading()
                 mDate = it.date
                 mView?.updateContentList(it.stories)
              }, {
                  mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
              }))

   }

   override fun loadMoreList() {
      addSubscription(
              mDate.let {
                 mModel.getDailyList(mDate!!)
                         .subscribe({ bean ->
                            mView.apply {
                               if (TextUtils.equals(mDate, bean.date))
                                  return@subscribe
                               mDate = bean.date
                               mView?.updateContentList(bean.stories)
                            }
                         }, {
                            mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                         })
              }
      )
   }
}

























