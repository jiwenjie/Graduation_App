package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.base.api.WangyiApi
import com.example.root.graduation_app.bean.WangyiNewsListBean
import com.example.root.graduation_app.mvp.constract.WangyiContract
import com.example.root.graduation_app.utils.Constants
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/08
 *  desc:
 *  version:1.0
 */
class WangyiModel : WangyiContract.WangyiModel {

    override fun getNewsList(id: Int): Observable<WangyiNewsListBean> {
//        RetrofitManager.setBaseUrl(Constants.WANGYI_BASE_URL)
//        LogUtils.e(RetrofitManager.mRetrofit.baseUrl())
        return RetrofitManager.provideClient(Constants.WANGYI_BASE_URL).create(WangyiApi::class.java)
            .getNewsList(id)
            .compose(RxJavaUtils.applyObservableAsync())
    }
}