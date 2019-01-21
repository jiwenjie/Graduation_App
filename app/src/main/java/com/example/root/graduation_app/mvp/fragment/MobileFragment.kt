package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.GankItemBean
import com.example.root.graduation_app.mvp.adapter.GankIoMobileAdapter
import com.example.root.graduation_app.mvp.constract.GankIoContract
import com.example.root.graduation_app.mvp.presenter.GankIoPresenter
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

   companion object {
      @JvmStatic
      fun newInstance(): MobileFragment {
         return MobileFragment()
      }
   }

   override fun loadData() {
      mPresenter.getGankIoDayMobile("Android", 10, 1)
   }

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = common_multipleStatusView
      mLayoutStatusView?.showContent()
   }

   override fun initPresenter(): GankIoPresenter = GankIoPresenter(this)

   override fun updateViewList(beanListL: ArrayList<GankItemBean>) {
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