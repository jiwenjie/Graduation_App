package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import com.example.root.graduation_app.mvp.adapter.DoubanBookAdapter
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.presenter.DoubanBookPresenter
import com.example.root.graduation_app.utils.Constants
import kotlinx.android.synthetic.main.common_multiple_recyclerview.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc:
 *  version:1.0
 */
class DoubanLiteratureFragment: BaseMvpFragment<DoubanContract.DoubanBookView, DoubanBookPresenter>(),
        DoubanContract.DoubanBookView {

   private var tags: String? = null
   private var start = 0
   private var loadingMore = false

   private val beanList by lazy { ArrayList<DoubanBookItemDetail>() }
   private val adapter by lazy { DoubanBookAdapter(activity!!, beanList) }

   companion object {
      private const val KEY_TAG = "key_tag"

      @JvmStatic
      fun getInstance(tag: String): DoubanLiteratureFragment {
         return DoubanLiteratureFragment().apply {
            arguments = Bundle().apply {
               putString(KEY_TAG, tag)
            }
         }
      }
   }

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview

   override fun loadData() {
      if (!tags.isNullOrEmpty()) {
         mPresenter.loadBookList(tags!!, start, Constants.CONFIG_LIMIE)
      }
   }

   override fun initFragment(savedInstanceState: Bundle?) {
      tags = arguments?.getString(KEY_TAG)
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
               start += Constants.CONFIG_LIMIE
               mPresenter.loadBookList(tags!!, start, Constants.CONFIG_LIMIE)
            }
         }
      })
   }

   override fun initPresenter(): DoubanBookPresenter = DoubanBookPresenter(this@DoubanLiteratureFragment)

   override fun updateDoubanContentList(itemList: ArrayList<DoubanBookItemDetail>) {
      loadingMore = false
      adapter.addAllData(itemList)
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