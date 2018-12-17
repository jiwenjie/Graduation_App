package com.example.root.graduation_app.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.root.graduation_app.R;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/17
 * desc:
 * version:1.0
 */
public class TestOneActivity extends AppCompatActivity {

//    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        TabLayout layout = findViewById(R.id.tabLayout);
        TabItem dayItem = layout.findViewById(R.id.dayItem);
        TabItem weekItem = layout.findViewById(R.id.weekItem);
        TabItem monthItem = layout.findViewById(R.id.monthItem);

        layout.removeAllTabs();

//        layout.addView(weekItem);
//        layout.addView(dayItem);
//        layout.addView(monthItem);

//        try {
//            toolbar.setTitle("测试是否崩溃");
//        } catch (Exception e) {
//            // 异常上传 蒲公英
//            e.printStackTrace();
//            PgyCrashManager.reportCaughtException(e);
//        }
    }
}
