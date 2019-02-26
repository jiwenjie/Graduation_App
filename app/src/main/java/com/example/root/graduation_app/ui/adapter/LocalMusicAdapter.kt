package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.Song
import kotlinx.android.synthetic.main.activity_music_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/21
 *  desc:本地音乐的适配器
 *  version:1.0
 */
class LocalMusicAdapter(context: Context, beanList: ArrayList<Song>)
   : BaseRecyclerAdapter<Song>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.activity_music_item

   override fun convertView(itemView: View, data: Song, position: Int) {
      itemView.activity_music_item_nameText.text = data.name
      itemView.activity_music_item_songNameText.text = data.singer
      itemView.activity_music_item_startMusic.setOnClickListener {
         if (listener != null) {
            // 当前音乐还没开始播放的话传 true，否则 false
            var currPlay: Boolean = itemView.activity_music_item_startMusic.background == ContextCompat.getDrawable(mContext, R.drawable.music_start)
            listener?.onOperationClick(data, currPlay)
         }
      }
   }

   interface StartPlayClickListener {
      fun onOperationClick(song: Song, currPlay: Boolean)
   }

   private var listener: StartPlayClickListener? = null

   fun setOnStartPlayClickListener(listener: StartPlayClickListener) {
      this.listener = listener
   }
}