package com.example.root.graduation_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.KnowledgeTreeBody
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean
import kotlinx.android.synthetic.main.item_knowledge_tree_list.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
class KnowledgeTreeAdapter(context: Context, beanList: ArrayList<KnowledgeTreeBody>)
   : BaseRecyclerAdapter<KnowledgeTreeBody>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.item_knowledge_tree_list


   @SuppressLint("SetTextI18n")
   override fun convertView(itemView: View, data: KnowledgeTreeBody, position: Int) {
      itemView.title_first.text = data.name
      data.children.let {
         itemView.title_second.text = "    " + Html.fromHtml(data.name)
      }
   }
}












