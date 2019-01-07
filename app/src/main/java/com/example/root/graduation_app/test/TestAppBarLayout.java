package com.example.root.graduation_app.test;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.base_library.base_utils.ScreenUtils;

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/24
 * desc:
 * version:1.0
 */
public class TestAppBarLayout extends AppCompatActivity {

   private Toolbar toolbar;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
         ((Toolbar.LayoutParams) toolbar.getLayoutParams()).bottomMargin = ScreenUtils.dip2px(this, 24f);
      }
   }

   private WebChromeClient webChromeClient = new WebChromeClient() {
      //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
      @Override
      public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
         AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
         localBuilder.setMessage(message).setPositiveButton("确定", null);
         localBuilder.setCancelable(false);
         localBuilder.create().show();

         //注意:
         //必须要这一句代码:result.confirm()表示:
         //处理结果为确定状态同时唤醒WebCore线程
         //否则不能继续点击按钮
         result.confirm();
         return true;
      }

      //获取网页标题
      @Override
      public void onReceivedTitle(WebView view, String title) {
         super.onReceivedTitle(view, title);
         Log.i("ansen", "网页标题:" + title);
      }

      //加载进度回调
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
//         progressBar.setProgress(newProgress);
      }
   };
}
