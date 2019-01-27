package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.utils.CommonUtils
import kotlinx.android.synthetic.main.item_knowledge_list.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
@Suppress("DEPRECATION")
class KnowledgeAdapter(context: Context, beanList: ArrayList<WanAndroidItem>)
   :  BaseRecyclerAdapter<WanAndroidItem>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.item_knowledge_list

   override fun convertView(itemView: View, data: WanAndroidItem, position: Int) {
      itemView.tv_article_title.text = Html.fromHtml(data.title)
      itemView.tv_article_author.text = data.author
      itemView.tv_article_date.text = data.niceDate
      itemView.iv_like.setImageResource(if (data.collect) R.drawable.ic_like else R.drawable.ic_like_not)
      itemView.iv_like.setOnClickListener {

      }
      val chapterName = when {
         data.superChapterName.isNotEmpty() and data.chapterName.isNotEmpty() ->
            "${data.superChapterName} / ${data.chapterName}"
         data.superChapterName.isNotEmpty() -> data.superChapterName
         data.chapterName.isNotEmpty() -> data.chapterName
         else -> ""
      }
      itemView.tv_article_chapterName.text = chapterName
      if (!TextUtils.isEmpty(data.envelopePic)) {
         itemView.iv_article_thumbnail.visibility = View.VISIBLE
         CommonUtils.displayImg(mContext, data.envelopePic, itemView.iv_article_thumbnail)
      } else {
         itemView.iv_article_thumbnail.visibility = View.GONE
      }
   }
}










