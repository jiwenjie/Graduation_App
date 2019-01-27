package com.example.root.graduation_app.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.*
import android.widget.TextView
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.ui.fragment.WeChatFragment
import com.example.root.graduation_app.ui.fragment.BookFragment
import com.example.root.graduation_app.ui.fragment.KnowledgeTreeFragment
import com.example.root.graduation_app.ui.fragment.HomeFragment
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_index_main.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/27
 *  desc:
 *  version:1.0
 */
class IndexMainActivity : BaseActivity() {

   private val BOTTOM_INDEX: String = "bottom_index"

   private val FRAGMENT_HOME = 0x01
   private val FRAGMENT_BOOK = 0x02
   private val FRAGMENT_WECHAT = 0x05
   private val FRAGMENT_KNOWLEDGE_TREE = 0x04

   private var mIndex = FRAGMENT_HOME

   private var mHomeFragment: HomeFragment? = null // 项目
   private var mBookFragment: BookFragment? = null
   private var mWeChatFragment: WeChatFragment? = null   // 微信公众号
   private var mKnowledgeTreeFragment: KnowledgeTreeFragment? = null

   protected var isLogin = false

   /**
    * local username
    */
//   private val username: String by Preference(Constants.USERNAME_KEY, "")

   /**
    * username TextView
    */
   private var nav_username: TextView? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      if (savedInstanceState != null) {
         mIndex = savedInstanceState.getInt(BOTTOM_INDEX)
      }
      super.onCreate(savedInstanceState)
   }

   override fun initActivity(savedInstanceState: Bundle?) {

      StatusBarUtil.setColorForDrawerLayout(this, drawer_layout,
              ContextCompat.getColor(this, R.color.colorPrimary))

      toolbar.run {
         title = getString(R.string.app_name)
         setSupportActionBar(this)
      }

      bottom_navigation.run {
         // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
         // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
         // BottomNavigationViewHelper.disableShiftMode(this)
         labelVisibilityMode = 1
         setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
      }

      initDrawerLayout()

      nav_view.run {
         setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
         nav_username = getHeaderView(0).findViewById(R.id.tv_username)
         menu.findItem(R.id.nav_logout).isVisible = isLogin
      }
      nav_username?.run {
         //         text = if (!isLogin) {
//            getString(R.string.login)
//         } else {
//            username
//         }
         setOnClickListener {
            if (!isLogin) {
               Intent(this@IndexMainActivity, LoginActivity::class.java).run {
                  startActivity(this)
               }
            } else {

            }
         }
      }

      showFragment(mIndex)

      floating_action_btn.run {
         setOnClickListener(onFABClickListener)
      }
   }

   /**
    * init DrawerLayout
    */
   private fun initDrawerLayout() {
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//         val params = window.attributes
//         params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//         drawer_layout.fitsSystemWindows = true
//         drawer_layout.clipToPadding = false
//      }
      drawer_layout.run {
         val toggle = ActionBarDrawerToggle(
                 this@IndexMainActivity,
                 this,
                 toolbar,
                 R.string.navigation_drawer_open,
                 R.string.navigation_drawer_close)
         addDrawerListener(toggle)
         toggle.syncState()
      }
   }

   override fun onSaveInstanceState(outState: Bundle?) {
      super.onSaveInstanceState(outState)
      outState?.putInt(BOTTOM_INDEX, mIndex)
   }

   override fun loadData() {

   }

   /**
    * 展示Fragment
    * @param index
    */
   private fun showFragment(index: Int) {
      val transaction = supportFragmentManager.beginTransaction()
      hideFragments(transaction)
      mIndex = index
      when (index) {
         FRAGMENT_HOME // 首页
         -> {
            toolbar.title = getString(R.string.app_name)
            if (mHomeFragment == null) {
               mHomeFragment = HomeFragment.newInstance()
               transaction.add(R.id.container, mHomeFragment!!, "home")
            } else {
               transaction.show(mHomeFragment!!)
            }
         }
         FRAGMENT_BOOK // 知识书籍
         -> {
            toolbar.title = getString(R.string.knowledge_system)
            if (mBookFragment == null) {
               mBookFragment = BookFragment.newInstance()
               transaction.add(R.id.container, mBookFragment!!, "knowledge")
            } else {
               transaction.show(mBookFragment!!)
            }
         }
         FRAGMENT_WECHAT // 公众号
         -> {
            toolbar.title = getString(R.string.wechat)
            if (mWeChatFragment == null) {
               mWeChatFragment = WeChatFragment.newInstance()
               transaction.add(R.id.container, mWeChatFragment!!, "wechat")
            } else {
               transaction.show(mWeChatFragment!!)
            }
         }
         FRAGMENT_KNOWLEDGE_TREE // 知识体系
         -> {
            toolbar.title = getString(R.string.project)
            if (mKnowledgeTreeFragment == null) {
               mKnowledgeTreeFragment = KnowledgeTreeFragment.newInstance()
               transaction.add(R.id.container, mKnowledgeTreeFragment!!, "project")
            } else {
               transaction.show(mKnowledgeTreeFragment!!)
            }
         }
      }
      transaction.commit()
   }

   /**
    * 隐藏所有的Fragment
    */
   private fun hideFragments(transaction: FragmentTransaction) {
      mHomeFragment?.let { transaction.hide(it) }
      mBookFragment?.let { transaction.hide(it) }
      mWeChatFragment?.let { transaction.hide(it) }
      mKnowledgeTreeFragment?.let { transaction.hide(it) }
   }

   /**
    * NavigationItemSelect监听
    */
   private val onNavigationItemSelectedListener =
           BottomNavigationView.OnNavigationItemSelectedListener { item ->
              return@OnNavigationItemSelectedListener when (item.itemId) {
                 R.id.action_home -> {
                    showFragment(FRAGMENT_HOME)
                    true
                 }
                 R.id.action_book -> {
                    showFragment(FRAGMENT_BOOK)
                    true
                 }
                 R.id.action_wechat -> {
                    showFragment(FRAGMENT_WECHAT)
                    true
                 }
                 R.id.action_system -> {
                    showFragment(FRAGMENT_KNOWLEDGE_TREE)
                    true
                 }
                 else -> {
                    false
                 }
              }
           }

   /**
    * NavigationView 监听
    */
   private val onDrawerNavigationItemSelectedListener =
           NavigationView.OnNavigationItemSelectedListener { item ->
              when (item.itemId) {
                 R.id.nav_collect -> {
//                    if (isLogin) {
//                       Intent(this@MainActivity, CommonActivity::class.java).run {
//                          putExtra(Constant.TYPE_KEY, Constant.Type.COLLECT_TYPE_KEY)
//                          startActivity(this)
//                       }
//                    } else {
//                       showToast(resources.getString(R.string.login_tint))
//                       Intent(this@MainActivity, LoginActivity::class.java).run {
//                          startActivity(this)
//                       }
//                    }
                    // drawer_layout.closeDrawer(GravityCompat.START)
                 }
                 R.id.nav_setting -> {
//                    Intent(this@MainActivity, SettingActivity::class.java).run {
//                       // putExtra(Constant.TYPE_KEY, Constant.Type.SETTING_TYPE_KEY)
//                       startActivity(this)
//                    }
                    // drawer_layout.closeDrawer(GravityCompat.START)
                 }
                 R.id.nav_about_us -> {
//                    Intent(this@MainActivity, CommonActivity::class.java).run {
//                       putExtra(Constant.TYPE_KEY, Constant.Type.ABOUT_US_TYPE_KEY)
//                       startActivity(this)
//                    }
                    // drawer_layout.closeDrawer(GravityCompat.START)
                 }
                 R.id.nav_logout -> {
//                    logout()
                    // drawer_layout.closeDrawer(GravityCompat.START)
                 }
                 R.id.nav_night_mode -> {
//                    if (SettingUtil.getIsNightMode()) {
//                       SettingUtil.setIsNightMode(false)
//                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    } else {
//                       SettingUtil.setIsNightMode(true)
//                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    }
//                    window.setWindowAnimations(R.style.WindowAnimationFadeInOut)
//                    recreate()
                 }
                 R.id.nav_todo -> {
//                    if (isLogin) {
//                       Intent(this@MainActivity, TodoActivity::class.java).run {
//                          startActivity(this)
//                       }
//                    } else {
//                       showToast(resources.getString(R.string.login_tint))
//                       Intent(this@MainActivity, LoginActivity::class.java).run {
//                          startActivity(this)
//                       }
//                    }
                    // drawer_layout.closeDrawer(GravityCompat.START)
                 }
              }
              true
           }

   override fun recreate() {
      try {
         val fragmentTransaction = supportFragmentManager.beginTransaction()
         if (mHomeFragment != null) {
            fragmentTransaction.remove(mHomeFragment!!)
         }
         if (mBookFragment != null) {
            fragmentTransaction.remove(mBookFragment!!)
         }
         if (mKnowledgeTreeFragment != null) {
            fragmentTransaction.remove(mKnowledgeTreeFragment!!)
         }
         if (mWeChatFragment != null) {
            fragmentTransaction.remove(mWeChatFragment!!)
         }
         fragmentTransaction.commitAllowingStateLoss()
      } catch (e: Exception) {
         e.printStackTrace()
      }
      super.recreate()
   }

   /**
    * FAB 监听
    */
   private val onFABClickListener = View.OnClickListener {
      when (mIndex) {
         FRAGMENT_HOME -> {
            mHomeFragment?.scrollToTop()
         }
         FRAGMENT_BOOK -> {
//            mBookFragment?.scrollToTop()
         }
         FRAGMENT_WECHAT -> {
//            mWeChatFragment?.scrollToTop()
         }
         FRAGMENT_KNOWLEDGE_TREE -> {
            mKnowledgeTreeFragment?.scrollToTop()
         }
      }
   }

   override fun onCreateOptionsMenu(menu: Menu?): Boolean {
      menuInflater.inflate(R.menu.menu_activity_main, menu)
      return super.onCreateOptionsMenu(menu)
   }

   override fun onOptionsItemSelected(item: MenuItem?): Boolean {
      when (item?.itemId) {
         R.id.action_search -> {
            Intent(this, SearchActivity::class.java).run {
               startActivity(this)
            }
            return true
         }
      }
      return super.onOptionsItemSelected(item)
   }

   private var mExitTime: Long = 0

   override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
      if (keyCode == KeyEvent.KEYCODE_BACK) {
         if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
            finish()
         } else {
            mExitTime = System.currentTimeMillis()
            ToastUtils.showToast(applicationContext, "再按一次退出程序")
         }
         return true
      }
      return super.onKeyDown(keyCode, event)
   }

   override fun onDestroy() {
      super.onDestroy()
      mHomeFragment = null
      mBookFragment = null
      mKnowledgeTreeFragment = null
      mWeChatFragment = null
      nav_username = null
   }

   override fun getLayoutId(): Int = R.layout.activity_index_main
}