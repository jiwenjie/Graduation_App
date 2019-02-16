package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.BaseJacksonList
import com.example.root.graduation_app.bean.TodoBean
import com.example.root.graduation_app.mvp.constract.TodoContract
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:
 *  version:1.0
 */
class TodoModel: TodoContract.Model {

   override fun getTodoList(userid: String, page: Int, limit: Int, complete: Boolean): Observable<BaseJacksonList<TodoBean>> {
      return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
              .create(JacksonApi::class.java)
              .getTodoListData(userid, page, limit, complete)
              .compose(RxJavaUtils.applyObservableAsync())
   }
}