package com.example.root.graduation_app.ui.adapter

import android.app.Activity
import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanAndroidItem
import com.example.root.graduation_app.ui.activity.LoginActivity
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.PhoneUserUtils
import kotlinx.android.synthetic.main.item_knowledge_list.view.*
import kotlinx.android.synthetic.main.item_project_list.view.*

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
         val user = App.getLoginUser()
         if (user == null) {
            // 说明此时还没登录
            ToastUtils.showToast(mContext, "请先登录")
            LoginActivity.runActivity(mContext as Activity, null)
         } else {
            if (data.collect) {  // 文章已被收藏（这里注意因为只在前台改了数据没有更改后台数据，所以刷新之后就会重置）
               // 说明此时文章已被收藏，所以点击的时候应该是取消收藏
               PhoneUserUtils.cancelCollect(user.userid!!, data.id, object : PhoneUserUtils.collectArticleInterface {
                  override fun success(msg: String) {
                     ToastUtils.showToast(mContext, "取消收藏成功")
                     data.collect = !data.collect
                     itemView.iv_like.setImageResource(R.drawable.ic_like)
                  }

                  override fun failed(error: String) {
                     LogUtils.e(error)
                     ToastUtils.showToast(mContext, "操作失败")
                  }
               })
            } else {
               // 说明此时文章还没被收藏或者已经取消了收藏
               PhoneUserUtils.collectArticle(user.userid!!, data, object : PhoneUserUtils.collectArticleInterface {
                  override fun success(msg: String) {
                     ToastUtils.showToast(mContext, "收藏成功")
                     data.collect = !data.collect
                     itemView.iv_like.setImageResource(R.drawable.ic_like)
                  }

                  override fun failed(error: String) {
                     LogUtils.e(error)
                     ToastUtils.showToast(mContext, "操作失败")
                  }
               })
            }
         }
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










