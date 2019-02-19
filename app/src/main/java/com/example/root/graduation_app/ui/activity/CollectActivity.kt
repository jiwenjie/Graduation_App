package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpActivity
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.mvp.constract.CollectContract
import com.example.root.graduation_app.mvp.presenter.CollectPresenter
import com.example.root.graduation_app.ui.adapter.CollectAdapter
import com.example.root.graduation_app.utils.ConstantConfig
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_collect.*
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/28
 *  desc:
 *  version:1.0
 */
class CollectActivity : BaseMvpActivity<CollectContract.View, CollectPresenter>(),
        CollectContract.View {

   private val beanList by lazy { ArrayList<WanAndroidItem>() }
   private val collectAdapter by lazy { CollectAdapter(this, beanList) }

   private var page = 1
   private var loadingMore = false

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, CollectActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@CollectActivity,
              ContextCompat.getColor(this@CollectActivity, R.color.colorPrimary), 0)

      mLayoutStatusView = multiple_status_view
      mLayoutStatusView?.showContent()

      swipeRefreshLayout.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.alpha_90_white))
      swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
      swipeRefreshLayout.setDistanceToTriggerSync(200)
      swipeRefreshLayout.setOnRefreshListener {
         swipeRefreshLayout.isRefreshing = true
         loadData()
      }
      recyclerView.adapter = collectAdapter
      recyclerView.layoutManager = LinearLayoutManager(this)
      recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val itemCount = recyclerView.layoutManager?.itemCount
            if (itemCount == null || itemCount < ConstantConfig.PAGE_LIMIT) return
            val lastVisibleItem =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
               loadingMore = true
               page ++
               mPresenter.requestCollectData(App.getLoginUser()?.userid!!, page, ConstantConfig.PAGE_LIMIT)
            }
         }
      })

      collectAdapter.setOnItemClickListener { position, view ->
         // 点击跳转
         CommonWebViewActivity.runActivity(this@CollectActivity, beanList[position].title, beanList[position].link)
      }

      activity_collect_toolbar.setNavigationOnClickListener {
         finish()
      }
   }

   override fun initPresenter(): CollectPresenter = CollectPresenter(this)

   override fun displayCollectData(collectList: ArrayList<WanAndroidItem>) {
      if (!swipeRefreshLayout.isRefreshing && collectList.size != 0 && !collectList.isEmpty()) {
         collectAdapter.addAllData(collectList)
      }
      swipeRefreshLayout.isRefreshing = false
      loadingMore = false

   }

   override fun showLoading() {
      mLayoutStatusView?.showLoading()
   }

   override fun dismissLoading() {
      mLayoutStatusView?.showContent()
   }

   override fun showError(errorMsg: String, errorCode: Int) {
      ToastUtils.showToast(this@CollectActivity, errorMsg)
      if (errorMsg != "not more data") {  // 判断如果当前是没有更多数据则只是提示而不展示错误页面
         if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
         } else {
            mLayoutStatusView?.showError()
         }
      }
   }

   override fun loadData() {
      page = 1
      mPresenter.requestCollectData(App.getLoginUser()?.userid!!, page, ConstantConfig.PAGE_LIMIT)
   }

   override fun getLayoutId(): Int = R.layout.activity_collect
}