package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.root.graduation_app.mvp.constract.TaskContract
import com.example.root.graduation_app.mvp.model.TaskModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/05
 *  desc:
 *  version:1.0
 */
class TaskPresenter(view: TaskContract.View)
   : BaseMvpPresenter<TaskContract.View>(view), TaskContract.Presenter {

   private val mModel by lazy { TaskModel() }

   override fun createNewTask(title: String, content: String, time: String) {
      addSubscription(
              mModel.createNewTask(title, content, time)
                      .subscribe({
                         mView?.createNewSuccess(it.data)
                      }, {
                         mView?.error()
                      })
      )
   }

   override fun getTaskDetail(id: Int) {
      addSubscription(
              mModel.getTaskDetail(id)
                      .subscribe({
                         mView?.getDetailSuccess(it.data)
                      }, {
                         mView?.error()
                      })
      )
   }

   override fun changeStatus(id: Int, complete: Boolean) {
      addSubscription(
              mModel.changeStatus(id, complete)
                      .subscribe({
                        mView?.changeStatusSuccess(it.data)
                      }, {
                         mView?.error()
                      })
      )
   }
}