package com.example.root.graduation_app.mvp.presenter

import android.text.TextUtils
import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.mvp.constract.WangyiContract
import com.example.root.graduation_app.mvp.model.WangyiModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/08
 *  desc:
 *  version:1.0
 */
class WangyiPresenter(view: WangyiContract.WangyiView) : BaseMvpPresenter<WangyiContract.WangyiView>(view),
        WangyiContract.WangyiPresenter {

   private val mModel by lazy { WangyiModel() }

   override fun loadLatestList(currentIndex: Int) {
      if (currentIndex == 0) {
         mView?.showLoading()
      }
      addSubscription(
              mModel.getNewsList(currentIndex)
                      .subscribe({
                         LogUtils.e(it.newsList)
                         mView?.dismissLoading()
                         val list = it.newsList
                         // 过滤无效的返回值
                         list.forEach { bean ->
                            if (TextUtils.isEmpty(bean.url)) list.remove(bean)
                         }
                         mView?.updateContentList(list)
                      }, {
                         LogUtils.e(it.cause)
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      }))
   }
}














