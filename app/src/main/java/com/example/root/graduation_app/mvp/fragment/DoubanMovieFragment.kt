package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanMovieDetail
import com.example.root.graduation_app.bean.DoubanSubjectBean
import com.example.root.graduation_app.mvp.adapter.DoubanMovieAdapter
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.presenter.DoubanMoviePresenter
import com.example.root.graduation_app.utils.Constants
import kotlinx.android.synthetic.main.common_multiple_recyclerview.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/11
 *  desc:
 *  version:1.0
 */
class DoubanMovieFragment : BaseMvpFragment<DoubanContract.DoubanMovieView, DoubanMoviePresenter>(), DoubanContract.DoubanMovieView {

   private var start = 0
   private var loadingMore = false

   private val beanList by lazy { ArrayList<DoubanSubjectBean>() }
   private val adapter by lazy { DoubanMovieAdapter(activity!!, beanList) }

   private var headerView: View? = null

   companion object {
      @JvmStatic
      fun newInstance(): DoubanMovieFragment {
         return DoubanMovieFragment()
      }
   }

   override fun initPresenter(): DoubanMoviePresenter = DoubanMoviePresenter(this)

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = common_multipleStatusView
      mLayoutStatusView?.showContent()

      initView()

      commonRv.adapter = adapter
      commonRv.layoutManager = LinearLayoutManager(activity)
//      commonRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            val itemCount = commonRv.layoutManager?.itemCount
//            val lastVisibleItem =
//                    (commonRv.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//            if (!loadingMore && lastVisibleItem == (itemCount!! - 1)) {
//               loadingMore = true
//               start += Constants.CONFIG_LIMIE
//               mPresenter.loadBookList(tags!!, start, Constants.CONFIG_LIMIE)
//            }
//         }
//      })
   }

   /**
    * 添加头部
    */
   private fun initView() {
      headerView = LayoutInflater.from(activity).inflate(R.layout.fragment_douban_movie_top_header, null)
      headerView!!.findViewById<View>(R.id.ll_movie_top).setOnClickListener {
         /** 点击跳转新的 Activity，展示 top 250 电影 **/
      }
      adapter.addHeaderView(headerView!!)
   }

   override fun loadData() {
      mPresenter.getDoubanHotMovie()
   }

   override fun updateDoubanContentList(subjectList: ArrayList<DoubanSubjectBean>) {
      loadingMore = false
      adapter.addAllData(subjectList)
   }

   override fun showDetail(bean: DoubanMovieDetail) {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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