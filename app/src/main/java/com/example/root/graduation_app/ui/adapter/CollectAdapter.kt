@file:Suppress("DEPRECATION")

package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.TextUtils
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.PhoneUserUtils
import kotlinx.android.synthetic.main.item_knowledge_list.view.*
import kotlinx.android.synthetic.main.item_project_list.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/17
 *  desc:
 *  version:1.0
 */
class CollectAdapter(context: Context, beanList: ArrayList<WanAndroidItem>)
   :  BaseRecyclerAdapter<WanAndroidItem>(context, beanList) {

   private var list:ArrayList<WanAndroidItem>? = null

   init {
      this.list = beanList
   }

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.item_knowledge_list

   override fun convertView(itemView: View, data: WanAndroidItem, position: Int) {
      itemView.tv_article_title.text = Html.fromHtml(data.title)
      itemView.tv_article_author.text = data.author
      itemView.tv_article_date.text = data.niceDate
      itemView.iv_like.setImageResource(R.drawable.ic_like)
      itemView.iv_like.setOnClickListener {
         // 先弹出对话框二次校验
         cancelCollect(itemView, data)
      }
//      val chapterName = when {
//         data.superChapterName.isNotEmpty() and data.chapterName.isNotEmpty() ->
//            "${data.superChapterName} / ${data.chapterName}"
//         data.superChapterName.isNotEmpty() -> data.superChapterName
//         data.chapterName.isNotEmpty() -> data.chapterName
//         else -> ""
//      }
//      itemView.tv_article_chapterName.text = chapterName
      if (!TextUtils.isEmpty(data.envelopePic)) {
         itemView.iv_article_thumbnail.visibility = View.VISIBLE
         CommonUtils.displayImg(mContext, data.envelopePic, itemView.iv_article_thumbnail)
      } else {
         itemView.iv_article_thumbnail.visibility = View.GONE
      }
   }

   private fun cancelCollect(itemView: View, data: WanAndroidItem) {
      AlertDialog.Builder(mContext)
              .setTitle("提示")
              .setMessage("确认取消收藏吗？")
              .setNegativeButton("取消", null)
              .setPositiveButton("确认") { dialog, which ->
                 PhoneUserUtils.cancelCollect(App.getLoginUser()?.userid!!, data.id, object : PhoneUserUtils.collectArticleInterface {
                    override fun success(msg: String) {
                       ToastUtils.showToast(mContext, "操作成功")
                       data.collect = !data.collect
                       itemView.iv_like.setImageResource(R.drawable.ic_like_not)
                       list?.remove(data) // 从列表中移除该项
                       notifyDataSetChanged()
                    }

                    override fun failed(error: String) {
                       LogUtils.e("CollectAdapter$error")
                       ToastUtils.showToast(mContext, "操作失败")
                    }
                 })
              }
              .show()
   }
}
