package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WeixinChoiceItemBean
import com.example.root.graduation_app.ui.adapter.WeixinAdapter
import com.example.root.graduation_app.mvp.constract.WeixinContract
import com.example.root.graduation_app.mvp.presenter.WeixinPresenter
import com.example.root.graduation_app.utils.ConstantConfig
import kotlinx.android.synthetic.main.fragment_weixin.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:
 *  version:1.0
 */
class WeixinFragment : BaseMvpFragment<WeixinContract.WeixinView, WeixinPresenter>(), WeixinContract.WeixinView {

   private val beanList by lazy { ArrayList<WeixinChoiceItemBean>() }
   private val adapter by lazy { WeixinAdapter(activity!!, beanList) }

   private var page = 1
   private var loadingMore = false

   companion object {
      @JvmStatic
      fun getInstance(): WeixinFragment {
         val fragment = WeixinFragment()
         val bundle = Bundle()
         fragment.arguments = bundle
         return fragment
      }
   }

   override fun initPresenter(): WeixinPresenter = WeixinPresenter(this)

   override fun getLayoutId(): Int = R.layout.fragment_weixin

   override fun loadData() {
      page = 1
      mPresenter.loadLatestList(page, ConstantConfig.PAGE_LIMIT, ConstantConfig.JU_HE_APP_KEY)
   }

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = weixin_multipleStatusView
      mLayoutStatusView?.showContent()

      weixinRecyclerView.adapter = adapter
      weixinRecyclerView.layoutManager = LinearLayoutManager(activity)
      weixinRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val itemCount = weixinRecyclerView.layoutManager?.itemCount
            val lastVisibleItem =
                    (weixinRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (!loadingMore && lastVisibleItem == (itemCount!! - 1)) {
               loadingMore = true
               page ++
               mPresenter.loadLatestList(page, ConstantConfig.PAGE_LIMIT, ConstantConfig.JU_HE_APP_KEY)
            }
         }
      })
   }

   override fun updateContentList(list: ArrayList<WeixinChoiceItemBean>) {
      loadingMore = false
      adapter.addAllData(list)
   }

   override fun showError(errorMsg: String, errorCode: Int) {
      ToastUtils.showToast(activity!!, errorMsg)
      if (errorCode == ErrorStatus.NETWORK_ERROR) {
         mLayoutStatusView?.showNoNetwork()
      } else {
         mLayoutStatusView?.showError()
      }
   }

   override fun showNetWorkError() {
      mLayoutStatusView?.showNoNetwork()
   }

   override fun showLoading() {
      mLayoutStatusView?.showLoading()
   }

   override fun dismissLoading() {
      mLayoutStatusView?.showContent()
   }
}