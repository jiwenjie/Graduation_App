package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.ZhihuDailyItemBean
import com.example.root.graduation_app.mvp.adapter.ZhihuAdapter
import com.example.root.graduation_app.mvp.constract.ZhihuContract
import com.example.root.graduation_app.mvp.presenter.ZhihuPresenter
import kotlinx.android.synthetic.main.fragment_zhihu.*


/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:
 *  version:1.0
 */
class ZhihuFragment : BaseMvpFragment<ZhihuContract.ZhihuView, ZhihuPresenter>(), ZhihuContract.ZhihuView {

   private val beanList by lazy { ArrayList<ZhihuDailyItemBean>() }
   private val zhihuAdapter by lazy { ZhihuAdapter(activity!!, beanList) }

   companion object {
      @JvmStatic
      fun getInstance(): ZhihuFragment {
         val fragment = ZhihuFragment()
         val bundle = Bundle()
         fragment.arguments = bundle
         return fragment
      }
   }

   /**
    * 是否加载更多
    */
   private var loadingMore = false

   override fun initPresenter(): ZhihuPresenter = ZhihuPresenter(this)

   override fun loadData() {
      mPresenter.loadLatestList()
   }

   override fun getLayoutId(): Int = R.layout.fragment_zhihu

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = zhihu_multipleStatusView
      mLayoutStatusView?.showContent()

      zhihu_recyclerView.adapter = zhihuAdapter
      zhihu_recyclerView.layoutManager = LinearLayoutManager(activity)

      // 监听滑动事件加载更多
      zhihu_recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val itemCount = zhihu_recyclerView.layoutManager?.itemCount
            val lastVisibleItem = (zhihu_recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!loadingMore && lastVisibleItem == (itemCount!! - 1)) {
               loadingMore = true
               mPresenter.loadMoreList()
            }
         }
      })
   }

   override fun updateContentList(ItemList: ArrayList<ZhihuDailyItemBean>) {
      //        Logger.e(list.toString());
      loadingMore = false
      zhihuAdapter.addAllData(ItemList)
   }

   override fun showError(errorMsg: String, errorCode: Int) {
      ToastUtils.showToast(activity!!, errorMsg)
      if (errorCode == ErrorStatus.NETWORK_ERROR) {
         mLayoutStatusView?.showNoNetwork()
      } else {
         mLayoutStatusView?.showError()
      }
   }

   override fun showLoading() {
      mLayoutStatusView?.showLoading()
   }

   override fun dismissLoading() {
      mLayoutStatusView?.showContent()
   }
}




















