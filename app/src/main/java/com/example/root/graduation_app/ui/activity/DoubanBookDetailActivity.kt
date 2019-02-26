package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.example.base_library.base_mvp.BaseMvpActivity
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ScreenUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanBookItemDetail
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.presenter.DoubanBookDetailPresenter
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.RequestOptions
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_douban_book_detail.*
import kotlinx.android.synthetic.main.activity_douban_book_detail_header.*
import kotlinx.android.synthetic.main.activity_douban_book_detail_mid.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/15
 *  desc:
 *  version:1.0
 */
class DoubanBookDetailActivity : BaseMvpActivity<DoubanContract.DoubanBookDetailView, DoubanBookDetailPresenter>(),
        DoubanContract.DoubanBookDetailView {

   private var bean: DoubanBookItemDetail? = null

   companion object {
      const val KEY_BEAN = "key_bean"

      fun runActivity(activity: Activity, bean: DoubanBookItemDetail) {
         val intent = Intent(activity, DoubanBookDetailActivity::class.java)
         intent.putExtra(KEY_BEAN, bean)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      bean = intent.getSerializableExtra(KEY_BEAN) as DoubanBookItemDetail
      initHeaderView()
      initView()
   }

   private fun initHeaderView() {
      // 共享动画接收部分
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         iv_book_photo.transitionName = "ShareElement"
      }

      tv_book_rating_number.text = bean!!.rating.average
      tv_collect_count.text = "${bean!!.rating.numRaters}"
      tv_book_author.text = CommonUtils.splicingString(bean!!.author)
      tv_book_publisher.text = bean!!.publisher
      tv_book_pupdate.text = bean!!.pubdate
      if (bean?.subtitle.isNullOrEmpty()) {
         ll_book_sub_title.visibility = View.GONE
      } else {
         ll_book_sub_title.visibility = View.VISIBLE
         tv_book_sub_title.text = bean?.subtitle
      }
      tv_book_author_intro.text = bean?.author_intro
      tv_book_summary.text = bean?.summary
      Glide.with(this@DoubanBookDetailActivity)
              .asBitmap()
              .load(bean!!.images.large)
              .apply(RequestOptions.getRequestOptions())
              .transition(BitmapTransitionOptions().crossFade(300))
              .thumbnail(0.5f)
              .into(iv_book_photo)
      CommonUtils.displayBlurImg(this@DoubanBookDetailActivity, bean!!.images.large, iv_header_bg)
      CommonUtils.displayBlurImg(this@DoubanBookDetailActivity, bean!!.images.large, iv_toolbar_bg)

      val headerBgHeight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
         toolbar.layoutParams.height + ScreenUtils.getStatusBarHeight(this@DoubanBookDetailActivity)
      } else {
         toolbar.layoutParams.height
      }
      // 使得背景图片上移动到图片的最低端，保留（toolbar + 状态栏）的高度
      // 实际上此时的 ivToolbarBg 高度还是 330dp，只是除了 toolbar 之外。剩下部分是透明状态
      val ivTitleParams = iv_toolbar_bg.layoutParams as ViewGroup.MarginLayoutParams
      val marginTop = iv_toolbar_bg.layoutParams.height - headerBgHeight
      ivTitleParams.setMargins(0, -marginTop, 0, 0)
   }

   private fun initView() {
      nsv_scrollview.setOnScrollChangeListener { nestedScrollView: NestedScrollView?, l: Int, t: Int, oldl: Int, oldt: Int ->
         var alpha = 1f
         var slideValue = t - ScreenUtils.dip2px(this@DoubanBookDetailActivity, 56f) + ScreenUtils.getStatusBarHeight(this@DoubanBookDetailActivity)
         if (slideValue < 0) {
            slideValue = 0
         }

         var fraction = slideValue / (toolbar.height / 2f)
         if (fraction > 1) {
            fraction = 1f
         }
         alpha *= fraction
         iv_toolbar_bg.alpha = alpha
      }
   }

   override fun loadData() {
      mPresenter.getBookDetail(bean!!.id)
   }

   override fun initPresenter(): DoubanBookDetailPresenter = DoubanBookDetailPresenter(this@DoubanBookDetailActivity)

   override fun getBookDetail(bean: DoubanBookItemDetail) {
      if (bean.subtitle.isEmpty()) {
         ll_book_sub_title.visibility = View.GONE
      } else {
         tv_book_sub_title.text = bean.subtitle
      }
      tv_book_author_intro.text = bean.author_intro
      tv_book_summary.text = bean.summary
      tv_book_detail.text = bean.catalog
   }

   override fun showLoading() {
      showProgress(null)
   }

   override fun dismissLoading() {
      dismissProgress()
   }

   override fun showError(errorMsg: String, errorCode: Int) {
      ToastUtils.showToast(this@DoubanBookDetailActivity, "$errorCode" + errorMsg)
      LogUtils.e("$errorCode" + errorMsg)
      nsv_scrollview.visibility = View.GONE
   }

   override fun onBackPressed() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         finishAfterTransition()
      }
      super.onBackPressed()
   }

   override fun getLayoutId(): Int = R.layout.activity_douban_book_detail
}




















