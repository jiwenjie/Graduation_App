package com.example.root.graduation_app.ui.fragment

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.widget.LinearLayoutManager
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.MusicPlayService
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.Song
import com.example.root.graduation_app.ui.activity.MusicPlayActivity
import com.example.root.graduation_app.ui.adapter.LocalMusicAdapter
import com.example.root.graduation_app.utils.MusicUtils
import com.example.root.graduation_app.utils.SpaceItemDecoration
import kotlinx.android.synthetic.main.common_multiple_recyclerview.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/21
 *  desc: 已弃用
 *  version:1.0
 */
class LocalMusicFragment : BaseFragment() {

   private val beanList by lazy { ArrayList<Song>() }
   private val localAdapter by lazy { LocalMusicAdapter(activity!!, beanList) }

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
      fun newInstance(): LocalMusicFragment {
         return LocalMusicFragment().apply {
            val bundle = arguments.run {

            }
         }
      }
   }

   override fun initFragment(savedInstanceState: Bundle?) {
      mLayoutStatusView = common_multipleStatusView
      mLayoutStatusView?.showContent()

      commonRv.layoutManager = LinearLayoutManager(activity)
      commonRv.addItemDecoration(SpaceItemDecoration(activity!!))
      commonRv.adapter = localAdapter  // 因为是一次性获取，所以不需要滚动监听了
      localAdapter.setOnItemClickListener { position, view ->
         MusicPlayActivity.runActivity(activity!!, position, beanList)  // 跳转过去的同时开始播放音乐
         binder?.playMusic(beanList[position])
      }

      localAdapter.setOnStartPlayClickListener(object : LocalMusicAdapter.StartPlayClickListener {
         override fun onOperationClick(song: Song, currPlay: Boolean) {
            if (currPlay) {
               binder?.playMusic(song)
            } else {
               binder?.pause()
            }
         }
      })
   }

   private fun createBindService(beanList: ArrayList<Song>) {
      val intent = Intent(activity, MusicPlayService::class.java)
      intent.putExtra(MusicPlayService.KEY_MUSIC, beanList)
      activity?.bindService(intent, connection, BIND_AUTO_CREATE)    // 创建并绑定了 Service
   }

   override fun loadData() {
      mLayoutStatusView?.showLoading()
      val songList = MusicUtils.getMusicData(activity!!)
      if (songList.isEmpty()) {
         mLayoutStatusView?.showEmpty()
      } else {
         mLayoutStatusView?.showContent()
         createBindService(songList)   // 创建绑定 Service
         localAdapter.addAllData(songList)
      }
   }

   override fun onDestroy() {
      activity?.unbindService(connection)
      super.onDestroy()
   }

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview
}
