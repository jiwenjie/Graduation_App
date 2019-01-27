package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.base_library.base_mvp.BaseMvpActivity
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanandroidHotkeyword
import com.example.root.graduation_app.ui.adapter.HotkeywordAdapter
import com.example.root.graduation_app.ui.adapter.WanandroidTabItemAdapter
import com.example.root.graduation_app.mvp.constract.WanandroidContract
import com.example.root.graduation_app.mvp.presenter.WanandroidHotwordPresenter
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.activity_search_public_history.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/22
 *  desc:
 *  version:1.0
 */
class SearchActivity :
    BaseMvpActivity<WanandroidContract.WanandroidHotwordView, WanandroidHotwordPresenter>(),
    WanandroidContract.WanandroidHotwordView {

    private val beanList by lazy { ArrayList<WanandroidHotkeyword>() }
    private val hotkeywordAdapter by lazy { HotkeywordAdapter(this, beanList) }

    private val searchList by lazy { ArrayList<WanAndroidItem>() }
    private val searchAdapter by lazy { WanandroidTabItemAdapter(this, searchList) }

    private var publicId = -1   // 公众号的 id 标识
    private var page = 1
    private var loadingMore = false
    private var tagKey = ""

    companion object {
        private const val KEY_TAB_NAME = "key_tab_name"

        @JvmStatic
        fun runActivity(activity: Activity, publicId : Int) {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(KEY_TAB_NAME, publicId)
            activity.startActivity(intent)
        }
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        mLayoutStatusView = multipleStatusView
        mLayoutStatusView?.showContent()
        publicId = intent.getIntExtra(KEY_TAB_NAME, -1)

        initView()
        initEvent()
    }

    private fun initView() {
        // 热词的 rv
        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      // 按正常方向换行
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW   // 主轴为水平方向，起点在左端
        flexBoxLayoutManager.alignItems = AlignItems.CENTER      // 定义项目在副轴轴上如何对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START   // 多个轴对其方式

        mRecyclerView_hot.adapter = hotkeywordAdapter
        mRecyclerView_hot.layoutManager = flexBoxLayoutManager

        // 搜索结果的 rv
        mRecyclerView_result.adapter = searchAdapter
        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView_result.layoutManager?.itemCount
                val visibleItemPoin = (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && visibleItemPoin == (itemCount!! - 1)) {
                    loadingMore = true
                    page ++
                    mPresenter.searchArticleInPublicAddress(publicId, page, tagKey)
                }
            }
        })
    }

    private fun initEvent() {
        hotkeywordAdapter.setOnTagItemClickListener { tag ->
            if (publicId == -1) return@setOnTagItemClickListener

            tagKey = tag
            mPresenter.searchArticleInPublicAddress(publicId, page, tagKey)
//            GeneralUtils.hideKeyboard(applicationContext, et_search_view)
        }
    }

    override fun loadData() {
        mPresenter.getHotword()
    }

    override fun initPresenter(): WanandroidHotwordPresenter = WanandroidHotwordPresenter(this)

    override fun displayHotword(hotwordList: ArrayList<WanandroidHotkeyword>) {
        hotkeywordAdapter.addAllData(hotwordList)
    }

    override fun displaySearchResult(searchResultList: ArrayList<WanAndroidItem>) {
        searchAdapter.addAllData(searchResultList)
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        ToastUtils.showToast(this@SearchActivity, errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_search_public_history
}