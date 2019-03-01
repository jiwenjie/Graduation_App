package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.example.root.graduation_app.R
import kotlinx.android.synthetic.main.activity_theme.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/28
 *  desc:更换主题色的 Activity
 *  version:1.0
 */
class ThemeActivity : AppCompatActivity() {

    companion object {
       @JvmStatic
       fun runActivity(activity: Activity) {
           val intent = Intent(activity, ThemeActivity::class.java)
           activity.startActivity(intent)
       }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        btn_change.setOnClickListener {
            setTheme(R.style.ZhiHuBlueTheme)
//            toolbar.setBackgroundColor(resources.getColor(themeInfo.getColor()))
//            recyclerView.setBackgroundColor(resources.getColor(themeInfo.getBackground()))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.statusBarColor = ContextCompat.getColor(this, R.color.zhihuBlue)
            }
        }

        activity_theme_toolBar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, IndexMainActivity::class.java))
        super.onBackPressed()
    }
}