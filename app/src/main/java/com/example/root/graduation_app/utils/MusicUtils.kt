package com.example.root.graduation_app.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.Song
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

/**
 * 音乐工具类,
 */
object MusicUtils {

   //获取专辑封面的Uri
   private val albumArtUri = Uri.parse("content://media/external/audio/albumart")

   /**
    * 扫描系统里面的音频文件，返回一个list集合
    */
   fun getMusicData(context: Context): ArrayList<Song> {
      val list = ArrayList<Song>()
      // 媒体库查询语句（写一个工具类MusicUtils）
      val cursor = context.contentResolver.query(
              MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
              null,
              null,
              null,
              MediaStore.Audio.AudioColumns.IS_MUSIC
      )
      if (cursor != null) {
         while (cursor.moveToNext()) {
            val song = Song()
            song.id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
            song.name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
            song.song = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
            song.singer = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))
            song.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))
            song.duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))
            song.size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
            song.albumId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
            if (song.size > 1000 * 800) {
               // 注释部分是切割标题，分离出歌曲名和歌手 （本地媒体库读取的歌曲信息不规范）
               if (song.song!!.contains("-")) {
                  val str = song.song!!.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                  song.singer = str[0]
                  song.song = str[1]
               }
               list.add(song)
            }
         }
         // 释放资源
         cursor.close()
      }
      return list
   }

   /**
    * 定义一个方法用来格式化获取到的时间
    */
   fun formatTime(time: Int): String {
      return if (time / 1000 % 60 < 10) {
         (time / 1000 / 60).toString() + ":0" + time / 1000 % 60
      } else {
         (time / 1000 / 60).toString() + ":" + time / 1000 % 60
      }
   }

   /**
    * 获取专辑封面位图对象
    *
    * @param context
    * @param song_id
    * @param album_id
    * @param allowdefalut
    * @return
    */
   fun getArtwork(context: Context, song_id: Long, album_id: Long, allowdefalut: Boolean, small: Boolean): Bitmap? {
      if (album_id < 0) {
         if (song_id < 0) {
            val bm = getArtworkFromFile(context, song_id, -1)
            if (bm != null) {
               return bm
            }
         }
         return if (allowdefalut) {
            getDefaultArtwork(context, small)
         } else null
      }
      val res = context.contentResolver
      val uri = ContentUris.withAppendedId(albumArtUri, album_id)
      if (uri != null) {
         var `in`: InputStream? = null
         try {
            `in` = res.openInputStream(uri)
            val options = BitmapFactory.Options()
            //先制定原始大小
            options.inSampleSize = 1
            //只进行大小判断
            options.inJustDecodeBounds = true
            //调用此方法得到options得到图片的大小
            BitmapFactory.decodeStream(`in`, null, options)
            /** 我们的目标是在你N pixel的画面上显示。 所以需要调用computeSampleSize得到图片缩放的比例  */
            /** 这里的target为800是根据默认专辑图片大小决定的，800只是测试数字但是试验后发现完美的结合  */
            if (small) {
               options.inSampleSize = computeSampleSize(options, 40)
            } else {
               options.inSampleSize = computeSampleSize(options, 600)
            }
            // 我们得到了缩放比例，现在开始正式读入Bitmap数据
            options.inJustDecodeBounds = false
            options.inDither = false
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            `in` = res.openInputStream(uri)
            return BitmapFactory.decodeStream(`in`, null, options)
         } catch (e: FileNotFoundException) {
            var bm = getArtworkFromFile(context, song_id, album_id)
            if (bm != null) {
               if (bm.config == null) {
                  bm = bm.copy(Bitmap.Config.RGB_565, false)
                  if (bm == null && allowdefalut) {
                     return getDefaultArtwork(context, small)
                  }
               }
            } else if (allowdefalut) {
               bm = getDefaultArtwork(context, small)
            }
            return bm
         } finally {
            try {
               `in`?.close()
            } catch (e: IOException) {
               e.printStackTrace()
            }

         }
      }
      return null
   }

   /**
    * 实现获取歌曲的专辑封面方法
    * @param context
    * @param url
    * @return
    */
   fun setArtwork(context: Context, url: String): Bitmap {
      val selectedAudio = Uri.parse(url)
      val myRetriever = MediaMetadataRetriever()
      myRetriever.setDataSource(context, selectedAudio) // the URI of audio file
      val artwork: ByteArray?

      artwork = myRetriever.embeddedPicture

      return if (artwork != null) {
         BitmapFactory.decodeByteArray(artwork, 0, artwork.size)
      } else {
         //ivPic.setImageResource(R.drawable.ic_menu_camera);
         BitmapFactory.decodeResource(context.resources, R.drawable.default_album)
      }
   }

   /**
    * 从文件当中获取专辑封面位图
    *
    * @param context
    * @param songid
    * @param albumid
    * @return
    */
   private fun getArtworkFromFile(context: Context, songid: Long, albumid: Long): Bitmap? {
      var bm: Bitmap? = null
      if (albumid < 0 && songid < 0) {
         throw IllegalArgumentException("Must specify an album or a song id")
      }
      try {
         val options = BitmapFactory.Options()
         var fd: FileDescriptor? = null
         if (albumid < 0) {
            val uri = Uri.parse("content://media/external/audio/media/"
                    + songid + "/albumart")
            val pfd = context.contentResolver.openFileDescriptor(uri, "r")
            if (pfd != null) {
               fd = pfd.fileDescriptor
            }
         } else {
            val uri = ContentUris.withAppendedId(albumArtUri, albumid)
            val pfd = context.contentResolver.openFileDescriptor(uri, "r")
            if (pfd != null) {
               fd = pfd.fileDescriptor
            }
         }
         options.inSampleSize = 1
         // 只进行大小判断
         options.inJustDecodeBounds = true
         // 调用此方法得到options得到图片大小
         BitmapFactory.decodeFileDescriptor(fd, null, options)
         // 我们的目标是在800pixel的画面上显示
         // 所以需要调用computeSampleSize得到图片缩放的比例
         options.inSampleSize = 100
         // 我们得到了缩放的比例，现在开始正式读入Bitmap数据
         options.inJustDecodeBounds = false
         options.inDither = false
         options.inPreferredConfig = Bitmap.Config.ARGB_8888

         //根据options参数，减少所需要的内存
         bm = BitmapFactory.decodeFileDescriptor(fd, null, options)
      } catch (e: FileNotFoundException) {
         e.printStackTrace()
      }

      return bm
   }

   /**
    * 获取默认专辑图片
    *
    * @param context
    * @return
    */
   @SuppressLint("ResourceType")
   fun getDefaultArtwork(context: Context, small: Boolean): Bitmap? {
      val opts = BitmapFactory.Options()
      opts.inPreferredConfig = Bitmap.Config.RGB_565
      if (small) {    //返回小图片
         //return
         BitmapFactory.decodeStream(context.resources.openRawResource(R.drawable.user_home_page_bg_default), null, opts)
      }
      //return BitmapFactory.decodeStream(context.getResources().openRawResource(R.drawable.defaultalbum), null, opts);
      return null
   }

   /**
    * 对图片进行合适的缩放
    *
    * @param options
    * @param target
    * @return
    */
   fun computeSampleSize(options: BitmapFactory.Options, target: Int): Int {
      val w = options.outWidth
      val h = options.outHeight
      val candidateW = w / target
      val candidateH = h / target
      var candidate = Math.max(candidateW, candidateH)
      if (candidate == 0) {
         return 1
      }
      if (candidate > 1) {
         if (w > target && w / candidate < target) {
            candidate -= 1
         }
      }
      if (candidate > 1) {
         if (h > target && h / candidate < target) {
            candidate -= 1
         }
      }
      return candidate
   }
}
