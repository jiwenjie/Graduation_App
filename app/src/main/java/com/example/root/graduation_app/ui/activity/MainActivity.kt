package com.example.root.graduation_app.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.ui.adapter.MainNoScrollViewPagerAdapter
import com.example.root.graduation_app.ui.fragment.*
import com.jaeger.library.StatusBarUtil
import com.zhouwei.blurlibrary.EasyBlur
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_drawer_layout.*
import java.util.*

@SuppressLint("Registered")
class MainActivity : BaseActivity(), View.OnClickListener {

   private var mFragments = ArrayList<Fragment>()
   private var user: LoginUser? = null
   private var currentIndex = 0  // current fragment's index

   companion object {
      private const val KEY_USER = "key_user"

      fun runActivity(activity: Activity, user: LoginUser?) {
         val intent = Intent(activity, MainActivity::class.java)
         intent.putExtra(KEY_USER, user)
         activity.startActivity(intent)
      }
   }

   override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
      if (savedInstanceState != null) {
         currentIndex = savedInstanceState.getInt("currTabIndex")
      }
      super.onCreate(savedInstanceState, persistentState)
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColorForDrawerLayout(this, drawerLyt, ContextCompat.getColor(this, R.color.colorPrimary), 0)
      user = intent.getSerializableExtra(KEY_USER) as LoginUser?
      initView()
      initEvent()
   }

   private fun initView() {
      // set header style and data
      setHeader()
//      mFragments.add(IndexFragment.newInstance())
      mFragments.add(HomeFragment.newInstance())
      mFragments.add(WeChatFragment.newInstance())
      mFragments.add(BookFragment.newInstance())
      mFragments.add(KnowledgeTreeFragment.newInstance())
      home_container.adapter = MainNoScrollViewPagerAdapter(supportFragmentManager, mFragments)
      home_container.currentItem = currentIndex
      home_container.offscreenPageLimit = 3
   }

   /** set blur background **/
   private fun setHeader() {
      userNameText.text = user?.username ?: user?.userphone
      val source = BitmapFactory.decodeResource(resources, R.drawable.img_avatar)
      val bitmap = EasyBlur.with(applicationContext)
              .bitmap(source)
              .radius(20)
              .blur()
      activity_main_drawer_layoutImg.setImageBitmap(bitmap)
   }

   private fun initEvent() {
      indexLyt.setOnClickListener(this)
      discoveryLyt.setOnClickListener(this)
      entertainmentLyt.setOnClickListener(this)
      mineLyt.setOnClickListener(this)

      // set listener on function button in the Sidebar(侧滑栏)
      watch_historyLyt.setOnClickListener(this)
      study_bookLyt.setOnClickListener(this)
      person_profileLyt.setOnClickListener(this)
      night_moonLyt.setOnClickListener(this)
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
      when (v?.id) {
         R.id.indexLyt -> {
            (findViewById<ImageView>(R.id.indexLyt_img)).setImageResource(R.drawable.ic_home_selected)
            currentIndex = 0
         }
         R.id.discoveryLyt -> {
            (findViewById<ImageView>(R.id.discoveryLyt_img)).setImageResource(R.drawable.ic_discovery_selected)
            currentIndex = 1
         }
         R.id.entertainmentLyt -> {
            (findViewById<ImageView>(R.id.entertainmentLyt_img)).setImageResource(R.drawable.ic_hot_selected)
            currentIndex = 2
         }
         R.id.mineLyt -> {
            (findViewById<ImageView>(R.id.mineLyt_img)).setImageResource(R.drawable.ic_mine_selected)
            currentIndex = 3
         }
         /** Sidebar listing response **/
         R.id.watch_historyLyt -> {

         }
         R.id.study_bookLyt -> {

         }
         R.id.person_profileLyt -> {
            ProfileActivity.runActivity(this@MainActivity)
         }
         R.id.night_moonLyt -> {

         }
         else -> {
            ToastUtils.showToast(applicationContext, "结果有些偏差，请重试")
         }
      }
      home_container.currentItem = currentIndex
   }

   override fun getLayoutId(): Int = R.layout.activity_main

   @SuppressLint("MissingSuperCall")
   override fun onSaveInstanceState(outState: Bundle) {
      //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
      if (home_container != null) {
         outState.putInt("currTabIndex", currentIndex)
      }
   }

   private var mExitTime: Long = 0

   /**
    * don't appear accident touch
    */
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
