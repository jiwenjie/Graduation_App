package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.WeixinChoiceItemBean
import com.example.root.graduation_app.bean.WeixinChoiceListBean
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/09
 *  desc: weixin 的 契约类
 *  version:1.0
 */
interface WeixinContract {

   /**
    * 获取微信精选
    *
    * @param page      指定微信精选页数->空的话默认1
    * @param pageStrip 每页显示条数->空的话默认20条
    * @param key       聚合key
    * @return Observable
    */
   interface WeixinModel {
      fun getWeixinChoiceList(page: Int, pageStrip: Int, key: String): Observable<WeixinChoiceListBean>
   }

   /**
    * 微信 view
    */
   interface WeixinView : IBaseView {
      fun updateContentList(list: ArrayList<WeixinChoiceItemBean>)

      /**
       * 显示错误信息
       */
      fun showError(errorMsg: String, errorCode: Int)

      fun showNetWorkError()
   }

   /**
    * 微信 presenter
    */
   interface WeixinPresenter {
      fun loadLatestList(page: Int, pageStrip: Int, key: String)
   }
}