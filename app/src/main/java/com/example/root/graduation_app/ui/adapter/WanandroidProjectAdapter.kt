package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:wanandroid 优秀项目的 adapter
 *  version:1.0
 */
class WanandroidProjectAdapter(context: Context, beanList: ArrayList<WanAndroidItem>)
   : BaseRecyclerAdapter<WanAndroidItem>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.activity_project_list

   override fun convertView(itemView: View, data: WanAndroidItem, position: Int) {

   }
}