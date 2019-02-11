package com.example.root.graduation_app.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_utils.ToastUtils.showToast

import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_common_webview.*

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2019/01/11
 * desc:
 * version:1.0
 */
@Suppress("OverridingDeprecatedMember")
class CommonWebViewActivity : BaseActivity() {

   private var refreshLayout: SwipeRefreshLayout? = null
   private var webView: WebView? = null
   private var titleText: TextView? = null
   private var title: String? = null
   private var shareUrl = ""

   companion object {
      private const val KEY_TITLE = "key_title"
      private const val KEY_URL = "key_url"
      private const val KEY_SHARE = "key_share"

      @JvmStatic
      @JvmOverloads
      fun runActivity(activity: Activity, title: String, url: String, share: Boolean = false) {
         val intent = Intent(activity, CommonWebViewActivity::class.java)
         intent.putExtra(KEY_TITLE, title)
         intent.putExtra(KEY_URL, url)
         intent.putExtra(KEY_SHARE, share)
         activity.startActivity(intent)
      }
   }

   override fun initActivity(savedInstanceState: Bundle?) {
      title = intent.getStringExtra(KEY_TITLE)
      shareUrl = intent.getStringExtra(KEY_URL)
      initView()
      initWebView()
   }

   private fun initView() {
      StatusBarUtil.setColor(this@CommonWebViewActivity,
         ContextCompat.getColor(this@CommonWebViewActivity, R.color.colorPrimary), 0)

      commonWebViewToolbar.apply {
         title = ""
         setSupportActionBar(this)
         supportActionBar?.setDisplayHomeAsUpEnabled(true)
      }
      commonWebViewToolbar.setNavigationOnClickListener {
         finish()
      }

      refreshLayout = findViewById(R.id.commonRefreshLyt)
      webView = findViewById(R.id.commonWebView)
      titleText = findViewById(R.id.commonWebViewTitle)
      refreshLayout!!.isEnabled = false
      refreshLayout!!.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.alpha_90_white))
      refreshLayout!!.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
      refreshLayout!!.setDistanceToTriggerSync(200)
   }

   /**
    * 增加 ToolBar 右侧按钮
    */
   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
      menuInflater.inflate(R.menu.menu_content, menu)
      return super.onCreateOptionsMenu(menu)
   }

   override fun onOptionsItemSelected(item: MenuItem?): Boolean {
      when (item?.itemId) {
         R.id.action_share -> {
            Intent().run {
               action = Intent.ACTION_SEND
               putExtra(Intent.EXTRA_TEXT,
                       getString(
                               R.string.share_article_url,
                               getString(R.string.app_name),
                               title,
                               shareUrl
                       ))
            }
            return true
         }
         R.id.action_like -> {
            ToastUtils.showToast(this@CommonWebViewActivity, "收藏成功")
            if (false) {   // 该处判断当前是否登陆
//               mPresenter?.addCollectArticle(shareId)
            } else {
               Intent(this, LoginActivity::class.java).run {
                  startActivity(this)
               }
               ToastUtils.showToast(this@CommonWebViewActivity, resources.getString(R.string.login_tint))
            }
            return true
         }
         R.id.action_browser -> {
            Intent().run {
               action = "android.intent.action.VIEW"
               data = Uri.parse(shareUrl)
               startActivity(this)
            }
            return true
         }
      }
      return super.onOptionsItemSelected(item)
   }

   @SuppressLint("SetJavaScriptEnabled")
   private fun initWebView() {
      val webSettings = webView!!.settings
      webSettings.javaScriptCanOpenWindowsAutomatically = true
      webSettings.javaScriptEnabled = true   // 支持 js
      webSettings.defaultTextEncodingName = "UTF-8"   // 设置默认字符编码
      webSettings.setSupportZoom(false)  // 支持缩放
      webSettings.builtInZoomControls = false
      webSettings.blockNetworkImage = false

      webView!!.webChromeClient = object : WebChromeClient() {
         override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
               refreshLayout!!.isRefreshing = false
               titleText!!.text = title
            } else {
               titleText!!.text = "加载中..."
            }
         }
      }
      webView!!.webViewClient = object : WebViewClient() {  // 覆盖用 WebView 默认使用第三方或系统默认浏览器打开网页的行为，使网页用 WebView 打开
         override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            LogUtils.e("开始加载")
         }

         override fun onPageFinished(view: WebView, url: String) {
            LogUtils.e("加载结束")
         }

         override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
         }

         override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()
         }
      }
      webView!!.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
         val intent = Intent(Intent.ACTION_VIEW)
         intent.addCategory(Intent.CATEGORY_BROWSABLE)
         intent.data = Uri.parse(url)
         startActivity(intent)
      }
   }

   override fun loadData() {
      refreshLayout!!.post {
         refreshLayout!!.isRefreshing = true
         webView!!.loadUrl(shareUrl)
      }
   }

   override fun getLayoutId(): Int {
      return R.layout.activity_common_webview
   }

   override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
      if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) {
         webView!!.canGoBack()
         return true
      }
      return super.onKeyDown(keyCode, event)
   }
}
