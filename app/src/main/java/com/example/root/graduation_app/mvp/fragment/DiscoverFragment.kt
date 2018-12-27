package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/24
 *  desc:
 *  version:1.0
 */
class DiscoverFragment : BaseFragment() {

   companion object {
      @JvmStatic
       fun newInstance() : DiscoverFragment {
          val fragment = DiscoverFragment()
          val bundle = Bundle()
          fragment.arguments = bundle
          return fragment
       }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.fragment_discovery

   override fun initFragment(savedInstanceState: Bundle?) {

   }
}