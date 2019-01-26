package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.WanAndroidListBean
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
interface ProjectContract {

   interface Model {
      fun requestProjectTree(): Observable<WanAndroidListBean<WanAndroidPublicItemBean>>
   }

   interface View : IBaseView {
      fun scrollToTop()

      fun setProjectTree(list: List<WanAndroidPublicItemBean>)
   }

   interface Presenter {
      fun requestProjectTree()
   }

}