package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.TodoBean
import kotlinx.android.synthetic.main.fragment_todo_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/05
 *  desc:
 *  version:1.0
 */
class TaskListAdapter(context: Context, beanList: ArrayList<TodoBean>)
   : BaseRecyclerAdapter<TodoBean>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.fragment_todo_item

   override fun convertView(itemView: View, data: TodoBean, position: Int) {
      itemView.fragment_todo_item_title.text = data.title
      itemView.fragment_todo_item_time.text = data.createtime
      if (data.complete) {
         itemView.fragment_todo_item_complete.text = "已完成"
         itemView.fragment_todo_item_complete.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
      } else {
         itemView.fragment_todo_item_complete.text = "未完成"
         itemView.fragment_todo_item_complete.setTextColor(ContextCompat.getColor(mContext, R.color.colorRateRed))
      }
   }
}