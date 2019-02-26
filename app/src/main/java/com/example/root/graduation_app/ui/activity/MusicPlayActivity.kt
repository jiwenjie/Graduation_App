package com.example.root.graduation_app.ui.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.support.v4.content.ContextCompat
import android.view.animation.LinearInterpolator
import android.widget.SeekBar
import android.widget.Toast
import com.example.base_library.RxBus
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.MusicPlayService
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.Song
import com.example.root.graduation_app.rxbusevent.MusicPlayEvent
import com.example.root.graduation_app.utils.MusicUtils
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_music_player.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/23
 *  desc:
 *  version:1.0
 */
class MusicPlayActivity : BaseActivity() {

   private var song: Song? = null
   private var beanList: ArrayList<Song>? = null

   private var runing: Boolean = false  // 是否当前音乐是否在运行

   private var rotation: ObjectAnimator? = null

   private var currentProgress = -1 // 当前播放进度
   private var currentIndex = -1 // 歌曲当前在列表中的位置

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

   private val handler = @SuppressLint("HandlerLeak")
   object : Handler() {
      override fun handleMessage(msg: Message) {
         when (msg.what) {
            1 -> {
               try {
                  if (binder != null) {
                     activity_music_player_seekBar.max = binder!!.getProgress()
                     activity_music_player_seekBar.progress = binder!!.getPlayPosition()
                     activity_music_player_currentTimeText.text = MusicUtils.formatTime(binder!!.getPlayPosition())
                     activity_music_player_totalTimeText.text = MusicUtils.formatTime(binder!!.getProgress())   // 获取歌曲的长度
                  } else {
                     Toast.makeText(baseContext, "null", Toast.LENGTH_SHORT).show()
                  }
               } catch (e: Exception) {
                  e.printStackTrace()
               }
               this.sendEmptyMessageDelayed(1, 500)
            }
         }
      }
   }

   companion object {
      private const val KEY_index = "key_index"
      private const val KEY_LIST = "key_list"

      @JvmStatic
      fun runActivity(activity: Activity, currentIndex: Int, beanList: ArrayList<Song>) {
         val intent = Intent(activity, MusicPlayActivity::class.java)
         intent.putExtra(KEY_index, currentIndex)
         intent.putExtra(KEY_LIST, beanList)
         activity.startActivity(intent)
      }
   }

   @SuppressLint("CheckResult")
   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@MusicPlayActivity, ContextCompat.getColor(this@MusicPlayActivity, R.color.alpha_70_black))
      currentIndex = intent.getIntExtra(KEY_index, -1)
      beanList = intent.getSerializableExtra(KEY_LIST) as ArrayList<Song>
      if (currentIndex == -1 || beanList == null || beanList?.size == 0) return
      song = beanList!![currentIndex]
      createBindService()   // 创建绑定 Service
      activity_music_player_albumIng.setImageBitmap(MusicUtils.setArtwork(this@MusicPlayActivity, song?.path!!))
      startAlbumAnimation(0f)
      if (!runing) {
         runing = true
         RxBus.mBus.post(MusicPlayEvent(song!!, runing))
      }
      initView()
      initEvent()
      Thread(Runnable {
         val message = handler.obtainMessage()
         message.what = 1
         handler.sendMessage(message)
      }).start()
   }

   private fun initView() {

      activity_music_player_toolbarName.title = song?.name
      if (runing) {  // 如果表示正在运行
         activity_music_player_playImg.setImageDrawable(ContextCompat.getDrawable(this@MusicPlayActivity, R.drawable.pause))
      } else {
         activity_music_player_playImg.setImageDrawable(ContextCompat.getDrawable(this@MusicPlayActivity, R.drawable.music_start))
      }

      if (binder != null) {
         LogUtils.e("需要设置的最大值" + binder?.getProgress())
         activity_music_player_seekBar.max = binder!!.getProgress()
         activity_music_player_totalTimeText.text = binder?.getProgress().toString()   // 获取歌曲的长度
      }
      activity_music_player_seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
         override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//            binder?.seekToPositon(progress)
         }

         override fun onStartTrackingTouch(seekBar: SeekBar?) {

         }

         override fun onStopTrackingTouch(seekBar: SeekBar?) {
            binder?.seekToPositon(seekBar?.progress!!)   // 手指拖动
         }
      })
   }

   private fun initEvent() {
      activity_music_player_playImg.setOnClickListener {
         if (runing) {
            binder?.pause()
            currentProgress = binder?.getPlayPosition()!!
            if (rotation?.isRunning!!) {
               rotation?.pause()
            }
            activity_music_player_playImg.setImageDrawable(ContextCompat.getDrawable(this@MusicPlayActivity, R.drawable.music_start))
            runing = false
         } else {
            binder?.playMusic(song!!)
            if (currentProgress != -1) {
               binder?.seekToPositon(currentProgress)
            }
            rotation?.start()
            activity_music_player_playImg.setImageDrawable(ContextCompat.getDrawable(this@MusicPlayActivity, R.drawable.pause))
            runing = true
         }
         RxBus.mBus.post(MusicPlayEvent(song!!, runing))
      }

      activity_music_player_nextImg.setOnClickListener {
         // 下一曲
         if (currentIndex == -1) return@setOnClickListener
         if (currentIndex == (beanList!!.size - 1)) { // 说明已经是最后一首歌曲
            currentIndex = 0
         } else {
            currentIndex ++
         }
         updateMusicAndUI()
      }

      activity_music_player_previousImg.setOnClickListener {
         // 上一曲
         if (currentIndex == -1) return@setOnClickListener
         if (currentIndex == 0) {   // 当前已经是第一首歌曲
            currentIndex = beanList!!.size - 1
         } else {
            currentIndex --
         }
         updateMusicAndUI()
      }
   }

   /**
    * 点击上一曲下一曲之后重新播放歌曲并且更新 UI
    */
   private fun updateMusicAndUI() {
      song = beanList!![currentIndex]
      binder?.playMusic(song!!)
      Thread(Runnable {
         val message = handler.obtainMessage()
         message.what = 1
         handler.sendMessage(message)
      }).start()

      activity_music_player_toolbarName.title = song?.name     // 更新 title
      activity_music_player_albumIng.setImageBitmap(MusicUtils.setArtwork(this@MusicPlayActivity, song?.path!!))  // 获取专辑封面图片
      RxBus.mBus.post(MusicPlayEvent(song!!, runing))
   }

   /**
    * 进入播放界面之后开启专辑旋转动画
    */
   private fun startAlbumAnimation(start: Float) {
      if (start != 0f) {

      } else {
         rotation = ObjectAnimator.ofFloat(activity_music_player_contentRyt,
                 "rotation", 0f, 359f)    // 最好是 0f 到 359f，0f 和 360f 的位置是重复的
      }
      rotation?.repeatCount = ObjectAnimator.INFINITE
      rotation?.interpolator = LinearInterpolator()
      rotation?.duration = 9000
      rotation?.start()
   }

   override fun loadData() {

   }

   private fun createBindService() {
      val intent = Intent(this, MusicPlayService::class.java)
//      intent.putExtra(MusicPlayService.KEY_MUSIC, beanList)
      bindService(intent, connection, Context.BIND_AUTO_CREATE)    // 创建并绑定了 Service
   }

   override fun onDestroy() {
      unbindService(connection)
      super.onDestroy()
   }

   override fun getLayoutId(): Int = R.layout.activity_music_player
}