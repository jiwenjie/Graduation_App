package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.GankIoListBean
import com.example.root.graduation_app.bean.GankItemBean
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/16
 *  desc:干货集中营的契约类
 *  version:1.0
 */
interface GankIoContract {

    /**
     * model 的 interface
     */
    interface GankIoModel {
        /**
         * 获取每日数据 list
         */
        fun getGankIoDayList(year: String, month: String, day: String)

        /**
         * 获取指定的 Android ios 数据
         */
        fun getGankIoDayMobile(type: String, prePage: Int, page: Int): Observable<GankIoListBean>

        /**
         * 获取指定的 数据
         */
//        fun getGankIoIOS(type: String, prePage: Int, page: Int): Observable<GankIoListBean>
    }

    /**
     * view 的 接口
     */
    interface GankIoView : IBaseView {
        fun updateViewList(beanListL: ArrayList<GankItemBean>)
    }

    /**
     * presenter 的 interface
     */
    interface GankIoPresenter {
        fun getGankIoDayMobile(type: String, prePage: Int, page: Int)
    }
}