package com.example.root.graduation_app.test;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
}
