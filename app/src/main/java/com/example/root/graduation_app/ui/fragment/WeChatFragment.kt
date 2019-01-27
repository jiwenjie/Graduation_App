package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.base_library.base_adapters.BaseFragmentPagerAdapter
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean
import com.example.root.graduation_app.mvp.constract.WanandroidContract
import com.example.root.graduation_app.mvp.presenter.WanandroidPublicPresenter
import kotlinx.android.synthetic.main.common_tablayout_viewpager.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/24
 *  desc: 显示微信公众号的页面
 *  version:1.0
 */
class WeChatFragment : BaseMvpFragment<WanandroidContract.WanandroidPublicView, WanandroidPublicPresenter>(),
    WanandroidContract.WanandroidPublicView {

    /* BaseFragmentPagerAdapter Demo */
    private val fragmentList by lazy { ArrayList<Fragment>() }
    private val titles by lazy { ArrayList<String>() }

    private val idList by lazy { ArrayList<Int>() }

    companion object {
        @JvmStatic
        fun newInstance(): WeChatFragment {
            return WeChatFragment().apply {
                arguments = Bundle().apply {
                    putString("", "")
                }
            }
        }
    }

    override fun loadData() {
        mPresenter.getPublicAddressList()
    }

    override fun getLayoutId(): Int = R.layout.fragment_discovery

    override fun initFragment(savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
//        common_toolbar_title.text = "技术世界"
//        common_toolbar_icon.visibility = View.GONE   // 隐藏头像
//        common_toolbar_search.visibility = View.VISIBLE    // 显示搜索框
//        common_toolbar_search.setOnClickListener {
//            SearchActivity.runActivity(activity!!, idList[container_tab.selectedTabPosition])
//        }
    }

    override fun initPresenter(): WanandroidPublicPresenter = WanandroidPublicPresenter(this)

    override fun displayPublicNumList(publicBeanList: ArrayList<WanAndroidPublicItemBean>) {
        LogUtils.e("DDDD" + publicBeanList)
        if (titles.size == 0 && (titles.size == fragmentList.size)) {
            publicBeanList.forEach {
                titles.add(it.name)
                idList.add(it.id)
                fragmentList.add(WanandroidTabItemFragment.newInstance(it.id))
            }
        }
        container_vp.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragmentList, titles)
        container_tab.setupWithViewPager(container_vp)
    }
}