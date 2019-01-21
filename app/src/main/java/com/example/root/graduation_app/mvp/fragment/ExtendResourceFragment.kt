package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/21
 *  desc:
 *  version:1.0
 */
class ExtendResourceFragment : BaseFragment() {

   companion object {
      @JvmStatic
      fun newInstance(): ExtendResourceFragment {
         return ExtendResourceFragment()
      }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview

   override fun initFragment(savedInstanceState: Bundle?) {

   }
}