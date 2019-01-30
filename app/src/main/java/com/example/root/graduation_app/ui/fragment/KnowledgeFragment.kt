package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanAndroidJson
import com.example.root.graduation_app.mvp.constract.KnowledgeContract
import com.example.root.graduation_app.mvp.presenter.KnowledgePresenter
import com.example.root.graduation_app.ui.adapter.KnowledgeAdapter
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
class KnowledgeFragment
   : BaseMvpFragment<KnowledgeContract.View, KnowledgePresenter>(), KnowledgeContract.View {

   /**
    * cid
    */
   private var cid: Int = 0
   /**
    * datas
    */
   private val datas = ArrayList<WanAndroidItem>()
   /**
    * Knowledge Adapter
    */
   private val knowledgeAdapter: KnowledgeAdapter by lazy {
      KnowledgeAdapter(activity!!, datas)
   }

   /**
    * is Refresh
    */
   private var isRefresh = true
   private var page = 0
   private var loadingMore = false

   companion object {
      fun getInstance(cid: Int): KnowledgeFragment {
         val fragment = KnowledgeFragment()
         val args = Bundle()
         args.putInt(ConstantConfig.CONTENT_CID_KEY, cid)
         fragment.arguments = args
         return fragment
      }
   }

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = multiple_status_view
      mLayoutStatusView?.showContent()

      cid = arguments?.getInt(ConstantConfig.CONTENT_CID_KEY) ?: 0
      swipeRefreshLayout.run {
         setOnRefreshListener(onRefreshListener)
      }
      recyclerView.run {
         layoutManager = linearLayoutManager
         adapter = knowledgeAdapter
         itemAnimator = DefaultItemAnimator()
         recyclerViewItemDecoration?.let { addItemDecoration(it) }
      }

      recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val itemCount = recyclerView.layoutManager?.itemCount
            val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!loadingMore && lastVisibleItem == (itemCount!! - 1)) {
               loadingMore = true
               page++
               mPresenter.requestKnowledgeList(page, cid)
            }
         }
      })
   }

   override fun loadData() {
      page = 0
      mPresenter.requestKnowledgeList(page, cid)
   }

   override fun initPresenter(): KnowledgePresenter = KnowledgePresenter(this)

   override fun scrollToTop() {
      recyclerView.run {
         if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
            scrollToPosition(0)
         } else {
            smoothScrollToPosition(0)
         }
      }
   }

   override fun setKnowledgeList(articles: WanAndroidJson<WanAndroidItem>) {
      loadingMore = false
      swipeRefreshLayout?.isRefreshing = false
      knowledgeAdapter.addAllData(articles.datas)
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

   /**
    * RecyclerView Divider
    */
   private val recyclerViewItemDecoration by lazy {
      activity?.let {
         SpaceItemDecoration(it)
      }
   }

   /**
    * LinearLayoutManager
    */
   private val linearLayoutManager: LinearLayoutManager by lazy {
      LinearLayoutManager(activity)
   }

   /**
    * RefreshListener
    */
   private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
      isRefresh = true
      mPresenter.requestKnowledgeList(0, cid)
   }

   override fun getLayoutId(): Int = R.layout.fragment_refresh_layout
}