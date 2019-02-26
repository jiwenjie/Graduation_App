package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.base_library.RxBus
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.MusicPlayService
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.Song
import com.example.root.graduation_app.rxbusevent.MusicPlayEvent
import com.example.root.graduation_app.ui.adapter.LocalMusicAdapter
import com.example.root.graduation_app.utils.MusicUtils
import com.example.root.graduation_app.utils.SpaceItemDecoration
import com.jaeger.library.StatusBarUtil
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_local_music.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/24
 *  desc:
 *  version:1.0
 */
class LocalMusicActivity: BaseActivity() {

   private val beanList by lazy { ArrayList<Song>() }
   private val localAdapter by lazy { LocalMusicAdapter(this, beanList) }

   private var song: Song? = null

   private var currentIndex = -1 // 代表当前点击播放的音乐位置

   /**
    * 获取 Service，使用 bind 方式绑定；
    */
   private var binder: MusicPlayService.MusicServiceBinder? = null

   private val connection = object : ServiceConnection {
      override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
         binder = service as MusicPlayService.MusicServiceBinder
      }
      override fun onServiceDisconnected(name: ComponentName?) {

      }
   }


   companion object {
      @JvmStatic
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, LocalMusicActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@LocalMusicActivity, ContextCompat.getColor(this@LocalMusicActivity, R.color.colorPrimary))
      activity_local_music_toolBar.setNavigationOnClickListener {
         finish()
      }
      initView()
      createBindService()
      initEvent()
   }

   private fun initView() {
      activity_local_music_recyclerView.layoutManager = LinearLayoutManager(this)
      activity_local_music_recyclerView.adapter = localAdapter
      activity_local_music_recyclerView.addItemDecoration(SpaceItemDecoration(this))
      localAdapter.setOnItemClickListener { position, view ->
//         this.song = beanList[position]
         currentIndex = position
         MusicPlayActivity.runActivity(this, currentIndex, beanList)
         binder?.playMusic(beanList[position])
      }
   }

   private fun initEvent() {
      activity_local_music_bottomLyt.setOnClickListener {
         if (song != null && currentIndex != -1) {
            MusicPlayActivity.runActivity(this, currentIndex, beanList)
         }
      }
   }

   override fun loadData() {
      showProgress(null)
      val songList = MusicUtils.getMusicData(this)
      dismissProgress()
      if (songList.isEmpty()) {
         activity_local_music_recyclerView.visibility = View.GONE
         activity_local_music_noDataLyt.visibility = View.VISIBLE
      } else {
         activity_local_music_recyclerView.visibility = View.VISIBLE
         activity_local_music_noDataLyt.visibility = View.GONE
         localAdapter.addAllData(songList)
      }
   }

   private fun createBindService() {
      val intent = Intent(this, MusicPlayService::class.java)
//      intent.putExtra(MusicPlayService.KEY_MUSIC, beanList)
      bindService(intent, connection, Context.BIND_AUTO_CREATE)    // 创建并绑定了 Service
   }

   /**
    * 播放成功后通知把底部栏显示出来
    */
   override fun handleRxBus() {
      RxBus.mBus.register(this@LocalMusicActivity, MusicPlayEvent::class.java,
              Consumer {
                 activity_local_music_bottomLyt.visibility = View.VISIBLE
              })
   }

   override fun onDestroy() {
      RxBus.mBus.unregister(this@LocalMusicActivity)
      super.onDestroy()
   }

   override fun getLayoutId(): Int = R.layout.activity_local_music
}