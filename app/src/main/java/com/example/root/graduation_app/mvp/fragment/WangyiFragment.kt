package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WangyiNewsItemBean
import com.example.root.graduation_app.mvp.adapter.WangyiAdapter
import com.example.root.graduation_app.mvp.constract.WangyiContract
import com.example.root.graduation_app.mvp.presenter.WangyiPresenter
import kotlinx.android.synthetic.main.fragment_wangyi.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:
 *  version:1.0
 */
class WangyiFragment : BaseMvpFragment<WangyiContract.WangyiView, WangyiPresenter>(), WangyiContract.WangyiView {

    private var currentIndex = 0
    private var loadingMore = false
    /**
     * 是否加载更多
     */
    private val beanList by lazy { ArrayList<WangyiNewsItemBean>() }
    private val wangyiAdapter by lazy { WangyiAdapter(activity!!, beanList) }

    companion object {
        @JvmStatic
        fun getInstance(): WangyiFragment {
            val fragment = WangyiFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initPresenter(): WangyiPresenter = WangyiPresenter(this)

    override fun loadData() {
        mPresenter.loadLatestList(currentIndex)
    }

    override fun getLayoutId(): Int = R.layout.fragment_wangyi

    override fun initFragment(savedInstanceState: Bundle?) {
        mLayoutStatusView = wangyi_multipleStatusView
        mLayoutStatusView?.showContent()

        wangyiRecyclerView.adapter = wangyiAdapter
        wangyiRecyclerView.layoutManager = LinearLayoutManager(activity)
        // 监听滑动事件加载更多
        wangyiRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = wangyiRecyclerView.layoutManager?.itemCount
                val lastVisibleItem =
                    (wangyiRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && lastVisibleItem == (itemCount!! - 1)) {
                    loadingMore = true
                    currentIndex += 20
                    mPresenter.loadLatestList(currentIndex)
                }
            }
        })
    }

    override fun updateContentList(list: ArrayList<WangyiNewsItemBean>) {
        loadingMore = false
        wangyiAdapter.addAllData(list)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        ToastUtils.showToast(activity!!, errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showNoData() {
        /**
         * all data is display, should display no more data
         */
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }
}