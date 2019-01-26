package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.WanandroidApi
import com.example.root.graduation_app.bean.WanAndroidBaseBean
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanAndroidJson
import com.example.root.graduation_app.mvp.constract.ProjectListContract
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
class ProjectListModel: ProjectListContract.Model {

   override fun requestProjectList(page: Int, cid: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>> {
      return RetrofitManager.provideClient(Constants.WANWANDROID_URL)
              .create(WanandroidApi::class.java)
              .getProjectList(page, cid)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}