package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.GankioApi
import com.example.root.graduation_app.bean.GankIoListBean
import com.example.root.graduation_app.mvp.constract.GankIoContract
import com.example.root.graduation_app.utils.Constants
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable
import java.io.File

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/17
 *  desc:
 *  version:1.0
 */
class GankIoModel : GankIoContract.GankIoModel {

    override fun getGankIoDayList(year: String, month: String, day: String) {

    }

    override fun getGankIoDayMobile(type: String, prePage: Int, page: Int): Observable<GankIoListBean> {
        return RetrofitManager.provideClient(Constants.GANKID_BASE_URL)
            .create(GankioApi::class.java)
            .getGankIoCustomList(type, prePage, page)
            .compose(RxJavaUtils.applyObservableAsync())
    }
}