package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.View
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.KnowledgeTreeBody
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean
import com.example.root.graduation_app.ui.adapter.KnowledgePagerAdapter
import com.example.root.graduation_app.ui.fragment.KnowledgeFragment
import com.example.root.graduation_app.utils.Constants
import kotlinx.android.synthetic.main.activity_knowledge.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
class KnowledgeActivity : BaseActivity() {


   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {

      }
   }

   /**
    * datas
    */
   private var knowledges = ArrayList<WanAndroidPublicItemBean>()

   /**
    * toolbar title
    */
   private lateinit var toolbarTitle: String

   override fun initActivity(savedInstanceState: Bundle?) {
      initView()
      toolbar.run {
         title = toolbarTitle
         setSupportActionBar(this)
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
         //StatusBarUtil2.setPaddingSmart(this@KnowledgeActivity, toolbar)
      }
      viewPager.run {
         adapter = viewPagerAdapter
         addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
         offscreenPageLimit = knowledges.size
      }
      tabLayout.run {
         setupWithViewPager(viewPager)
         // TabLayoutHelper.setUpIndicatorWidth(tabLayout)
         addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
         addOnTabSelectedListener(onTabSelectedListener)
      }
      floating_action_btn.run {
         setOnClickListener(onFABClickListener)
      }
   }

   private fun initView() {
      intent.extras?.let {
         toolbarTitle = it.getString(Constants.CONTENT_TITLE_KEY) ?: ""
         it.getSerializable(Constants.CONTENT_DATA_KEY)?.let {


            val data = it as KnowledgeTreeBody
            knowledges.addAll(data.children)
//            knowledges.addAll(data.children)
//            data.children.let {
//               knowledges.addAll()
//            }
         }
      }
   }

   override fun loadData() {

   }

   /**
    * ViewPagerAdapter
    */
   private val viewPagerAdapter: KnowledgePagerAdapter by lazy {
      KnowledgePagerAdapter(knowledges, supportFragmentManager)
   }


   /**
    * onTabSelectedListener
    */
   private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: TabLayout.Tab?) {
      }

      override fun onTabUnselected(tab: TabLayout.Tab?) {
      }

      override fun onTabSelected(tab: TabLayout.Tab?) {
         // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
         tab?.let {
            viewPager.setCurrentItem(it.position, false)
         }
      }
   }

   /**
    * FAB 监听
    */
   private val onFABClickListener = View.OnClickListener {
      if (viewPagerAdapter.count == 0) {
         return@OnClickListener
      }
      val fragment: KnowledgeFragment = viewPagerAdapter.getItem(viewPager.currentItem) as KnowledgeFragment
      fragment.scrollToTop()
   }


   override fun getLayoutId(): Int = R.layout.activity_knowledge
}