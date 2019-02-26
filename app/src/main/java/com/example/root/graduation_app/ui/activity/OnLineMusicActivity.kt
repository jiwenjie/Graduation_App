package com.example.root.graduation_app.ui.activity

import android.os.Bundle
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/21
 *  desc:当用户拒绝了读写权限之后跳转网络歌曲界面，否则就不跳转留在 tab 页
 *  version:1.0
 */
class OnLineMusicActivity : BaseActivity() {

   override fun initActivity(savedInstanceState: Bundle?) {

   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_online_music
}