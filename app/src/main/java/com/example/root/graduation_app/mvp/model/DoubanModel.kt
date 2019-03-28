package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.DoubanApi
import com.example.root.graduation_app.bean.DoubanBookBean
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import com.example.root.graduation_app.bean.DoubanMovieBean
import com.example.root.graduation_app.bean.DoubanMovieDetail
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc:
 *  version:1.0
 */
class DoubanModel : DoubanContract.DoubanBookModel, DoubanContract.DoubanMovieModel {

   /** 豆瓣电影部分 **/
   override fun getDoubanHotMovie(): Observable<DoubanMovieBean> {
      return RetrofitManager
              .provideClient(ConstantConfig.DOUBAN_BASE_URL)
              .create(DoubanApi::class.java)
              .getDoubanHotMovie()
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getDoubanMovieDetail(id: String): Observable<DoubanMovieDetail> {
      return RetrofitManager
              .provideClient(ConstantConfig.DOUBAN_BASE_URL)
              .create(DoubanApi::class.java)
              .getDoubanMovieDetail(id)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getDoubanMovieTop250(start: Int, count: Int): Observable<DoubanMovieBean> {
      return RetrofitManager
              .provideClient(ConstantConfig.DOUBAN_BASE_URL)
              .create(DoubanApi::class.java)
              .getDoubanMovieTop250(start, count)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   /** 豆瓣书籍部分 **/
   override fun getBookListWithTag(tag: String, start: Int, count: Int): Observable<DoubanBookBean> {
      return RetrofitManager
              .provideClient(ConstantConfig.DOUBAN_BASE_URL)
              .create(DoubanApi::class.java)
              .getBookListWithTag(tag, start, count, ConstantConfig.DOUBAN_APIKEY)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getBookDetail(id: String): Observable<DoubanBookItemDetail> {
      return RetrofitManager
              .provideClient(ConstantConfig.DOUBAN_BASE_URL)
              .create(DoubanApi::class.java)
              .getBookDetail(id, ConstantConfig.DOUBAN_APIKEY)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}