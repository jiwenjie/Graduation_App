package com.example.root.graduation_app.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.bean.TodoBean
import com.example.root.graduation_app.mvp.constract.TodoContract
import com.example.root.graduation_app.mvp.presenter.TodoPresenter
import com.example.root.graduation_app.ui.activity.TaskActivity
import com.example.root.graduation_app.ui.adapter.TodoAdapter
import com.example.root.graduation_app.utils.ConstantConfig
import kotlinx.android.synthetic.main.common_multiple_recyclerview.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:
 *  version:1.0
 */
class TodoFragment : BaseMvpFragment<TodoContract.View, TodoPresenter>(), TodoContract.View {

   private var page = 1
   private var loadingMore = false
   private var complete: Boolean? = null  // 传递的参数，查看是否查询已完成或者未完成的
   private val beanList by lazy { ArrayList<TodoBean>() }
   val adapter by lazy { TodoAdapter(activity!!, beanList) }

   companion object {
      private const val KEY_COMPLETE = "key_complete"
      // 如果跳转详情之后有改变状态的操作，则刷新数据
      private const val REQ_REFLESH = 1526

      @JvmStatic
      fun newInstance(complete: Boolean): TodoFragment {
         return TodoFragment().apply {
            arguments = Bundle().apply {
               putBoolean(KEY_COMPLETE, complete)
            }
         }
      }
   }

   override fun initFragment(savedInstanceState: Bundle?) {
      complete = arguments?.getBoolean(KEY_COMPLETE, false)

      mLayoutStatusView = common_multipleStatusView
      mLayoutStatusView?.showContent()

      commonRv.adapter = adapter
      commonRv.layoutManager = LinearLayoutManager(activity)
      commonRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val itemCount = commonRv.layoutManager?.itemCount
            if (itemCount == null || itemCount < ConstantConfig.PAGE_LIMIT) return
            val lastVisibleItem =
                    (commonRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
               loadingMore = true
               page++
               mPresenter.getTodoList(App.getLoginUser()?.userid!!, page, ConstantConfig.PAGE_LIMIT, complete!!)
            }
         }
      })

      /**
       * 点击列表的子项跳转详情部分
       */
      adapter.setOnItemClickListener { position, view ->
         TaskActivity.runActivity(activity!!, beanList[position].todoid, REQ_REFLESH, false)
      }
   }

   override fun loadData() {
      page = 1
      mPresenter.getTodoList(App.getLoginUser()?.userid!!, page, ConstantConfig.PAGE_LIMIT, complete!!)
   }

   override fun initPresenter(): TodoPresenter = TodoPresenter(this)

   override fun showResult(dataList: ArrayList<TodoBean>) {
      loadingMore = false
      if (dataList.isEmpty() || dataList.size == 0) {
         mLayoutStatusView?.showEmpty()
         return
      } else {
         if (page == 1) {
            adapter.mDataList?.clear()
         }
         adapter.addAllData(dataList)
      }
   }

   override fun showLoading() {
      mLayoutStatusView?.showLoading()
   }

   override fun dismissLoading() {
      mLayoutStatusView?.showContent()
   }

   override fun showError(errorMsg: String, errorCode: Int) {
      ToastUtils.showToast(activity!!, errorMsg)
      if (errorCode == ErrorStatus.NETWORK_ERROR) {
         mLayoutStatusView?.showNoNetwork()
      } else {
         mLayoutStatusView?.showError()
      }
   }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      if (resultCode != Activity.RESULT_OK) return
      if (requestCode == REQ_REFLESH) {
         loadData()
      }
   }

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview
}