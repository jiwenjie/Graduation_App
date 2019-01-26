package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import kotlinx.android.synthetic.main.fragment_wanandroid_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/22
 *  desc:
 *  version:1.0
 */
class WanandroidTabItemAdapter(context: Context, beanList: ArrayList<WanAndroidItem>)
   : BaseRecyclerAdapter<WanAndroidItem>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.fragment_wanandroid_item

   override fun convertView(itemView: View, data: WanAndroidItem, position: Int) {
      itemView.fragment_wanandroid_item_title.text = data.title
      itemView.fragment_wanandroid_item_author.text = data.author
      itemView.fragment_wanandroid_item_time.text = data.niceDate
      itemView.fragment_wanandroid_item_source.text = data.superChapterName
   }
}