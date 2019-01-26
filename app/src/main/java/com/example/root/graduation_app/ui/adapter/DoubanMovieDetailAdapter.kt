package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.graphics.drawable.TransitionDrawable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanCastsBean
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_detail_person_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/14
 *  desc:豆瓣 movie 的 detail 适配器
 *  version:1.0
 */
class DoubanMovieDetailAdapter(context: Context, beanList: ArrayList<DoubanCastsBean>) :
        BaseRecyclerAdapter<DoubanCastsBean>(context, beanList) {

    override fun getAdapterLayoutId(viewType: Int): Int = R.layout.activity_movie_detail_person_item

    override fun convertView(itemView: View, data: DoubanCastsBean, position: Int) {
        itemView.tv_person_name.text = data.name
        itemView.tv_person_type.text = data.type

        if (data.avatars.large.isEmpty()) return

        CommonUtils.displayImg(mContext, data.avatars.large, itemView.iv_avatar_photo)
    }
}


