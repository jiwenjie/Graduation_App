package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R
import kotlinx.android.synthetic.main.common_toolbar_layout.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/24
 *  desc: 存放新闻页面
 *  version:1.0
 */
class DiscoverFragment : BaseFragment() {

   /* BaseFragmentPagerAdapter Demo */
   private val fragmentList by lazy {  ArrayList<Fragment>() }
   private val titles by lazy {  ArrayList<String>() }

   companion object {
      @JvmStatic
       fun newInstance() : DiscoverFragment {
          return DiscoverFragment().apply {
             arguments = Bundle().apply {
                putString("", "")
             }
          }
       }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.common_tablayout_viewpager

   override fun initFragment(savedInstanceState: Bundle?) {
      initView()
   }

   private fun initView() {

      common_toolbar_title.text = "技术世界"

//      if (titles.size != 2 && fragmentList.size != 2) {
//         titles.add("移动")
//         titles.add("扩展资源")
//
//         fragmentList.add(MobileFragment.newInstance())
//         fragmentList.add(ExtendResourceFragment.newInstance())
//      }
//      container_vp.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragmentList, titles)
//      container_tab.setupWithViewPager(container_vp)
   }
}