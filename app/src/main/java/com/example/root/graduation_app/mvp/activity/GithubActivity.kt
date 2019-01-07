package com.example.root.graduation_app.mvp.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ScreenUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.R.id.activity_github_webView
import com.example.root.graduation_app.utils.Constants
import com.example.root.graduation_app.utils.StatusBarUtils
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_github.*
import java.util.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/30
 *  desc:
 *  version:1.0
 */
@Suppress("OverridingDeprecatedMember", "DEPRECATION")
class GithubActivity : BaseActivity() {

   companion object {
      fun runActivity(activity: Activity) {
         val intent = Intent(activity, GithubActivity::class.java)
         activity.startActivity(intent)
      }
   }

   override fun loadData() {
      activity_github_webView.loadUrl(Constants.GITHUB_URL)
   }

   override fun getLayoutId(): Int = R.layout.activity_github

   @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
   override fun initActivity(savedInstanceState: Bundle?) {
      StatusBarUtil.setColor(this@GithubActivity, ContextCompat.getColor(this@GithubActivity, R.color.colorPrimary), 0)

      val webSettings = activity_github_webView.settings
      webSettings.javaScriptEnabled = true
      webSettings.javaScriptCanOpenWindowsAutomatically = true
      webSettings.defaultTextEncodingName = "UTF-8"
      //支持屏幕缩放
      webSettings.setSupportZoom(false)
      webSettings.builtInZoomControls = false

      webSettings.blockNetworkImage = false
      webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

      activity_github_webView.addJavascriptInterface(this, "android")//添加js监听 这样html就能调用客户端
      activity_github_webView.webChromeClient = webChromeClient
      activity_github_webView.webViewClient = webViewClient
   }


   private val webViewClient = object : WebViewClient() {
      override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
         showProgress("正在加载...")
      }

      override fun onPageFinished(view: WebView?, url: String?) {
         dismissProgress()
      }

      override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
         view!!.loadUrl(url)
         return true
      }
   }

   //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
   private val webChromeClient = object : WebChromeClient() {
      //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
      override fun onJsAlert(webView: WebView, url: String, message: String, result: JsResult): Boolean {
         val localBuilder = AlertDialog.Builder(webView.context)
         localBuilder.setMessage(message).setPositiveButton("确定", null)
         localBuilder.setCancelable(false)
         localBuilder.create().show()

         //注意:
         //必须要这一句代码:result.confirm()表示:
         //处理结果为确定状态同时唤醒WebCore线程
         //否则不能继续点击按钮
         result.confirm()
         return true
      }


      //获取网页标题
      override fun onReceivedTitle(view: WebView, title: String) {
         super.onReceivedTitle(view, title)
         LogUtils.i("网页标题:$title")
      }

   }

   override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
      if (activity_github_webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK) {   //点击返回按钮的时候判断有没有上一页
         activity_github_webView.goBack()  // goBack()表示返回webView的上一页面
         return true
      }
      return super.onKeyDown(keyCode, event)
   }

   override fun onDestroy() {
      //释放资源
      activity_github_webView.destroy()
      super.onDestroy()
   }
}