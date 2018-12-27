package com.example.root.graduation_app.mvp.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.mvp.adapter.MainNoScrollViewPagerAdapter
import com.example.root.graduation_app.mvp.fragment.DiscoverFragment
import com.example.root.graduation_app.mvp.fragment.EntertainmentFragment
import com.example.root.graduation_app.mvp.fragment.IndexFragment
import com.example.root.graduation_app.mvp.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener {

    private var mFragments = ArrayList<Fragment>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initActivity(savedInstanceState: Bundle?) {
        initView()
        initEvent()
    }

    private fun initView() {
        mFragments.add(IndexFragment.newInstance())
        mFragments.add(DiscoverFragment.newInstance())
        mFragments.add(EntertainmentFragment.newInstance())
        mFragments.add(MineFragment.newInstance())
        home_container.adapter = MainNoScrollViewPagerAdapter(supportFragmentManager, mFragments)
        home_container.currentItem = 0
    }

    private fun initEvent() {
        indexLyt.setOnClickListener(this)
        discoveryLyt.setOnClickListener(this)
        entertainmentLyt.setOnClickListener(this)
        mineLyt.setOnClickListener(this)
//        indexLyt.setOnClickListener {
//            setNormalBackground()
//            (findViewById<ImageView>(R.id.indexLyt_img)).setImageResource(R.drawable.ic_home_selected)
//            home_container.currentItem = 0
//        }
//        discoveryLyt.setOnClickListener {
//            setNormalBackground()
//            (findViewById<ImageView>(R.id.discoveryLyt_img)).setImageResource(R.drawable.ic_discovery_selected)
//            home_container.currentItem = 1
//        }
//        entertainmentLyt.setOnClickListener {
//            setNormalBackground()
//            (findViewById<ImageView>(R.id.entertainmentLyt_img)).setImageResource(R.drawable.ic_hot_selected)
//            home_container.currentItem = 2
//        }
//        mineLyt.setOnClickListener {
//            setNormalBackground()
//            (findViewById<ImageView>(R.id.mineLyt_img)).setImageResource(R.drawable.ic_mine_selected)
//            home_container.currentItem = 3
//        }
    }

    override fun loadData() {

    }

    private fun setNormalBackground() {
        (findViewById<ImageView>(R.id.indexLyt_img)).setImageResource(R.drawable.ic_home_normal)
        (findViewById<ImageView>(R.id.discoveryLyt_img)).setImageResource(R.drawable.ic_discovery_normal)
        (findViewById<ImageView>(R.id.entertainmentLyt_img)).setImageResource(R.drawable.ic_hot_normal)
        (findViewById<ImageView>(R.id.mineLyt_img)).setImageResource(R.drawable.ic_mine_normal)
    }

    override fun onClick(v: View?) {
        setNormalBackground()
        when {
            v?.id == R.id.indexLyt -> {
                (findViewById<ImageView>(R.id.indexLyt_img)).setImageResource(R.drawable.ic_home_selected)
                home_container.currentItem = 0
            }
            v?.id == R.id.discoveryLyt -> {
                (findViewById<ImageView>(R.id.discoveryLyt_img)).setImageResource(R.drawable.ic_discovery_selected)
                home_container.currentItem = 1
            }
            v?.id == R.id.entertainmentLyt -> {
                (findViewById<ImageView>(R.id.entertainmentLyt_img)).setImageResource(R.drawable.ic_hot_selected)
                home_container.currentItem = 2
            }
            v?.id == R.id.mineLyt -> {
                (findViewById<ImageView>(R.id.mineLyt_img)).setImageResource(R.drawable.ic_mine_selected)
                home_container.currentItem = 3
            }
        }
    }

    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                ToastUtils.showToast(applicationContext, "再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
