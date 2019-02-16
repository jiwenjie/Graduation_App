package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.BaseJackson
import com.example.root.graduation_app.bean.TodoBean
import com.example.root.graduation_app.mvp.constract.TaskContract
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/05
 *  desc:
 *  version:1.0
 */
class TaskModel : TaskContract.Model {

   override fun createNewTask(userid: String, title: String, content: String): Observable<BaseJackson<String>> {
      return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .createNewTask(userid, title, content)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getTaskDetail(userid: String, id: String): Observable<BaseJackson<TodoBean>> {
      return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .getTaskDetail(userid, id)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun changeStatus(userid: String, id: String): Observable<BaseJackson<String>> {
      return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .changeStatus(userid, id)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}