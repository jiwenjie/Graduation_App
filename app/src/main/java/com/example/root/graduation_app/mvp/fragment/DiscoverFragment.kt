package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import com.example.base_library.base_adapters.BaseFragmentPagerAdapter
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/24
 *  desc: 存放新闻页面
 *  version:1.0
 */
class DiscoverFragment : BaseFragment() {

   /* BaseFragmentPagerAdapter Demo */
   private lateinit var mFragmentAdapter: BaseFragmentPagerAdapter
   private val mFragments = ArrayList<BaseFragment>()
   private val mTitles = arrayOf("头条", "社会")
   private val mKeys = arrayOf("top", "shehui")

   private var types: Array<String>? = null         //顶部 tab 英文内容数组
   private var typesCN: Array<String>? = null       //顶部 tab 中文内容数组

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
      types = resources.getStringArray(R.array.news_type_en)
      typesCN = resources.getStringArray(R.array.news_type_cn)
   }
}