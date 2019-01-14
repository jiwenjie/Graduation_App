package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import com.example.base_library.base_adapters.BaseFragmentPagerAdapter
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_tablayout_viewpager.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/24
 *  desc:
 *  version:1.0
 */
class EntertainmentFragment : BaseFragment() {

   private val fragmentList by lazy {  ArrayList<Fragment>() }
   private val mTitles by lazy {  ArrayList<String>() }

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
      LogUtils.e("EntertainmentFragment initFragment()")
      initView()
   }

   private fun initView() {
      common_toolbar_title.text = "休闲文娱"
      common_toolbar_icon.setOnClickListener {
         activity!!.drawerLyt.openDrawer(Gravity.START)
      }

      if (mTitles.size != 4 && fragmentList.size != 4) {
         mTitles.add("文学")
         mTitles.add("文化")
         mTitles.add("生活")
         mTitles.add("影视")    // 单独调用接口

         fragmentList.add(DoubanLiteratureFragment.getInstance(mTitles[0]))
         fragmentList.add(DoubanLiteratureFragment.getInstance(mTitles[1]))
         fragmentList.add(DoubanLiteratureFragment.getInstance(mTitles[2]))
         fragmentList.add(DoubanMovieFragment.newInstance())
      }
      container_tab.setupWithViewPager(container_vp)
      container_vp.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragmentList, mTitles)
   }
}