package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.LogUtils
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

   override fun createNewTask(userid: String, title: String, content: String) {
      addSubscription(
              mModel.createNewTask(userid, title, content)
                      .subscribe({
                         if (it.result == "succeed") {
                            mView?.createNewSuccess(it.result) // 后台这里新建成功之后没有添加 data
                         } else {
                            LogUtils.e(it.msg + it.code)
                            mView?.error()
                         }
                      }, {
                         LogUtils.e(it.message.toString())
                         mView?.error()
                      }))
   }

   override fun getTaskDetail(userid: String, id: String) {
      addSubscription(
              mModel.getTaskDetail(userid, id)
                      .subscribe({
                         if (it.result == "succeed") {
                            mView?.getDetailSuccess(it.data)
                         } else {
                            mView?.error()
                            LogUtils.e(it.msg + it.code)
                         }
                      }, {
                         LogUtils.e(it.message.toString())
                         mView?.error()
                      }))
   }

   override fun changeStatus(userid: String, id: String) {
      addSubscription(
              mModel.changeStatus(userid, id)
                      .subscribe({
                         if (it.result == "succeed") {
                            // 返回的没有 data 这个字段 （当返回的格式是 String 的时候）
                            mView?.changeStatusSuccess(it.result)
                         } else {
                            mView?.error()
                            LogUtils.e(it.msg)
                         }
                      }, {
                         LogUtils.e(it.message.toString())
                         mView?.error()
                      }))
   }
}