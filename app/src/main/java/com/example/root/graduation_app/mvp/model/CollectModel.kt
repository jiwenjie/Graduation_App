package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.WanAndroidBaseBean
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanAndroidJson
import com.example.root.graduation_app.mvp.constract.CollectContract
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/28
 *  desc:
 *  version:1.0
 */
class CollectModel : CollectContract.Model {

   override fun requestCollectData(page: Int, limit: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>> {
      return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .getCollectData(page, limit)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}