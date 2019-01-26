package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.WanandroidApi
import com.example.root.graduation_app.bean.*
import com.example.root.graduation_app.mvp.constract.WanandroidContract
import com.example.root.graduation_app.utils.Constants
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/22
 *  desc:
 *  version:1.0
 */
class WanandroidModel : WanandroidContract.WanandroidModel {

   override fun getPublicAddressList(): Observable<WanAndroidListBean<WanAndroidPublicItemBean>> {
      return RetrofitManager.provideClient(Constants.WANWANDROID_URL)
              .create(WanandroidApi::class.java)
              .getPublicAddressList()
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getOnePublicAddressHistory(publicId: Int, pageNum: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>> {
      return RetrofitManager.provideClient(Constants.WANWANDROID_URL)
              .create(WanandroidApi::class.java)
              .getOnePublicAddressHistory(publicId, pageNum)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun searchArticleInPublicAddress(publicId: Int, pageNum: Int, searchKey: String): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>> {
      return RetrofitManager.provideClient(Constants.WANWANDROID_URL)
              .create(WanandroidApi::class.java)
              .searchArticleInPublicAddress(publicId, pageNum, searchKey)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getProjectList(page: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>> {
      return RetrofitManager.provideClient(Constants.WANWANDROID_URL)
              .create(WanandroidApi::class.java)
              .getProjectList(page)
              .compose(RxJavaUtils.applyObservableAsync())
   }

    override fun getHotWord(): Observable<WanAndroidListBean<WanandroidHotkeyword>> {
        return RetrofitManager.provideClient(Constants.WANWANDROID_URL)
            .create(WanandroidApi::class.java)
            .getHotkeyWord()
            .compose(RxJavaUtils.applyObservableAsync())
    }
}