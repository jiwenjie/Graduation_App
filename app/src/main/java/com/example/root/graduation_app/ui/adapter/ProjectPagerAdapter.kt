package com.example.root.graduation_app.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.text.Html
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean
import com.example.root.graduation_app.ui.fragment.ProjectListFragment

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
@Suppress("DEPRECATION")
class ProjectPagerAdapter(private val list: MutableList<WanAndroidPublicItemBean>, fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

   private val fragments = mutableListOf<Fragment>()

   init {
      fragments.clear()
      list.forEach {
         fragments.add(ProjectListFragment.getInstance(it.id))
      }
   }

   override fun getItem(position: Int): Fragment = fragments[position]

   override fun getCount(): Int = list.size

   override fun getPageTitle(position: Int): CharSequence? = Html.fromHtml(list[position].name)

   override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}