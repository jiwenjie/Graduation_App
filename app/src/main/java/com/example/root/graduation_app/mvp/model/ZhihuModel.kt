package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.ZhihuApi
import com.example.root.graduation_app.bean.ZhihuDailyListBean
import com.example.root.graduation_app.mvp.constract.ZhihuContract
import com.example.root.graduation_app.utils.Constants
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:知乎请求数据的 model
 *  version:1.0
 */
class ZhihuModel : ZhihuContract.ZhihuModel {

   override fun getDailyList(): Observable<ZhihuDailyListBean> {
      RetrofitManager.setBaseUrl(Constants.ZHIHU_BASE_URL)
      return RetrofitManager.mRetrofit.create(ZhihuApi::class.java)
              .getLastDailyList()
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getDailyList(date: String): Observable<ZhihuDailyListBean> {
      RetrofitManager.setBaseUrl(Constants.ZHIHU_BASE_URL)
      return RetrofitManager.mRetrofit.create(ZhihuApi::class.java)
              .getDailyListWithDate(date)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}
















