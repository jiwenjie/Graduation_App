package com.example.root.graduation_app.mvp.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.example.base_library.base_mvp.BaseMvpActivity
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ScreenUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.root.graduation_app.R
import com.example.root.graduation_app.bean.DoubanCastsBean
import com.example.root.graduation_app.bean.DoubanMovieDetail
import com.example.root.graduation_app.bean.DoubanSubjectBean
import com.example.root.graduation_app.mvp.adapter.DoubanMovieDetailAdapter
import com.example.root.graduation_app.mvp.constract.DoubanContract
import com.example.root.graduation_app.mvp.presenter.DoubanMoviePresenter
import com.example.root.graduation_app.utils.CommonUtils
import com.example.root.graduation_app.utils.RequestOptions
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.activity_movie_detail_header.*
import kotlinx.android.synthetic.main.activity_movie_detail_mid.*
import kotlinx.android.synthetic.main.common_toolbar_layout_return.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/14
 *  desc: movie detail activity
 *  version:1.0
 */
class DoubanMovieDetailActivity : BaseMvpActivity<DoubanContract.DoubanMovieView, DoubanMoviePresenter>(), DoubanContract.DoubanMovieView {

    private var bean: DoubanSubjectBean? = null

    private val beanList by lazy { ArrayList<DoubanCastsBean>() }
    private val adapter by lazy { DoubanMovieDetailAdapter(this@DoubanMovieDetailActivity, beanList) }

    companion object {
        private const val KEY_BEAN = "key_bean"

        fun runActivity(activity: Activity, subjectBean: DoubanSubjectBean) {
            val intent = Intent(activity, DoubanMovieDetailActivity::class.java)
            intent.putExtra(KEY_BEAN, subjectBean)
            activity.startActivity(intent)
        }
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        bean = intent.getSerializableExtra(KEY_BEAN) as DoubanSubjectBean
        initHeaderView()
        initView()
    }

    private fun initHeaderView() {
        common_toolbar_title.visibility = View.VISIBLE
        common_toolbar_title.text = bean?.title
        tv_movie_rating_number.text = "${bean!!.rating.average}"
        tv_collect_count.text = "${bean?.collect_count}"
        tv_movie_directors.text = CommonUtils.splicingAuthor(bean!!.directors)
        tv_movie_casts.text = CommonUtils.splicingAuthor(bean!!.casts)
        tv_movie_genres.text = CommonUtils.splicingString(bean!!.genres)
        tv_movie_date.text = bean?.year
        Glide.with(this@DoubanMovieDetailActivity)
            .asBitmap()
            .load(bean?.images?.large)
            .apply(RequestOptions.getRequestOptions())
            .into(iv_movie_photo)

        CommonUtils.displayBlurImg(this@DoubanMovieDetailActivity, bean!!.images.large, iv_header_bg)     // 显示虚化图片
        CommonUtils.displayBlurImg(this@DoubanMovieDetailActivity, bean!!.images.large, iv_toolbar_bg)    // 显示虚化图片
    }

    private fun initView() {
        rv_movie_detail.layoutManager = LinearLayoutManager(this@DoubanMovieDetailActivity)
        rv_movie_detail.adapter = adapter
        rv_movie_detail.isNestedScrollingEnabled = false
        nsv_scrollview.setOnScrollChangeListener { nestedScrollView: NestedScrollView?, l: Int, t: Int, oldl: Int, oldt: Int ->
            var alpha = 1f
            var slideValue = t - ScreenUtils.dip2px(this@DoubanMovieDetailActivity, 56f) + ScreenUtils.getStatusBarHeight(this@DoubanMovieDetailActivity)
            if (slideValue < 0) {
                slideValue = 0
            }

            var fraction = slideValue / (common_toolbar.height / 2f)
            if (fraction > 1) {
                fraction = 1f
            }
            alpha *= fraction
            toolbg.alpha = alpha
        }
    }

    override fun loadData() {
        mPresenter.getDoubanMovieDetail(bean!!.id)
    }

    override fun initPresenter(): DoubanMoviePresenter = DoubanMoviePresenter(this@DoubanMovieDetailActivity)

    override fun updateDoubanContentList(subjectList: ArrayList<DoubanSubjectBean>) {

    }

    @SuppressLint("SetTextI18n")
    override fun showDetail(bean: DoubanMovieDetail) {
        adapter.addAllData(bean.casts)
        tv_movie_city.text = "制片国家/地区：" + CommonUtils.splicingString(bean.countries)
        tv_movie_sub_title.text = CommonUtils.splicingString(bean.aka)
        tv_moive_summary.text = bean.summary
    }

    override fun showLoading() {
        showProgress(null)
    }

    override fun dismissLoading() {
        dismissProgress()
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        ToastUtils.showToast(this@DoubanMovieDetailActivity, "$errorCode" + errorMsg)
        LogUtils.e("$errorCode" + errorMsg)
    }

    override fun getLayoutId(): Int = R.layout.activity_movie_detail
}














