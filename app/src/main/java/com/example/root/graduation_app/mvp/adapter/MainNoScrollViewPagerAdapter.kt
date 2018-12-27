package com.example.root.graduation_app.mvp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/26
 *  desc:
 *  version:1.0
 */
class MainNoScrollViewPagerAdapter constructor(fragmentManager: FragmentManager,
                                               private val list: List<Fragment>?) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getItem(i: Int): Fragment {
        return list!![i]
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }
}