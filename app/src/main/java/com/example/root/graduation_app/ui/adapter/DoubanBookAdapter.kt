package com.example.root.graduation_app.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.R.id.imageView
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import com.example.root.graduation_app.ui.activity.DoubanBookDetailActivity
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
      itemView.tv_item_author.text = "作者：${CommonUtils.splicingString(data.author)}"
      itemView.tv_item_publisher.text = "出版社：${data.publisher}"
      itemView.tv_item_pubdate.text = "出版日期：${data.pubdate}"
      itemView.tv_item_rate.text = "评分：${data.rating.average}"

      CommonUtils.displayImg(mContext, data.image, itemView.iv_item_image)

      itemView.iv_item_image.setOnClickListener {
         // 共享动画接收部分
         if (listener != null) {
            listener?.onItemClick(data, itemView.iv_item_image)
         }
//         val intent = Intent(mContext, DoubanBookDetailActivity::class.java)
//         val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(mContext as Activity, itemView.iv_item_image, "ShareElement")
//         mContext.startActivity(intent, compat.toBundle())
      }
   }

   interface OnShareElementInterface {
      fun onItemClick(data: DoubanBookItemDetail, imageView: ImageView)
   }

   private var listener: OnShareElementInterface? = null

   fun setOnShareElementClickListener(listener: OnShareElementInterface) {
      this.listener = listener
   }
}













