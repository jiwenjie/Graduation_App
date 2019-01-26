package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.text.Html
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.utils.CommonUtils
import kotlinx.android.synthetic.main.item_project_list.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/25
 *  desc:
 *  version:1.0
 */
@Suppress("DEPRECATION")
class ProjectAdapter(context: Context, beanList: ArrayList<WanAndroidItem>)
   : BaseRecyclerAdapter<WanAndroidItem>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.item_project_list

   override fun convertView(itemView: View, data: WanAndroidItem, position: Int) {
      itemView.item_project_list_title_tv.text = Html.fromHtml(data.title)
      itemView.item_project_list_content_tv.text = Html.fromHtml(data.desc)
      itemView.item_project_list_time_tv.text = data.niceDate
      itemView.item_project_list_author_tv.text = data.author
      itemView.item_project_list_like_iv.setImageResource(if (data.collect) R.drawable.ic_like else R.drawable.ic_like_not)

      CommonUtils.displayImg(mContext, data.envelopePic, itemView.item_project_list_iv)
   }
}















