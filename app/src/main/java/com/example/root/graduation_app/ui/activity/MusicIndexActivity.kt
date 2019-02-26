package com.example.root.graduation_app.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.base_library.PermissionListener
import com.example.base_library.base_adapters.BaseFragmentPagerAdapter
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.ui.fragment.LocalMusicFragment
import com.example.root.graduation_app.ui.fragment.OnlineMusicFragment
import kotlinx.android.synthetic.main.activity_music_index.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/20
 *  desc:点击轻松一下跳转的界面，默认应该获取网络列表，有按钮可以展示本地音乐列表
 *  version:1.0
 */
class MusicIndexActivity : BaseActivity() {

   private val fragmentList by lazy { ArrayList<Fragment>() }
   private val mTitles by lazy { ArrayList<String>() }

   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, MusicIndexActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      // 动态申请权限
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 判断大于 6.0 进行权限请求
         onRuntimePermissionsAsk(arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE), object : PermissionListener {
            override fun onGranted() {
               initView()
            }

            override fun onDenied(deniedPermissions: List<String>) { // 未授权的话就跳转到网路歌曲界面吧
               ToastUtils.showToast(this@MusicIndexActivity, "未授权")
            }
         })
      }
   }

   private fun initView() {
      mTitles.add("本地音乐")
      mTitles.add("在线音乐")
      fragmentList.add(LocalMusicFragment.newInstance())
      fragmentList.add(OnlineMusicFragment.newInstance())

      activity_music_index_toolBar.setNavigationOnClickListener {
         finish()
      }
      activity_music_index_tabLyt.setupWithViewPager(activity_music_index_viewPager)
      activity_music_index_viewPager.adapter = BaseFragmentPagerAdapter(supportFragmentManager, fragmentList, mTitles)
      activity_music_index_viewPager.offscreenPageLimit = mTitles.size
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_music_index
}