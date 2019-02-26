package com.example.root.graduation_app.bean

import java.io.Serializable

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2019/02/20
 * desc: 歌手的 bean
 * version:1.0
 */
class Song : Serializable {
   /**
    * 歌手
    */
   var singer: String? = null
   /**
    * 歌曲名
    */
   var song: String? = null
   /**
    * 歌曲的地址
    */
   var path: String? = null
   /**
    * 歌曲长度
    */
   var duration: Int = 0
   /**
    * 歌曲的大小
    */
   var size: Long = 0

   var name: String? = null
   var albumId: Long = 0
   var id: Long? = 0
}
