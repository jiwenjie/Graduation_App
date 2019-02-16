package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.base_library.base_utils.LogUtils
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

   override fun getTodoList(userid: String, page: Int, limit: Int, complete: Boolean) {
      if (page == 1) mView?.showLoading()
      addSubscription(
              mModel.getTodoList(userid, page, limit, complete)
                      .subscribe({
                         mView?.dismissLoading()
                         LogUtils.e(it.msg)
                         if (it.result == "succeed") {
                            mView?.showResult(it.data)
                         } else {
                            mView?.showError(it.msg, it.code)
                         }
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}