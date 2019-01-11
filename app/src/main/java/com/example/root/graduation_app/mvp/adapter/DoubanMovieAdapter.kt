package com.example.root.graduation_app.mvp.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanCastsBean
import com.example.root.graduation_app.bean.DoubanSubjectBean
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.RequestOptions
import kotlinx.android.synthetic.main.fragment_douban_movie_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/11
 *  desc:
 *  version:1.0
 */
class DoubanMovieAdapter(context: Context, beanList: ArrayList<DoubanSubjectBean>)
   : BaseRecyclerAdapter<DoubanSubjectBean>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.fragment_douban_movie_item

   override fun convertView(itemView: View, data: DoubanSubjectBean, position: Int) {
      itemView.tv_movie_title.text = data.title
      itemView.tv_movie_directors.text = splicingAuthor(data.directors)
      itemView.tv_movie_actors.text = splicingAuthor(data.casts)
      itemView.tv_movie_genres.text = CommonUtils.splicing(data.genres)
      itemView.tv_movie_rating_rate.text = "${data.rating.average}"
      Glide.with(mContext)
              .load(data.images.large)
              .apply(RequestOptions.getRequestOptions())
              .transition(DrawableTransitionOptions().crossFade(300))
              .thumbnail(0.5f)   // 缩略图
              .into(itemView.iv_moive_photo)
   }

   private fun splicingAuthor(authers: ArrayList<DoubanCastsBean>): String {
      var name = ""
      return when {
         authers.isEmpty() -> name
         authers.size == 1 -> authers[0].name
         else -> {
            name = authers[0].name

            authers.forEach {
               name = " / ${it.name}"
            }
            name
         }
      }
   }

}










