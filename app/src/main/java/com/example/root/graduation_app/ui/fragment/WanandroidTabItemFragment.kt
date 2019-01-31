package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.mvp.constract.WanandroidContract
import com.example.root.graduation_app.mvp.presenter.WanandroidDataPresenter
import com.example.root.graduation_app.ui.activity.CommonWebViewActivity
import com.example.root.graduation_app.ui.adapter.KnowledgeAdapter
import kotlinx.android.synthetic.main.common_multiple_recyclerview.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/22
 *  desc:
 *  version:1.0
 */
class WanandroidTabItemFragment : BaseMvpFragment<WanandroidContract.WanandroidDataView, WanandroidDataPresenter>(),
      WanandroidContract.WanandroidDataView {

   private val beanList by lazy { ArrayList<WanAndroidItem>() }
   private val adapter by lazy { KnowledgeAdapter(activity!!, beanList) }

   private var publicId = -1  // 公众号的 id

   private var page = 1
   private var loadingMore = false

   companion object {
      private const val KEY_PUBLIC_NUM = "key_public_num"

      @JvmStatic
      fun newInstance(publicId: Int): WanandroidTabItemFragment {
         return WanandroidTabItemFragment().apply {
            arguments = Bundle().apply {
               putInt(KEY_PUBLIC_NUM, publicId)
            }
         }
      }
   }

   override fun loadData() {
      if (publicId == -1) return
      mPresenter.getOnePublicAddressHistory(publicId, page)
   }

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview

   override fun initFragment(savedInstanceState: Bundle?) {
      publicId = arguments!!.getInt(KEY_PUBLIC_NUM)

      mLayoutStatusView = common_multipleStatusView
      mLayoutStatusView?.showContent()

      commonRv.adapter = adapter
      commonRv.layoutManager = LinearLayoutManager(activity)
      commonRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val itemCount = commonRv.layoutManager?.itemCount
            val lastVisibleItem = (commonRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!loadingMore && lastVisibleItem == (itemCount!! - 1)) {
               loadingMore = true
               page ++
               mPresenter.getOnePublicAddressHistory(publicId, page)
            }
         }
      })

      adapter.setOnItemClickListener { position, view ->
         CommonWebViewActivity.runActivity(activity!!, beanList[position].title, beanList[position].link)
      }
   }

   override fun initPresenter(): WanandroidDataPresenter = WanandroidDataPresenter(this)

   override fun displayPublicHistory(historyList: ArrayList<WanAndroidItem>) {
      loadingMore = false
      adapter.addAllData(historyList)
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











