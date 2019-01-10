package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.WangyiNewsItemBean
import com.example.root.graduation_app.bean.WangyiNewsListBean
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/08
 *  desc:
 *  version:1.0
 */
interface WangyiContract {

    /**
     * 网易的 model 接口
     */
    interface WangyiModel {
        /**
         * get news list about wangyi
         */
        fun getNewsList(id: Int): Observable<WangyiNewsListBean>
    }

    /**
     * 网易的 View 接口
     */
    interface WangyiView : IBaseView {
        fun updateContentList(list: ArrayList<WangyiNewsItemBean>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)

        /**
         * 已加载完
         */
        fun showNoData()

    }

    /**
     * 网易的 presenter 接口
     */
    interface WangyiPresenter {
        /**
         * load data
         */
        fun loadLatestList(currentIndex: Int)
    }

}














