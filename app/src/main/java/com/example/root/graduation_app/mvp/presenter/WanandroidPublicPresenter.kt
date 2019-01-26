package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.mvp.constract.WanandroidContract
import com.example.root.graduation_app.mvp.model.WanandroidModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/22
 *  desc:
 *  version:1.0
 */
class WanandroidPublicPresenter(view: WanandroidContract.WanandroidPublicView) : BaseMvpPresenter<WanandroidContract.WanandroidPublicView>(view),
        WanandroidContract.WanandroidPublicPresenter {

   private val mModel by lazy { WanandroidModel() }

   override fun getPublicAddressList() {
      addSubscription(
              mModel.getPublicAddressList()
                      .subscribe ({
                         mView?.displayPublicNumList(it.data)
                      }, {
                          LogUtils.e(it)
                      })
      )
   }
}