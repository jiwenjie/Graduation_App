package com.example.root.graduation_app

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.bean.Song

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/23
 *  desc:音乐播放的实现，使用 Service
 *  version:1.0
 */
class MusicPlayService : Service() {

   companion object {
      const val KEY_MUSIC = "key_music"
   }

   private val mMediaPlayer by lazy { MediaPlayer() }

   override fun onBind(intent: Intent): IBinder? {
      //开启服务
      //其他对象通过bindService 方法通知该Service时该方法被调用
      LogUtils.e("onBind")
      //返回 对象
      return MusicServiceBinder()
   }

   /**
    * 添加file文件到MediaPlayer对象并且准备播放音频
    */
   private fun iniMediaPlayerFile(song: Song) {
      try {
         mMediaPlayer.reset()
         mMediaPlayer.setDataSource(song.path)
         mMediaPlayer.prepare()
      } catch (e: Exception) {
         e.printStackTrace()
      }

   }

   //用来返回 musicservice 对象
   //binder 可以返回 bind对象
   inner class MusicServiceBinder : Binder() {
      /**
       * 播放音乐
       */
      fun playMusic(song: Song) {
         iniMediaPlayerFile(song)
         if (!mMediaPlayer.isPlaying) {
            //如果还没开始播放，就开始
            mMediaPlayer.start()
         }
      }

      /**
       * 暂停音乐播放
       */
      fun pause() {
         if (mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
         }
      }

      /**
       * 获取歌曲长度
       */
      fun getProgress(): Int {
         return mMediaPlayer.duration     //获取文件的总长度
      }

      /**
       * 获取播放位置
       */
      fun getPlayPosition(): Int {
         return mMediaPlayer.currentPosition
      }

      /**
       * 播放指定位置
       */
      fun seekToPositon(position: Int) {
         mMediaPlayer.seekTo(position)
      }

   }

   override fun onDestroy() {
      //销毁时
      LogUtils.e("onDestroy")
      //停止
      mMediaPlayer.stop()
      super.onDestroy()
   }

   override fun onUnbind(intent: Intent): Boolean {
      //其它对象通过unbindService方法通知该Service时该方法被调用
      //解绑定操作
      LogUtils.e("onUnbind")
      //停止
      return super.onUnbind(intent)
   }
}