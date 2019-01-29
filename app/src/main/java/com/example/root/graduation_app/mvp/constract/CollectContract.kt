package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.WanAndroidBaseBean
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanAndroidJson
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/28
 *  desc:收藏的契约类
 *  version:1.0
 */
interface CollectContract {

   interface Model {
      fun requestCollectData(page: Int, limit: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>
   }

   interface View: IBaseView {
      fun displayCollectData(collectList: ArrayList<WanAndroidItem>)
   }

   interface Presenter {
      fun requestCollectData(page: Int, limit: Int)
   }
}