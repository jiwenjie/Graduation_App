package com.example.root.graduation_app.mvp.presenter

import com.example.base_library.base_mvp.BaseMvpPresenter
import com.example.base_library.base_utils.ExceptionHandle
import com.example.root.graduation_app.mvp.constract.ProjectContract
import com.example.root.graduation_app.mvp.model.ProjectModel

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
class ProjectPresenter(view: ProjectContract.View)
   : BaseMvpPresenter<ProjectContract.View>(view), ProjectContract.Presenter {

   private val mModel by lazy { ProjectModel() }

   override fun requestProjectTree() {
      mView?.showLoading()
      addSubscription(
              mModel.requestProjectTree()
                      .subscribe({
                         mView?.dismissLoading()
                         mView?.setProjectTree(it.data)
                      }, {
                         mView?.showError(ExceptionHandle.handleException(it), ExceptionHandle.errorCode)
                      })
      )
   }
}