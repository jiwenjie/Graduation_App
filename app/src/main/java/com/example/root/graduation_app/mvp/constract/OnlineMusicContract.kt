package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.BaseJacksonList
import com.example.root.graduation_app.bean.Song
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/22
 *  desc:获取在线音乐接口的契约类
 *  version:1.0
 */
interface OnlineMusicContract {

    /**
     * 获取音乐的 model 接口
     */
    interface Model {
        fun getOnlineMusicList(page: Int, limit: Int): Observable<BaseJacksonList<Song>>
    }

    /**
     * 获取音乐的 view
     */
    interface View: IBaseView {
        fun showOnlineMusic(beanList: ArrayList<Song>)
    }

    /**
     * 获取音乐的 presenter
     */
    interface Presenter {
        fun getOnlineMusic(page: Int, limit: Int)
    }
}