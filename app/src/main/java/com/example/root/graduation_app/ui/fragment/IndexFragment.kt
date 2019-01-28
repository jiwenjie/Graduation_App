package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import com.example.base_library.base_adapters.BaseFragmentPagerAdapter
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.ui.activity.CommonWebViewActivity
import com.example.root.graduation_app.utils.ConstantConfig
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_toolbar_layout.*
import kotlinx.android.synthetic.main.fragment_index.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/24
 *  desc:
 *  version:1.0
 */
class IndexFragment : BaseFragment() {

   private var user: LoginUser? = null
   private val fragmentList by lazy {  ArrayList<Fragment>() }
   private val titles by lazy {  ArrayList<String>() }

   companion object {
      @JvmStatic
      fun newInstance(): IndexFragment {
         val fragment = IndexFragment()
         val bundle = Bundle()
         fragment.arguments = bundle
         return fragment
      }
   }

   override fun loadData() {
      fab_download.setOnClickListener {
         CommonWebViewActivity.runActivity(activity!!, "github", ConstantConfig.GITHUB_URL)
      }
   }

   override fun getLayoutId(): Int = R.layout.fragment_index

   override fun initFragment(savedInstanceState: Bundle?) {
      LogUtils.e("IndexFragment initFragment()")
      initView()
   }

   private fun initView() {
      common_toolbar_title.text = "推荐"
      // set header style and data
      common_toolbar_icon.setOnClickListener {
         activity!!.drawerLyt.openDrawer(Gravity.START)
      }

      /**
       * 生命周期判断每次都会调用该方法，所以为了避免 bug，也为了减少内存开销，此处做一个判断
       */
      if (titles.size != 2 && fragmentList.size != 2) {
         titles.add("知乎日报")
//         titles.add("网易新闻")
         titles.add("微信精选")

         fragmentList.add(ZhihuFragment.getInstance())
//         fragmentList.add(WangyiFragment.getInstance())
         fragmentList.add(WeixinFragment.getInstance())
      }
      indexViewpager.adapter = BaseFragmentPagerAdapter(childFragmentManager, fragmentList, titles)
      indexTabLayout.setupWithViewPager(indexViewpager)
   }
}