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

   override fun createNewTask(title: String, content: String, time: String): Observable<BaseJackson<String>> {
      return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .createNewTask(title, content, time)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun getTaskDetail(id: Int): Observable<BaseJackson<TodoBean>> {
      return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .getTaskDetail(id)
              .compose(RxJavaUtils.applyObservableAsync())
   }

   override fun changeStatus(id: Int, complete: Boolean): Observable<BaseJackson<String>> {
      return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .changeStatus(id, complete)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}