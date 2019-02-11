package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.example.base_library.base_adapters.BaseFragmentPagerAdapter
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.ui.fragment.TodoFragment
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.common_tablayout_viewpager.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:查看 todo 列表的界面
 *  version:1.0
 */
class TodoActivity: BaseActivity() {

   private val fragmentList by lazy { ArrayList<Fragment>() }
   private val mTitles by lazy { ArrayList<String>() }

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, TodoActivity::class.java)
         intent.putExtra("", "")
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(
              this@TodoActivity,
              ContextCompat.getColor(this@TodoActivity, R.color.colorPrimary),
              0)

   }

   override fun loadData() {

      mTitles.add("未完成")
      mTitles.add("已完成")
      fragmentList.add(TodoFragment.newInstance(false))
      fragmentList.add(TodoFragment.newInstance(true))

      container_tab.setupWithViewPager(container_vp)
      container_vp.adapter = BaseFragmentPagerAdapter(supportFragmentManager, fragmentList, mTitles)
      container_vp.offscreenPageLimit = fragmentList.size
   }

   override fun getLayoutId(): Int = R.layout.common_tablayout_viewpager
}