package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanMovieDetail
import com.example.root.graduation_app.bean.DoubanSubjectBean
import com.example.root.graduation_app.ui.activity.DoubanMovieTopActivity
import com.example.root.graduation_app.ui.activity.DoubanMovieDetailActivity
import com.example.root.graduation_app.ui.adapter.DoubanMovieAdapter
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.presenter.DoubanMoviePresenter
import kotlinx.android.synthetic.main.common_multiple_recyclerview.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/11
 *  desc:
 *  version:1.0
 */
class DoubanMovieFragment : BaseMvpFragment<DoubanContract.DoubanMovieView, DoubanMoviePresenter>(), DoubanContract.DoubanMovieView {

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
      adapter.setOnItemClickListener { position, view ->
         DoubanMovieDetailActivity.runActivity(activity!!, beanList[position])
      }
   }

   /**
    * 添加头部
    */
   private fun initView() {
      headerView = LayoutInflater.from(activity).inflate(R.layout.fragment_douban_movie_top_header, null)
      headerView!!.findViewById<View>(R.id.ll_movie_top).setOnClickListener {
         DoubanMovieTopActivity.runActivity(activity!!)
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