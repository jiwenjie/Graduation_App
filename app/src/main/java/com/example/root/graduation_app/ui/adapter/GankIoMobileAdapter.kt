package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.GankItemBean
import com.example.root.graduation_app.utils.CommonUtils
import kotlinx.android.synthetic.main.fragment_gank_io_bottom.view.*
import kotlinx.android.synthetic.main.fragment_gank_io_normal_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/17
 *  desc:
 *  version:1.0
 */
class GankIoMobileAdapter(context: Context, beanList: ArrayList<GankItemBean>, normalLayout: Boolean = true)
   : BaseRecyclerAdapter<GankItemBean>(context, beanList) {

   private val showNormal = normalLayout  // true 显示 normal 布局，否则显示无图片布局

   private val mImageSize = "?imageView2/0/w/200"

   override fun getAdapterLayoutId(viewType: Int): Int {
      return when (viewType) {
         1 -> {
            R.layout.fragment_gank_io_normal_item
         }
         2 -> {
            R.layout.fragment_gank_io_no_image_item
         }
         else -> {
            R.layout.fragment_gank_io_normal_item
         }
      }
   }

   override fun convertView(itemView: View, data: GankItemBean, position: Int) {
      initTypeImage(itemView, data)

      itemView.tv_item_who.text = data.who   // 可能为空
      /**
       * helper.setText(R.id.tv_item_who, StringUtils.isEmpty(item.getWho()) ? "佚名" : item
      .getWho());
       */
      itemView.tv_item_type.text = data.type
      itemView.tv_item_time.text = data.createdAt.substring(0, 10)

      when (getItemViewType(position)) {
         1 -> {
            itemView.tv_item_title.text = data.desc
            if (!data.images.isEmpty()) {
               if (data.images.size > 0 && !TextUtils.isEmpty(data.images[0])) {
                  CommonUtils.displayImgAsBitmap(mContext, data.images[0] + mImageSize, itemView.iv_item_image)
               }
            }
         }
         2 -> {
            itemView.tv_item_title.text = data.desc
         }
      }
   }

   override fun getItemViewType(position: Int): Int {
      if (showNormal) {
         return 1
      }
      return 2
   }

   private fun initTypeImage(itemView: View, data: GankItemBean) {
      when (data.type) {
         "Android" -> {
            itemView.iv_type_item_title.setImageResource(R.drawable.ic_vector_title_android)
         }
         "iOS" -> {
            itemView.iv_type_item_title.setImageResource(R.drawable.ic_vector_title_ios)
         }
         "前端" -> {
            itemView.iv_type_item_title.setImageResource(R.drawable.ic_vector_title_front)
         }
         "休息视频" -> {
            itemView.iv_type_item_title.setImageResource(R.drawable.ic_vector_title_video)
         }
         "瞎推荐" -> {
            itemView.iv_type_item_title.setImageResource(R.drawable.ic_vector_item_tuijian)
         }
         "拓展资源" -> {
            itemView.iv_type_item_title.setImageResource(R.drawable.ic_vector_item_tuozhan)
         }
         "App" -> {
            itemView.iv_type_item_title.setImageResource(R.drawable.ic_vector_item_app)
         }
         else -> {

         }
      }
   }
}