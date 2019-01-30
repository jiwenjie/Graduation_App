package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import com.example.base_library.base_mvp.BaseMvpActivity
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.bean.WanandroidHotkeyword
import com.example.root.graduation_app.ui.adapter.HotkeywordAdapter
import com.example.root.graduation_app.mvp.constract.WanandroidContract
import com.example.root.graduation_app.mvp.presenter.WanandroidSearchPresenter
import com.example.root.graduation_app.ui.adapter.KnowledgeAdapter
import com.google.android.flexbox.*
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_search_public_history.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/22
 *  desc:
 *  version:1.0
 */
class SearchActivity :
    BaseMvpActivity<WanandroidContract.WanandroidSearchView, WanandroidSearchPresenter>(),
    WanandroidContract.WanandroidSearchView {

    private val beanList by lazy { ArrayList<WanandroidHotkeyword>() }
    private val hotkeywordAdapter by lazy { HotkeywordAdapter(this, beanList) }

    private val searchList by lazy { ArrayList<WanAndroidItem>() }
    private val searchAdapter by lazy { KnowledgeAdapter(this, searchList) }

    private var publicId = -1   // 公众号的 id 标识
    private var page = 0
    private var loadingMore = false
    private var tagKey = ""

    companion object {
        private const val KEY_TAB_NAME = "key_tab_name"

        @JvmStatic
        fun runActivity(activity: Activity, publicId: Int) {
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
        StatusBarUtil.setColor(
            this@SearchActivity,
            ContextCompat.getColor(this@SearchActivity, R.color.colorPrimary),
            0
        )
        setupSearchView()
        // 热词的 rv
        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      // 按正常方向换行
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW   // 主轴为水平方向，起点在左端
        flexBoxLayoutManager.alignItems = AlignItems.CENTER      // 定义项目在副轴轴上如何对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START   // 多个轴对其方式

        mRecyclerView_hot.adapter = hotkeywordAdapter
        mRecyclerView_hot.layoutManager = flexBoxLayoutManager

        mRecyclerView_result.adapter = searchAdapter
        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView_result.layoutManager?.itemCount
                val visibleItemPoin =
                    (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!loadingMore && visibleItemPoin == (itemCount!! - 1)) {
                    loadingMore = true
                    page++
                    mPresenter.searchArticleAll(page, tagKey)
                }
            }
        })
    }

    private fun initEvent() {
        hotkeywordAdapter.setOnItemClickListener { position, view ->
            LogUtils.e("positionView")
            layout_hot_words.visibility = View.GONE
            layout_content_result.visibility = View.VISIBLE
            tagKey = beanList[position].name
            page = 0
            mPresenter.searchArticleAll(page, tagKey)
        }
        // 取消
        searchBack.setOnClickListener {
            onBackPressed()
        }
//        tv_cancel.setOnClickListener {
//            onBackPressed()
//        }
        // 键盘的搜索按钮 (EditText 的 几种 imeAction 设置使用，此处判断是否是搜索)
//        et_search_view.setOnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
////                closeSoftKeyboard()
//                tagKey = et_search_view.text.toString()
//                page = 0
//                if (tagKey.isEmpty()) {
//                    ToastUtils.showToast(applicationContext!!, "请输入您感兴趣的关键词")
//                } else {
//                    mPresenter.searchArticleAll(page, tagKey)
//                }
//            }
//            false
//        }
    }

    private fun setupSearchView() {
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        searchView.imeOptions = searchView.imeOptions or EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchFor(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (TextUtils.isEmpty(query)) {
                    clearResults()
                }
                return true
            }
        })

        searchBack.setOnClickListener {
            dismiss()
        }
//        scrim.setOnClickListener {
//            dismiss()
//        }
    }

    private fun searchFor(query: String) {
//        keyword = query
//        if (keyword.isBlank()) {
//            showToast(GlobalUtil.getString(R.string.search_keyword_can_not_be_blank))
//        } else {
//            clearResults()
//            loading?.visibility = View.VISIBLE
//            hideKeyboard()
//
//            isLoading = true
//            isNoMoreData = false
//            GifFun.getHandler().postDelayed(600) {
//                SearchMixed.getResponse(keyword, object : Callback {
//                    override fun onResponse(response: Response) {
//                        if (activity == null) {
//                            return
//                        }
//                        isLoading = false
//                        if (!ResponseHandler.handleResponse(response)) {
//                            val searchMixed = response as SearchMixed
//                            val status = searchMixed.status
//                            when (status) {
//                                0 -> {
//                                    searchItemList.addAll(searchMixed.users)
//                                    searchItemList.addAll(searchMixed.feeds)
//                                    if (searchMixed.feeds.size < 10) {
//                                        isNoMoreData = true
//                                    }
//                                    adapter.notifyDataSetChanged()
//                                    loadFinished()
//                                }
//                                10004 -> {
//                                    loadFinished()
//                                }
//                                10501 -> {
//                                    loadFailed(GlobalUtil.getString(R.string.search_keyword_can_not_be_blank))
//                                }
//                                10502 -> {
//                                    loadFailed(GlobalUtil.getString(R.string.search_keyword_to_long))
//                                }
//                                else -> {
//                                    logWarn(TAG, "Search failed. " + GlobalUtil.getResponseClue(status, searchMixed.msg))
//                                    loadFailed(GlobalUtil.getString(R.string.search_failed) + " " + status)
//                                }
//                            }
//                        } else {
//                            loadFailed(GlobalUtil.getString(R.string.unknown_error) + ": " + response.status)
//                        }
//                    }
//
//                    override fun onFailure(e: Exception) {
//                        logWarn(TAG, e.message, e)
//                        isLoading = false
//                        loadFailed(GlobalUtil.getString(R.string.search_failed_bad_network))
//                    }
//                })
//            }
//        }
    }

    private fun dismiss() {
        // clear the background else the touch ripple moves with the translation which looks bad
        searchBack.background = null
//        if (AndroidVersion.hasLollipop()) {
//            finishAfterTransition()
//        } else {
//            finish()
//        }
    }

    private fun clearResults() {
//        runAutoTransition()
//        adapter.clear()
//        searchResults.visibility = View.INVISIBLE
//        loading?.visibility = View.GONE
//        hideNoContentView()
//        hideLoadErrorView()
    }


    override fun loadData() {
        mPresenter.getHotword()
    }

    override fun initPresenter(): WanandroidSearchPresenter = WanandroidSearchPresenter(this)

    override fun displayHotword(hotwordList: ArrayList<WanandroidHotkeyword>) {
        hotkeywordAdapter.addAllData(hotwordList)
    }

    override fun displaySearchResult(searchResultList: ArrayList<WanAndroidItem>) {
        tv_search_count.visibility = View.GONE
//        tv_search_count.text = String.format(resources.getString(R.string.search_result_count), keyWords, issue.total)
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

    override fun onBackPressed() {
        if (layout_hot_words.visibility == View.GONE) { // 说明此时正在展示搜索结果
            layout_hot_words.visibility = View.VISIBLE
            layout_content_result.visibility = View.GONE
            return
        }
        super.onBackPressed()
    }

    override fun getLayoutId(): Int = R.layout.activity_search_public_history
}