package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.GankItemBean
import com.example.root.graduation_app.ui.adapter.GankIoMobileAdapter
import com.example.root.graduation_app.mvp.constract.GankIoContract
import com.example.root.graduation_app.mvp.presenter.GankIoPresenter
import com.example.root.graduation_app.utils.ConstantConfig
import kotlinx.android.synthetic.main.common_multiple_recyclerview.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/21
 *  desc:dvery -> android or ios
 *  version:1.0
 */
class MobileFragment : BaseMvpFragment<GankIoContract.GankIoView, GankIoPresenter>(), GankIoContract.GankIoView {

   private val beanList by lazy { ArrayList<GankItemBean>() }
   private val adapter by lazy { GankIoMobileAdapter(activity!!, beanList) }

   private var page = 1
   private var loadingMore = false

   companion object {
      @JvmStatic
      fun newInstance(): MobileFragment {
         return MobileFragment()
      }
   }

   override fun loadData() {
      page = 1
      mPresenter.getGankIoDayMobile("Android", ConstantConfig.CONFIG_LIMIE, page)
   }

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = common_multipleStatusView
      mLayoutStatusView?.showContent()

      commonRv.adapter = adapter
      commonRv.layoutManager = LinearLayoutManager(activity)
      commonRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val itemCount = commonRv.layoutManager?.itemCount
            val lastVisibleItem =
               (commonRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!loadingMore && lastVisibleItem == (itemCount!! - 1)) {
               loadingMore = true
               page ++
               mPresenter.getGankIoDayMobile("Android", ConstantConfig.CONFIG_LIMIE, page)
            }
         }
      })
   }

   override fun initPresenter(): GankIoPresenter = GankIoPresenter(this)

   override fun updateViewList(beanListL: ArrayList<GankItemBean>) {
      LogUtils.e("nnnnn" + beanListL.size)
      adapter.addAllData(beanList)
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