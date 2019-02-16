package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.ui.adapter.KnowledgeAdapter
import com.jaeger.library.StatusBarUtil

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/28
 *  desc:
 *  version:1.0
 */
class CollectActivity : BaseActivity() {

   private val beanList by lazy { ArrayList<WanAndroidItem>() }
   private val collectAdapter by lazy { KnowledgeAdapter(this, beanList) }

   private var page = 1
   private var loadingMore = false

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, CollectActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@CollectActivity,
              ContextCompat.getColor(this@CollectActivity, R.color.colorPrimary), 0)


   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_collect
}