package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WeixinChoiceItemBean
import kotlinx.android.synthetic.main.fragment_home_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/09
 *  desc:
 *  version:1.0
 */
class WeixinAdapter(context: Context, beanList: ArrayList<WeixinChoiceItemBean>)
         : BaseRecyclerAdapter<WeixinChoiceItemBean>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.fragment_home_item

   override fun convertView(itemView: View, data: WeixinChoiceItemBean, position: Int) {
      itemView.tv_item_title.setTextColor(Color.BLACK)

      itemView.tv_item_title.text = data.title
      itemView.tv_item_who.text = data.source
      itemView.iv_item_image.visibility = View.INVISIBLE
   }
}