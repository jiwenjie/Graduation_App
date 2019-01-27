package com.example.root.graduation_app.mvp.constract

import com.example.root.graduation_app.base.IBaseView
import com.example.root.graduation_app.bean.KnowledgeTreeBody
import com.example.root.graduation_app.bean.WanAndroidListBean
import com.example.root.graduation_app.bean.WanAndroidPublicItemBean
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
interface KnowledgeTreeContract {

    interface Model {
        fun requestKnowledgeTree(): Observable<WanAndroidListBean<KnowledgeTreeBody>>
    }

    interface View : IBaseView {
        fun scrollToTop()

        fun setKnowledgeTree(lists: ArrayList<KnowledgeTreeBody>)
    }

    interface Presenter {
        fun requestKnowledgeTree()
    }
}