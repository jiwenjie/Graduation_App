package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.TodoContract
import com.example.root.graduation_app.mvp.model.TodoModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:
 *  version:1.0
 */
class TodoPresenter(view: TodoContract.View) : BaseMvpPresenter<TodoContract.View>(view),
        TodoContract.Presenter {

   private val mModel by lazy { TodoModel() }

   override fun getTodoList(page: Int, limit: Int, complete: Boolean) {
      if (page == 1) mView?.showLoading()
      addSubscription(
              mModel.getTodoList(page, limit, complete)
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.showResult(it.data)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}