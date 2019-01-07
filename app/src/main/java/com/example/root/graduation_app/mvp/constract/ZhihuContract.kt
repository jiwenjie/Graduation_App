package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.ZhihuDailyItemBean
import com.example.root.graduation_app.bean.ZhihuDailyListBean
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:知乎接口的契约类
 *  version:1.0
 */
interface ZhihuContract {

   /**
    * 知乎的 model 接口
    */
   interface ZhihuModel {
      /**
       *  获取日报的 list
       */
      fun getDailyList(): Observable<ZhihuDailyListBean>

      /**
       * 根据日期获取日报 list --> 20190107
       */
      fun getDailyList(date: String): Observable<ZhihuDailyListBean>
   }

   /**
    * 知乎的 View
    */
   interface ZhihuView: IBaseView {
      /**
       * 展示数据
       */
      fun updateContentList(ItemList: ArrayList<ZhihuDailyItemBean>)

      /**
       * 显示错误信息
       */
      fun showError(errorMsg: String, errorCode: Int)
   }

   /**
    * 知乎的 Presenter
    */
   interface ZhihuPresenter {
      /**
       * 加载最新数据
       */
      fun loadLatestList()

      /**
       * 加载更多数据
       */
      fun loadMoreList()
   }
}









