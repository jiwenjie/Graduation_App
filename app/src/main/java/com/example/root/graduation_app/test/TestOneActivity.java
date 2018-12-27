package com.example.root.graduation_app.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.base_library.base_utils.ToastUtils;
import com.example.root.graduation_app.R;
import com.example.root.graduation_app.mvp.fragment.DiscoverFragment;
import com.example.root.graduation_app.mvp.fragment.EntertainmentFragment;
import com.example.root.graduation_app.mvp.fragment.IndexFragment;
import com.example.root.graduation_app.mvp.fragment.MineFragment;
import com.example.root.graduation_app.widget.CustomTabView;
import com.example.root.graduation_app.widget.NoScrollViewPager;
import com.pgyersdk.crash.PgyCrashManager;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/17
 * desc:
 * version:1.0
 */
public class TestOneActivity extends AppCompatActivity implements View.OnClickListener {

   private List<Fragment> fragments = new ArrayList<>();

   private NoScrollViewPager noScrollViewPager;

   private IndexFragment indexFragment;
   private DiscoverFragment discoverFragment;
   private EntertainmentFragment entertainmentFragment;
   private MineFragment mineFragment;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_one);

//      noScrollViewPager = findViewById(R.id.home_container);

      fragments.add(indexFragment = IndexFragment.newInstance());
      fragments.add(discoverFragment = DiscoverFragment.newInstance());
      fragments.add(entertainmentFragment = EntertainmentFragment.newInstance());
      fragments.add(mineFragment = MineFragment.newInstance());

//      noScrollViewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), fragments));

//      findViewById(R.id.indexLyt).setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            setNormalBackground();
//            noScrollViewPager.setCurrentItem(0, false);
//            ((ImageView) findViewById(R.id.indexLyt_img)).setImageResource(R.drawable.ic_home_selected);
//         }
//      });
//
//      findViewById(R.id.discoveryLyt).setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            setNormalBackground();
//            noScrollViewPager.setCurrentItem(1, false);
//            ((ImageView) findViewById(R.id.discoveryLyt_img)).setImageResource(R.drawable.ic_discovery_selected);
//         }
//      });
   }

//   private void onTabItemSelected(int id) {
//      Fragment fragment = null;
//      switch (id) {
//         case R.id.tab_menu_home:
//            fragment = fragments.get(0);
//            break;
//         case R.id.tab_menu_discovery:
//            fragment = fragments.get(1);
//            break;
//         case R.id.tab_menu_hot:
//            fragment = fragments.get(2);
//            break;
//         case R.id.tab_menu_profile:
//            fragment = fragments.get(3);
//            break;
//      }
//      if (fragment != null) {
//         getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).commit();
//      }
//   }

   @Override
   public void onClick(View v) {
      setNormalBackground();
      ToastUtils.showToast(getApplicationContext(), "点击", Toast.LENGTH_SHORT);
      switch (v.getId()) {
         case R.id.indexLyt: {
//            noScrollViewPager.setCurrentItem(0, false);
            ((ImageView) findViewById(R.id.indexLyt_img)).setImageResource(R.drawable.ic_home_selected);
            break;
         }
         case R.id.discoveryLyt: {
//            noScrollViewPager.setCurrentItem(1, false);
            ((ImageView) findViewById(R.id.discoveryLyt_img)).setImageResource(R.drawable.ic_discovery_selected);
            break;
         }
         case R.id.entertainmentLyt: {
//            noScrollViewPager.setCurrentItem(2, false);
            ((ImageView) findViewById(R.id.entertainmentLyt_img)).setImageResource(R.drawable.ic_hot_selected);
            break;
         }
         case R.id.mineLyt: {
//            noScrollViewPager.setCurrentItem(3, false);
            ((ImageView) findViewById(R.id.mineLyt_img)).setImageResource(R.drawable.ic_mine_selected);
            break;
         }
      }
   }

   private void setNormalBackground() {
      ((ImageView) findViewById(R.id.indexLyt_img)).setImageResource(R.drawable.ic_home_normal);
      ((ImageView) findViewById(R.id.discoveryLyt_img)).setImageResource(R.drawable.ic_discovery_normal);
      ((ImageView) findViewById(R.id.entertainmentLyt_img)).setImageResource(R.drawable.ic_hot_normal);
      ((ImageView) findViewById(R.id.mineLyt_img)).setImageResource(R.drawable.ic_mine_normal);
   }

   private class MainAdapter extends FragmentPagerAdapter {

      private List<Fragment> list;

      MainAdapter(FragmentManager fragmentManager, List<Fragment> list) {
         super(fragmentManager);
         this.list = list;
      }

      @Override
      public Fragment getItem(int i) {
         return list.get(i);
      }

      @Override
      public int getCount() {
         return list == null ? 0 : list.size();
      }
   }
}
