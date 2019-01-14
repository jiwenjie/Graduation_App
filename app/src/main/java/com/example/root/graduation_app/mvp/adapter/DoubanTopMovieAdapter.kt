package com.example.root.graduation_app.mvp.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanSubjectBean
import com.example.root.graduation_app.utils.RequestOptions
import kotlinx.android.synthetic.main.activity_douban_movie_top.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/12
 *  desc:douban top 250 movie
 *  version:1.0
 */
class DoubanTopMovieAdapter(context: Context, beanList: ArrayList<DoubanSubjectBean>)
   : BaseRecyclerAdapter<DoubanSubjectBean>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.activity_douban_movie_top

   override fun convertView(itemView: View, data: DoubanSubjectBean, position: Int) {
      itemView.tv_top_moive_name.text = data.title
      itemView.tv_top_moive_rate.text = "${data.rating.average}"
      Glide.with(mContext)
              .load(data.images.large)
              .apply(RequestOptions.getRequestOptions())
              .transition(DrawableTransitionOptions.withCrossFade())
              .thumbnail(0.8f)
              .into(itemView.iv_top_moive_photo)
   }
}














