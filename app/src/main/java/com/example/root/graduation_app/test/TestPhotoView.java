package com.example.root.graduation_app.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

       viewPager = findViewById(R.id.viewpager);
    }
}
