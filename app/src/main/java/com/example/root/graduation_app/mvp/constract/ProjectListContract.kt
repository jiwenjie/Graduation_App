package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.WanAndroidBaseBean
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanAndroidJson
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
interface ProjectListContract {

   interface View : IBaseView {
      fun scrollToTop()

      fun setProjectList(articles: WanAndroidJson<WanAndroidItem>)
   }

   interface Presenter {
      fun requestProjectList(page: Int, cid: Int)
   }

   interface Model {
      fun requestProjectList(page: Int, cid: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>
   }
}