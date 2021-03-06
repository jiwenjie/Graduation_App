package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.ZhihuDailyItemBean
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.RequestOptions
import kotlinx.android.synthetic.main.fragment_home_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/07
 *  desc:知乎首页的适配器
 *  version:1.0
 */
@Suppress("PLUGIN_WARNING")
class ZhihuAdapter(context: Context, beanList: ArrayList<ZhihuDailyItemBean>)
   : BaseRecyclerAdapter<ZhihuDailyItemBean>(context, beanList) {

   override fun getAdapterLayoutId(viewType: Int): Int = R.layout.fragment_home_item

   override fun convertView(itemView: View, data: ZhihuDailyItemBean, position: Int) {
      itemView.tv_item_title.text = data.title

      CommonUtils.displayImg(mContext, data.images[0], itemView.iv_item_image)
   }
}



















