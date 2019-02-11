package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.KnowledgeTreeBody
import com.example.root.graduation_app.mvp.constract.KnowledgeTreeContract
import com.example.root.graduation_app.mvp.presenter.KnowledgeTreePresenter
import com.example.root.graduation_app.ui.activity.KnowledgeActivity
import com.example.root.graduation_app.ui.adapter.KnowledgeTreeAdapter
import com.example.root.graduation_app.utils.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/24
 *  desc:
 *  version:1.0
 */
class KnowledgeTreeFragment
   : BaseMvpFragment<KnowledgeTreeContract.View, KnowledgeTreePresenter>(), KnowledgeTreeContract.View {

   /**
    * datas
    */
   private val beanList = ArrayList<KnowledgeTreeBody>()
   private val knowLedgeAdapter by lazy { KnowledgeTreeAdapter(activity!!, beanList) }

   companion object {
      @JvmStatic
      fun newInstance(): KnowledgeTreeFragment {
         val fragment = KnowledgeTreeFragment()
         val bundle = Bundle()
         fragment.arguments = bundle
         return fragment
      }
   }

   override fun loadData() {
      mPresenter.requestKnowledgeTree()
   }

   override fun getLayoutId(): Int = R.layout.fragment_refresh_layout

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = multiple_status_view
      mLayoutStatusView?.showContent()

      initEvent()

      swipeRefreshLayout.run {
         setOnRefreshListener(onRefreshListener)
      }

      /**
       * 这里因为外部的 tree 列表是一次性请求完毕，所以不需要添加滚动监听
       */
      recyclerView.run {
         layoutManager = linearLayoutManager
         adapter = knowLedgeAdapter
         itemAnimator = DefaultItemAnimator()
         addItemDecoration(SpaceItemDecoration(activity!!))
      }
   }

   private fun initEvent() {
      knowLedgeAdapter.setOnItemClickListener { position, view ->
         if (beanList.size != 0) {
            val data = beanList[position]
            KnowledgeActivity.runActivity(activity!!, data.name, data)
         }
      }
   }

   /**
    * RefreshListener
    */
   private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
      mPresenter.requestKnowledgeTree()
   }

   override fun initPresenter(): KnowledgeTreePresenter = KnowledgeTreePresenter(this)

   override fun scrollToTop() {
      recyclerView.run {
         if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
            scrollToPosition(0)
         } else {
            smoothScrollToPosition(0)
         }
      }
   }

   override fun setKnowledgeTree(lists: ArrayList<KnowledgeTreeBody>) {
      lists.let {
         knowLedgeAdapter.run {
            swipeRefreshLayout.isRefreshing = false
            addAllData(lists)
         }
      }
      if (lists.isEmpty()) {
         mLayoutStatusView?.showEmpty()
      } else {
         mLayoutStatusView?.showContent()
      }
   }

   /**
    * LinearLayoutManager
    */
   private val linearLayoutManager: LinearLayoutManager by lazy {
      LinearLayoutManager(activity)
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
}