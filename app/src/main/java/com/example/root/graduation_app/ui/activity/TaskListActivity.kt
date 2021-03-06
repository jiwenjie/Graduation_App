package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.example.base_library.base_adapters.BaseFragmentPagerAdapter
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.ui.fragment.TaskListFragment
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_task_list.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/04
 *  desc:查看 todoTask 列表的界面
 *  version:1.0
 */
class TaskListActivity: BaseActivity() {

   private val fragmentList by lazy { ArrayList<Fragment>() }
   private val mTitles by lazy { ArrayList<String>() }

   private val noComplete by lazy { TaskListFragment.newInstance(false) }
   private val complete by lazy { TaskListFragment.newInstance(true) }

   companion object {
      private const val REQ_CREATE = 12035

      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, TaskListActivity::class.java)
         intent.putExtra("", "")
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(
              this@TaskListActivity,
              ContextCompat.getColor(this@TaskListActivity, R.color.colorPrimary),
              0)

      activity_todo_toolbar.setNavigationOnClickListener {
         finish() // 点击返回按钮监听
      }

      activity_todo_newTask.setOnClickListener {
         // 点击新建
         CreateTaskActivity.runActivity(this@TaskListActivity, null, REQ_CREATE, true)
      }
   }

   override fun loadData() {
      mTitles.add("未完成")
      mTitles.add("已完成")
      fragmentList.add(noComplete)
      fragmentList.add(complete)

      activity_todo_tabLyt.setupWithViewPager(activity_todo_vp)
      activity_todo_vp.adapter = BaseFragmentPagerAdapter(supportFragmentManager, fragmentList, mTitles)
      activity_todo_vp.offscreenPageLimit = fragmentList.size
   }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      if (resultCode != Activity.RESULT_OK) return
      // 新建成功之后需要刷新数据
      if (requestCode == REQ_CREATE) {    // 新建后刷新
         noComplete.adapter.clearData()
         noComplete.loadData()
      } else if (requestCode == TaskListFragment.REQ_REFLESH) {   // 变更状态后刷新
         noComplete.adapter.clearData()
         noComplete.loadData()
         complete.adapter.clearData()
         complete.loadData()
      }
   }

   override fun getLayoutId(): Int = R.layout.activity_task_list
}