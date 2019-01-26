package com.example.root.graduation_app.ui.adapter

import android.content.Context
import android.view.View
import com.example.base_library.base_adapters.BaseRecyclerAdapter
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.WanandroidHotkeyword
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.activity_flow_text_item.view.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/24
 *  desc:
 *  version:1.0
 */
class HotkeywordAdapter(mContext: Context, mList: ArrayList<WanandroidHotkeyword>)
    : BaseRecyclerAdapter<WanandroidHotkeyword>(mContext, mList) {

    /**
     * Kotlin 的函数可以作为参数，这样写 callback 的时候就可以不用 interface 了
     * 而且这样的写法可以更好的使用 lambda 方式
     */
    private var mOnTagItemClickListener: ((tag: String) -> Unit)? = null

    fun setOnTagItemClickListener(onTagItemClickListener: (tag: String) -> Unit) {
        this.mOnTagItemClickListener = onTagItemClickListener
    }


    override fun getAdapterLayoutId(viewType: Int): Int = R.layout.activity_flow_text_item

    override fun convertView(itemView: View, data: WanandroidHotkeyword, position: Int) {
        itemView.tv_title.text = data.name
        LogUtils.e("SSS" + data.name)
        val params = itemView.tv_title.layoutParams
        if (params is FlexboxLayoutManager.LayoutParams) params.flexGrow = 1.0f

        itemView.setOnClickListener {
            mOnTagItemClickListener?.invoke(data.name)
        }
    }
}
