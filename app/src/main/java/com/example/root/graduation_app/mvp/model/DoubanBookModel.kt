package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.DoubanApi
import com.example.root.graduation_app.bean.DoubanBookBean
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.utils.Constants
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc:
 *  version:1.0
 */
class DoubanBookModel : DoubanContract.DoubanBookModel {

   override fun getBookListWithTag(tag: String, start: Int, count: Int): Observable<DoubanBookBean> {
      return RetrofitManager
              .provideClient(Constants.DOUBAN_BASE_URL)
              .create(DoubanApi::class.java)
              .getBookListWithTag(tag, start, count)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getBookDetail(id: String): Observable<DoubanBookItemDetail> {
      return RetrofitManager
              .provideClient(Constants.DOUBAN_BASE_URL)
              .create(DoubanApi::class.java)
              .getBookDetail(id)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}