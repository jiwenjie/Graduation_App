package com.example.root.graduation_app.mvp.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WangyiNewsItemBean
import com.example.root.graduation_app.utils.RequestOptions
import kotlinx.android.synthetic.main.fragment_home_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/08
 *  desc:
 *  version:1.0
 */
class WangyiAdapter(context: Context, beanList: ArrayList<WangyiNewsItemBean>)
            : BaseRecyclerAdapter<WangyiNewsItemBean>(context, beanList) {

    override fun getAdapterLayoutId(viewType: Int): Int = R.layout.fragment_home_item

    override fun convertView(itemView: View, data: WangyiNewsItemBean, position: Int) {
        itemView.tv_item_title.setTextColor(Color.BLACK)

        itemView.tv_item_title.text = data.title
        itemView.tv_item_who.text = data.source
        itemView.tv_item_time.text = data.ptime
        Glide.with(mContext)
            .load(data.imgsrc)
            .apply(RequestOptions.getRequestOptions())
            .transition(DrawableTransitionOptions().crossFade())
            .into(itemView.iv_item_image)
    }
}