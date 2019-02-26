package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v4.content.ContextCompat
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
import com.example.root.graduation_app.mvp.constract.ProjectListContract
import com.example.root.graduation_app.mvp.presenter.ProjectListPresenter
import com.example.root.graduation_app.ui.activity.CommonWebViewActivity
import com.example.root.graduation_app.ui.adapter.ProjectAdapter
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
class ProjectListFragment : BaseMvpFragment<ProjectListContract.View, ProjectListPresenter>(), ProjectListContract.View {

   /**
    * cid
    */
   private var cid: Int = -1

   /**
    * Article datas
    */
   private val datas = ArrayList<WanAndroidItem>()

   private var page = 1
   private var loadingMore = false

   companion object {
      fun getInstance(cid: Int): ProjectListFragment {
         val fragment = ProjectListFragment()
         val args = Bundle()
         args.putInt(ConstantConfig.CONTENT_CID_KEY, cid)
         fragment.arguments = args
         return fragment
      }
   }

   override fun loadData() {
      mPresenter.requestProjectList(1, cid)
   }

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = multiple_status_view
      mLayoutStatusView?.showContent()
      cid = arguments!!.getInt(ConstantConfig.CONTENT_CID_KEY)

      recyclerView.run {
         layoutManager = linearLayoutManager
         adapter = projectAdapter
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
               mPresenter.requestProjectList(page, cid)
            }
         }
      })
      initEvent()
   }

   private fun initEvent() {
      swipeRefreshLayout.run {
         setOnRefreshListener(onRefreshListener)
      }

      projectAdapter.setOnItemClickListener { position, view ->
         // 点击项目跳转活动
         if (datas.size > 0) {
            CommonWebViewActivity.runActivity(activity!!, datas[position].title, datas[position].link)
         }
      }
   }

   /**
    * RefreshListener
    */
   private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
      swipeRefreshLayout.isRefreshing = true
      swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity!!, R.color.colorPrimary))
      loadData()
   }

   override fun initPresenter(): ProjectListPresenter = ProjectListPresenter(this)

   override fun scrollToTop() {
      recyclerView.run {
         if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
            scrollToPosition(0)
         } else {
            smoothScrollToPosition(0)
         }
      }
   }

   override fun setProjectList(articles: WanAndroidJson<WanAndroidItem>) {
      loadingMore = false
      swipeRefreshLayout.isRefreshing = false
      projectAdapter.addAllData(articles.datas)
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

   private val projectAdapter: ProjectAdapter by lazy {
      ProjectAdapter(activity!!, datas)
   }

   /**
    * LinearLayoutManager
    */
   private val linearLayoutManager: LinearLayoutManager by lazy {
      LinearLayoutManager(activity)
   }

   /**
    * RecyclerView Divider
    */
   private val recyclerViewItemDecoration by lazy {
      activity?.let { SpaceItemDecoration(it) }
   }

   override fun getLayoutId(): Int = R.layout.fragment_refresh_layout
}