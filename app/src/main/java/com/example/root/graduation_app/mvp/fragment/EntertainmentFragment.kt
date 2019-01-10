package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.base_library.base_adapters.BaseFragmentPagerAdapter
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R
import com.example.root.graduation_app.R.id.container_tab
import com.example.root.graduation_app.R.id.container_vp
import kotlinx.android.synthetic.main.common_tablayout_viewpager.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/24
 *  desc:
 *  version:1.0
 */
class EntertainmentFragment : BaseFragment() {

   private val mTitles = ArrayList<String>()
   private val fragmentList = ArrayList<Fragment>()

   companion object {
      @JvmStatic
       fun newInstance() : EntertainmentFragment {
          val fragment = EntertainmentFragment()
          val bundle = Bundle()
          fragment.arguments = bundle
          return fragment
       }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.common_tablayout_viewpager

   override fun initFragment(savedInstanceState: Bundle?) {
      initView()
   }

   private fun initView() {
      mTitles.add("文学")
      mTitles.add("文化")
      mTitles.add("生活")
//      mTitles.add("影视")    // 单独调用接口

      fragmentList.add(LiteratureFragment.getInstance(mTitles[0]))
      fragmentList.add(LiteratureFragment.getInstance(mTitles[1]))
      fragmentList.add(LiteratureFragment.getInstance(mTitles[2]))
//      fragmentList.add(LiteratureFragment.getInstance(mTitles[3]))
      container_tab.setupWithViewPager(container_vp)
      container_vp.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragmentList, mTitles)
   }
}