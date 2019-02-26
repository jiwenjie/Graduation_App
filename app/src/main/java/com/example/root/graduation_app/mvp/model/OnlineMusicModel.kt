package com.example.root.graduation_app.mvp.model

import com.example.base_library.RetrofitManager
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.BaseJacksonList
import com.example.root.graduation_app.bean.Song
import com.example.root.graduation_app.mvp.constract.OnlineMusicContract
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.RxJavaUtils
import io.reactivex.Observable

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/22
 *  desc:
 *  version:1.0
 */
class OnlineMusicModel: OnlineMusicContract.Model {

    override fun getOnlineMusicList(page: Int, limit: Int): Observable<BaseJacksonList<Song>> {
        return RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
            .create(JacksonApi::class.java)
            .getOnlineMusicList(page, limit)
            .compose(RxJavaUtils.applyObservableAsync())
    }
}