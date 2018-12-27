//package com.example.root.graduation_app.test;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.root.graduation_app.R;
//import com.example.root.graduation_app.mvp.fragment.DiscoverFragment;
//import com.example.root.graduation_app.mvp.fragment.EntertainmentFragment;
//import com.example.root.graduation_app.mvp.fragment.IndexFragment;
//import com.example.root.graduation_app.mvp.fragment.MineFragment;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
///**
// * author:Jiwenjie
// * email:278630464@qq.com
// * time:2018/12/24
// * desc:测试 tabItem
// * version:1.0
// */
//public class TestTabItem extends AppCompatActivity {
//
//   private List<String> titles;
//
//   private TabLayout tabLayout;
//   private ViewPager viewPager;
//
//   private int[] tabIcons = {
//           R.drawable.tab_index_selector,
//           R.drawable.tab_discovery_selector,
//           R.drawable.tab_hot_selector,
//           R.drawable.tab_mine_selector};
//
//   @Override
//   protected void onCreate(@Nullable Bundle savedInstanceState) {
//      super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_test_tab_item);
//
//      tabLayout = findViewById(R.id.activity_test_tab_tabLyt);
//      viewPager = findViewById(R.id.activity_test_tab_viewPager);
//
//      List<Fragment> fragments = new ArrayList<>();
//      titles = new ArrayList<>();
//
//      fragments.add(new IndexFragment());
//      fragments.add(new DiscoverFragment());
//      fragments.add(new EntertainmentFragment());
//      fragments.add(new MineFragment());
//
//      titles.add("首页");
//      titles.add("发现");
//      titles.add("热门");
//      titles.add("我的");
//
//      FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
//      viewPager.setAdapter(adapter);
//      tabLayout.setupWithViewPager(viewPager);
//      setupTabIcons();
//
//
//      tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//         @Override
//         public void onTabSelected(TabLayout.Tab tab) {
//            changeTabSelect(tab);
//         }
//
//         @Override
//         public void onTabUnselected(TabLayout.Tab tab) {
//            changeTabNormal(tab);
//         }
//
//         @Override
//         public void onTabReselected(TabLayout.Tab tab) {
//
//         }
//      });
//
//      viewPager.setCurrentItem(0);
//   }
//
//   private void changeTabSelect(TabLayout.Tab tab) {
//      View view = tab.getCustomView();
//      assert view != null;
//      ImageView img_title = view.findViewById(R.id.img_tab);
//      TextView txt_title = view.findViewById(R.id.titleText);
//      txt_title.setTextColor(Color.YELLOW);
//      switch (txt_title.getText().toString()) {
//         case "首页":
//            img_title.setImageResource(R.drawable.ic_home_selected);
//            viewPager.setCurrentItem(0);
//            break;
//         case "发现":
//            img_title.setImageResource(R.drawable.ic_discovery_selected);
//            viewPager.setCurrentItem(1);
//            break;
//         case "热门":
//            img_title.setImageResource(R.drawable.ic_hot_selected);
//            viewPager.setCurrentItem(2);
//            break;
//         case "我的":
//            img_title.setImageResource(R.drawable.ic_mine_selected);
//            viewPager.setCurrentItem(3);
//            break;
//      }
//   }
//
//   private void changeTabNormal(TabLayout.Tab tab) {
//      View view = tab.getCustomView();
//      assert view != null;
//      ImageView img_title = view.findViewById(R.id.img_tab);
//      TextView txt_title = view.findViewById(R.id.titleText);
//      txt_title.setTextColor(Color.WHITE);
//      switch (txt_title.getText().toString()) {
//         case "首页":
//            img_title.setImageResource(R.drawable.ic_home_normal);
//            break;
//         case "发现":
//            img_title.setImageResource(R.drawable.ic_discovery_normal);
//            break;
//         case "热门":
//            img_title.setImageResource(R.drawable.ic_hot_normal);
//            break;
//         case "我的":
//            img_title.setImageResource(R.drawable.ic_mine_normal);
//            break;
//      }
//   }
//
//   private void setupTabIcons() {
//      Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(getTabView(0));
//      Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(getTabView(1));
//      Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(getTabView(2));
//      Objects.requireNonNull(tabLayout.getTabAt(3)).setCustomView(getTabView(3));
//   }
//
//   public View getTabView(int position) {
//      View view = LayoutInflater.from(this).inflate(R.layout.tab_item_layout, null);
//      TextView title = view.findViewById(R.id.titleText);
//      title.setText(titles.get(position));
//      ImageView imgIcon = view.findViewById(R.id.img_tab);
//      imgIcon.setImageResource(tabIcons[position]);
//      return view;
//   }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
