package com.example.root.graduation_app.mvp.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.base_library.base_utils.ScreenUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.utils.Constants
import kotlinx.android.synthetic.main.activity_github.*
import java.util.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/30
 *  desc:
 *  version:1.0
 */
@Suppress("OverridingDeprecatedMember")
class GithubActivity : BaseActivity() {

   companion object {
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, GithubActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun loadData() {

   }

   override fun getLayoutId(): Int = R.layout.activity_github

   @SuppressLint("SetJavaScriptEnabled")
   override fun initActivity(savedInstanceState: Bundle?) {
      val webSettings = activity_github_webView.settings
      webSettings.javaScriptEnabled = true
      webSettings.javaScriptCanOpenWindowsAutomatically = true
      webSettings.defaultTextEncodingName = "UTF-8"
      webSettings.setSupportZoom(false)
      webSettings.blockNetworkImage = false
      webSettings.builtInZoomControls = false
      webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
      activity_github_webView.loadUrl(Constants.GITHUB_URL)
      activity_github_webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
      activity_github_webView.webChromeClient = WebChromeClient()
      activity_github_webView.webViewClient = object : WebViewClient() {
         override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view!!.loadUrl(url)
            return true
         }

         override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            view!!.loadUrl(String.format(Locale.CHINA, "javascript:document.body.style.paddingTop='%fpx'; void 0", ScreenUtils.px2dip(applicationContext, activity_github_webView.paddingTop.toFloat())))
         }
      }
   }
}