package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.ProjectListContract
import com.example.root.graduation_app.mvp.model.ProjectListModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
class ProjectListPresenter(view: ProjectListContract.View)
   : BaseMvpPresenter<ProjectListContract.View>(view), ProjectListContract.Presenter {

   private val mModel by lazy { ProjectListModel() }

   override fun requestProjectList(page: Int, cid: Int) {
      mView?.showLoading()
      addSubscription(
              mModel.requestProjectList(page, cid)
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.setProjectList(it.data)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}