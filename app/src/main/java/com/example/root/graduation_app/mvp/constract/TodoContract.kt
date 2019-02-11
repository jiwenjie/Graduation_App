package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.BaseJacksonList
import com.example.root.graduation_app.bean.TodoBean
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:
 *  version:1.0
 */
interface TodoContract {

   interface Model {
      fun getTodoList(page: Int, limit: Int, complete: Boolean)
              : Observable<BaseJacksonList<TodoBean>>
   }

   interface View: IBaseView {
      fun showResult(dataList: ArrayList<TodoBean>)
   }

   interface Presenter {
      fun getTodoList(page: Int, limit: Int, complete: Boolean)
   }
}