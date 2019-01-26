package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
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
      itemView.tv_movie_directors.text = CommonUtils.splicingAuthor(data.directors)
      itemView.tv_movie_actors.text = CommonUtils.splicingAuthor(data.casts)
      itemView.tv_movie_genres.text = CommonUtils.splicingString(data.genres)
      itemView.tv_movie_rating_rate.text = "${data.rating.average}"

      CommonUtils.displayImg(mContext, data.images.large, itemView.iv_moive_photo)
   }
}










