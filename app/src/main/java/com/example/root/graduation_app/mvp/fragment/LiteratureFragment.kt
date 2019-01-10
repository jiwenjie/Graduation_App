package com.example.root.graduation_app.mvp.fragment

import android.os.Bundle
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc:
 *  version:1.0
 */
class LiteratureFragment: BaseFragment() {

   companion object {
      @JvmStatic
      fun getInstance(): LiteratureFragment {
         val fragment = LiteratureFragment()
         val bundle = Bundle()
         fragment.arguments = bundle
         return fragment
      }
   }

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview

   override fun loadData() {

   }

   override fun initFragment(savedInstanceState: Bundle?) {
      TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
   }
}