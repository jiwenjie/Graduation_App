package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.WeixinApi
import com.example.root.graduation_app.bean.WeixinChoiceListBean
import com.example.root.graduation_app.mvp.constract.WeixinContract
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/09
 *  desc:
 *  version:1.0
 */
class WeixinModel : WeixinContract.WeixinModel {

   override fun getWeixinChoiceList(page: Int, pageStrip: Int, key: String) : Observable<WeixinChoiceListBean> {
//      RetrofitManager.setBaseUrl(ConstantConfig.WEIXIN_BASE_URL)
      return RetrofitManager.provideClient(ConstantConfig.WEIXIN_BASE_URL).create(WeixinApi::class.java)
              .getWeixinChoiceList(page, pageStrip, key)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}