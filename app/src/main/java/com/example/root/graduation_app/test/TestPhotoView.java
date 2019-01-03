package com.example.root.graduation_app.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.root.graduation_app.R;


/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/28
 * desc: test photoView
 * version:1.0
 */
public class TestPhotoView extends AppCompatActivity {

   private ViewPager viewPager;
   private PhotoSlideAdapter adapter;
   WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

       viewPager = findViewById(R.id.viewpager);
       viewPager.setAdapter(adapter = new PhotoSlideAdapter(this));
    }

   public static boolean isMobileNO(String mobileNums) {
      /**
       * 判断字符串是否符合手机号码格式
       * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
       * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
       * 电信号段: 133,149,153,170,173,177,180,181,189
       * @param str
       * @return 待检测的字符串
       */
      String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
      if (TextUtils.isEmpty(mobileNums))
         return false;
      else
         return mobileNums.matches(telRegex);
   }
}
