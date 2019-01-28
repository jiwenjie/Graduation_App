package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.WanandroidApi
import com.example.root.graduation_app.bean.KnowledgeTreeBody
import com.example.root.graduation_app.bean.WanAndroidListBean
import com.example.root.graduation_app.mvp.constract.KnowledgeTreeContract
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/26
 *  desc:
 *  version:1.0
 */
class KnowledgeTreeModel : KnowledgeTreeContract.Model {

    override fun requestKnowledgeTree(): Observable<WanAndroidListBean<KnowledgeTreeBody>> {
        return RetrofitManager.provideClient(ConstantConfig.WANWANDROID_URL)
            .create(WanandroidApi::class.java)
            .getKnowledgeTree()
            .compose(RxJavaUtils.applyObservableAsync())
    }
}








