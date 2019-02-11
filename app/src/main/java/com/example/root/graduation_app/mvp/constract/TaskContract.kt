package com.example.root.graduation_app.mvp.constract

import com.example.base_library.base_mvp.BaseMvpViewImpl
import com.example.root.graduation_app.bean.BaseJackson
import com.example.root.graduation_app.bean.TodoBean
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/05
 *  desc:
 *  version:1.0
 */
interface TaskContract {

   interface Model {
      fun createNewTask(title: String, content: String, time: String): Observable<BaseJackson<String>>

      fun getTaskDetail(id: Int): Observable<BaseJackson<TodoBean>>

      fun changeStatus(id: Int, complete: Boolean): Observable<BaseJackson<String>>
   }

   interface View : BaseMvpViewImpl {
      fun createNewSuccess(msg: String)

      fun getDetailSuccess(bean: TodoBean)

      fun changeStatusSuccess(msg: String)

      fun error()
   }

   interface Presenter {
      fun createNewTask(title: String, content: String, time: String)

      fun getTaskDetail(id: Int)

      fun changeStatus(id: Int, complete: Boolean)
   }
}