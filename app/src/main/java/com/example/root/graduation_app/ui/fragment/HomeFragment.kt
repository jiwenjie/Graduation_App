package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import com.alibaba.fastjson.JSON
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.ErrorStatus
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean
import com.example.root.graduation_app.mvp.constract.ProjectContract
import com.example.root.graduation_app.mvp.presenter.ProjectPresenter
import com.example.root.graduation_app.ui.adapter.ProjectPagerAdapter
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.SharePreferencesUtil
import kotlinx.android.synthetic.main.fragment_project_index.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
class HomeFragment : BaseMvpFragment<ProjectContract.View, ProjectPresenter>(), ProjectContract.View {

    /**
     * ProjectTreeBean
     */
    private var projectTree = mutableListOf<WanAndroidPublicItemBean>()

    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: ProjectPagerAdapter by lazy {
        ProjectPagerAdapter(projectTree, childFragmentManager)
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun loadData() {
        mPresenter.requestProjectTree()
    }

    override fun initFragment(savedInstanceState: Bundle?) {
        mLayoutStatusView = multiple_status_view
        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }

        tabLayout.run {
            setupWithViewPager(viewPager)
            // TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(onTabSelectedListener)
        }
    }

    override fun initPresenter(): ProjectPresenter = ProjectPresenter(this)

    /**
     * onTabSelectedListener
     */
    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
            tab?.let {
                viewPager.setCurrentItem(it.position, false)    //false：代表快速切换 true：表示切换速度慢
            }
        }
    }

    override fun scrollToTop() {
        if (viewPagerAdapter.count == 0) {
            return
        }
        val fragment: ProjectListFragment = viewPagerAdapter.getItem(viewPager.currentItem) as ProjectListFragment
        fragment.scrollToTop()
    }

    override fun setProjectTree(list: List<WanAndroidPublicItemBean>) {
        list.let {
            projectTree.addAll(it)
            viewPager.run {
                adapter = viewPagerAdapter
                offscreenPageLimit = projectTree.size
            }
        }
        if (list.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
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

    override fun getLayoutId(): Int  = R.layout.fragment_project_index
}