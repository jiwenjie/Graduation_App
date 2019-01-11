package com.example.root.graduation_app.mvp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.RequestOptions
import kotlinx.android.synthetic.main.fragment_douban_book_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/10
 *  desc:豆瓣图书的适配器
 *  version:1.0
 */
class DoubanBookAdapter(context: Context, beanList: ArrayList<DoubanBookItemDetail>)
   : BaseRecyclerAdapter<DoubanBookItemDetail>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.fragment_douban_book_item

   @SuppressLint("SetTextI18n")
   override fun convertView(itemView: View, data: DoubanBookItemDetail, position: Int) {
      LogUtils.e(data.title + data.publisher)
      itemView.tv_item_title.text = "《" + data.title + "》"
      itemView.tv_item_author.text = "作者：${CommonUtils.splicing(data.author)}"
      itemView.tv_item_publisher.text = "出版社：${data.publisher}"
      itemView.tv_item_pubdate.text = "出版日期：${data.pubdate}"
      itemView.tv_item_rate.text = "评分：${data.rating.average}"
      Glide.with(mContext)
              .load(data.image)
              .apply(RequestOptions.getRequestOptions())
              .transition(DrawableTransitionOptions().crossFade(300))
              .thumbnail(0.5f)   // 缩略图
              .into(itemView.iv_item_image)
   }
}













