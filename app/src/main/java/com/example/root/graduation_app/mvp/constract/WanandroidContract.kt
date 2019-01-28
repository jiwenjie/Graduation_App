package com.example.root.graduation_app.mvp.constract

import com.example.base_library.base_mvp.BaseMvpViewImpl
import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.*
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/22
 *  desc:Wanandroid 的 Contract 类
 *  version:1.0
 */
interface WanandroidContract {

    /**
     * model interface for get data
     */
    interface WanandroidModel {
        /**
         * 获取公众号列表
         */
        fun getPublicAddressList(): Observable<WanAndroidListBean<WanAndroidPublicItemBean>>

        /**
         * 获取某个公众号的历史数据
         */
        fun getOnePublicAddressHistory(
            publicId: Int,
            pageNum: Int
        ): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

        /**
         * search article in public history
         */
        fun searchArticleInPublicAddress(
            publicId: Int,
            pageNum: Int,
            searchKey: String
        ): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

        /**
         * get the latest project
         */
        fun getProjectList(page: Int): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>

        /**
         * get hot word
         */
        fun getHotWord(): Observable<WanAndroidListBean<WanandroidHotkeyword>>

        fun getHotSearchData(): Observable<WanAndroidListBean<WanandroidHotkeyword>>

        /**
         * get search result
         */
        fun getSearchResult(page: Int, key: String): Observable<WanAndroidBaseBean<WanAndroidJson<WanAndroidItem>>>
    }


    /**
     * get public num view
     */
    interface WanandroidPublicView : BaseMvpViewImpl {
        /**
         * first get the public num data
         */
        fun displayPublicNumList(publicBeanList: ArrayList<WanAndroidPublicItemBean>)
    }

    /**
     * get public data view
     */
    interface WanandroidDataView : IBaseView {
        /**
         * display history data
         */
        fun displayPublicHistory(historyList: ArrayList<WanAndroidItem>)
    }

    /**
     * display the latest project view
     */
    interface WanandroidProjectView : IBaseView {
        /**
         * display the latest project
         */
        fun displayLatestProject(projectList: ArrayList<WanAndroidItem>)
    }

    /**
     * display hot word in searchActivity
     */
    interface WanandroidSearchView : IBaseView {
        fun displayHotword(hotwordList: ArrayList<WanandroidHotkeyword>)

        /**
         * search article from history
         */
        fun displaySearchResult(searchResultList: ArrayList<WanAndroidItem>)
    }

    /**
     * presenter
     */
    interface WanandroidPublicPresenter {
        /**
         * get public list
         */
        fun getPublicAddressList()
    }

    /**
     * presenter
     */
    interface WanandroidDataPresenter {
        /**
         * get public history data
         */
        fun getOnePublicAddressHistory(publicId: Int, pageNum: Int)
    }

    /**
     * display project activity presenter
     */
    interface WanandroidProjectPresenter {
        /**
         * get the latest project
         */
        fun getProjectList(page: Int)
    }

    /**
     * search activity presenter
     */
    interface WanandroidSearchPresenter {
        /**
         * get hot word
         */
        fun getHotword()

        /**
         * search article in public history, search in one publicNo
         */
        fun searchArticleInPublicAddress(publicId: Int, pageNum: Int, searchKey: String)

        /**
         * search article all
         */
        fun searchArticleAll(page: Int, key: String)
    }
}