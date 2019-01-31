package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.base_library.base_mvp.BaseMvpActivity
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanMovieDetail
import com.example.root.graduation_app.bean.DoubanSubjectBean
import com.example.root.graduation_app.ui.adapter.DoubanTopMovieAdapter
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.presenter.DoubanMoviePresenter
import com.example.root.graduation_app.utils.ConstantConfig
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.common_toolbar_multiple_recyclerview.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/11
 *  desc:douban 电影 top 250 的活动
 *  version:1.0
 */
class DoubanMovieTopActivity : BaseMvpActivity<DoubanContract.DoubanMovieView, DoubanMoviePresenter>(), DoubanContract.DoubanMovieView {

   private var start = 0
   private var loadingMore = false
   private val beanList by lazy { ArrayList<DoubanSubjectBean>() }
   private val adapter by lazy { DoubanTopMovieAdapter(this@DoubanMovieTopActivity, beanList) }

   private val stagLayoutSpanCount by lazy { IntArray(3) }  // StaggeredGridLayoutManager 一行的 item 个数

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, DoubanMovieTopActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun loadData() {
      mPresenter.getDoubanMovieTop250(start, ConstantConfig.CONFIG_LIMIE)
   }

   override fun getLayoutId(): Int = R.layout.common_toolbar_multiple_recyclerview

   override fun initPresenter(): DoubanMoviePresenter = DoubanMoviePresenter(this)

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@DoubanMovieTopActivity,
              ContextCompat.getColor(this@DoubanMovieTopActivity, R.color.colorPrimary), 0)
      mLayoutStatusView = common_toolbar_multipleStatusView
      mLayoutStatusView?.showContent()

      common_toolbarLyt.title = resources.getString(R.string.title_about_top250)
      common_toolbarRv.adapter = adapter
      common_toolbarRv.layoutManager = StaggeredGridLayoutManager(3,
              StaggeredGridLayoutManager.VERTICAL)
      common_toolbarRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
         override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val itemCount = recyclerView.layoutManager?.itemCount

            val mLastVisibleItemPosition = (common_toolbarRv.layoutManager as StaggeredGridLayoutManager)
                    .findLastVisibleItemPositions(stagLayoutSpanCount)
            if (!loadingMore && mLastVisibleItemPosition.max() == (itemCount!! - 1)) {
               loadingMore = true
               start += ConstantConfig.CONFIG_LIMIE
               mPresenter.getDoubanMovieTop250(start, ConstantConfig.CONFIG_LIMIE)
            }
         }
      })
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
      ToastUtils.showToast(this@DoubanMovieTopActivity, errorMsg)
      if (errorCode == ErrorStatus.NETWORK_ERROR) {
         mLayoutStatusView?.showNoNetwork()
      } else {
         mLayoutStatusView?.showError()
      }
   }
}




