package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.WanandroidApi
import com.example.root.graduation_app.bean.WanAndroidListBean
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean
import com.example.root.graduation_app.mvp.constract.ProjectContract
import com.example.root.graduation_app.utils.Constants
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
class ProjectModel : ProjectContract.Model {

   override fun requestProjectTree(): Observable<WanAndroidListBean<WanAndroidPublicItemBean>> {
      return RetrofitManager.provideClient(Constants.WANWANDROID_URL)
              .create(WanandroidApi::class.java)
              .getProjectTree()
              .compose(RxJavaUtils.applyObservableAsync())
   }
}