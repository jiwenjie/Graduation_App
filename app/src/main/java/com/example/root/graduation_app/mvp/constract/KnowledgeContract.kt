package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.WanAndroidBaseBean
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanAndroidJson
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
interface KnowledgeContract {

   interface Model {
      fun requestKnowledgeList(page: Int, cid: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>
   }

   interface View : IBaseView {
      fun scrollToTop()

      fun setKnowledgeList(articles: WanAndroidJson<WanAndroidItem>)
   }

   interface Presenter {
      fun requestKnowledgeList(page: Int, cid: Int)
   }
}