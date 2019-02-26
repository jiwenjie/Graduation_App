package com.example.root.graduation_app.ui.fragment

import android.os.Bundle
import com.example.base_library.base_mvp.BaseMvpFragment
import com.example.base_library.base_views.BaseFragment
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.Song
import com.example.root.graduation_app.mvp.constract.OnlineMusicContract
import com.example.root.graduation_app.mvp.presenter.OnlinePresenter

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/21
 *  desc:
 *  version:1.0
 */
class OnlineMusicFragment :
   BaseMvpFragment<OnlineMusicContract.View, OnlinePresenter>(), OnlineMusicContract.View {

   companion object {
      @JvmStatic
      fun newInstance(): OnlineMusicFragment {
         return OnlineMusicFragment().apply {
            val bundle = arguments.run {

            }
         }
      }
   }

   override fun initFragment(savedInstanceState: Bundle?) {

   }

   override fun showOnlineMusic(beanList: ArrayList<Song>) {

   }

   override fun loadData() {

   }

   override fun showLoading() {

   }

   override fun dismissLoading() {

   }

   override fun showError(errorMsg: String, errorCode: Int) {

   }

   override fun initPresenter(): OnlinePresenter = OnlinePresenter(this)

   override fun getLayoutId(): Int = R.layout.common_multiple_recyclerview
}