package com.example.root.graduation_app.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.base_library.RetrofitManager
import com.example.base_library.RxBus
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.rxbusevent.UserInfoChangeEvent
import com.example.root.graduation_app.ui.fragment.WeChatFragment
import com.example.root.graduation_app.ui.fragment.BookFragment
import com.example.root.graduation_app.ui.fragment.KnowledgeTreeFragment
import com.example.root.graduation_app.ui.fragment.HomeFragment
import com.example.root.graduation_app.utils.*
import com.example.root.graduation_app.widget.SignUpDialog
import com.jaeger.library.StatusBarUtil
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_index_main.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.FileInputStream
import java.util.*
import java.util.concurrent.TimeUnit

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
     * username TextView
     */
    private var nav_username: TextView? = null

    private var user: LoginUser? = null
    private var signUp: TextView? = null

    private var bgImg: ImageView? = null
    private var imgAvatar: ImageView? = null
    private var userName: TextView? = null
    private var introduction: TextView? = null

    companion object {
        private const val REQ_REFRESH = 116

        @JvmStatic
        fun runActivity(activity: Activity) {
            val intent = Intent(activity, IndexMainActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(BOTTOM_INDEX)
        }
        super.onCreate(savedInstanceState)
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        StatusBarUtil.setColorForDrawerLayout(
            this, drawer_layout,
            ContextCompat.getColor(this, R.color.colorPrimary)
        )
        user = App.getLoginUser()
        isLogin = user != null && user?.isSignout == false   // 判断用户是否登录，登录则是 true，否则是 false

        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
        }
        initDrawerLayout()
        showFragment(mIndex)
        bottom_navigation.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = 1
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }
        floating_action_btn.run {
            setOnClickListener(onFABClickListener)
        }
    }

    /**
     * init DrawerLayout
     */
    @SuppressLint("SetTextI18n")
    private fun initDrawerLayout() {
        val headerView = nav_view.getHeaderView(0)
        signUp = headerView.findViewById(R.id.headerSignUpText)
        userName = headerView.findViewById(R.id.nicknameMe)
        val description = headerView.findViewById<LinearLayout>(R.id.descriptionLayout)
        introduction = headerView.findViewById(R.id.descriptionMe)
        imgAvatar = headerView.findViewById(R.id.avatarMe)

        if (isLogin) {
            // 说明此时用户登陆了账号
            userName?.text = user?.username
            introduction?.text = "简介：" + if (user?.profile.isNullOrEmpty()) "" else user?.profile
            signUp!!.visibility = View.VISIBLE
            if (user?.isSignintoday!!) { // 如果今天已经签到
                signUp!!.text = "已签到"
                signUp?.isEnabled = false
                signUp?.setBackgroundColor(ContextCompat.getColor(this@IndexMainActivity, R.color.item_date))
            }
        } else {
            userName?.text = "点击登陆"
            introduction?.text = "简介："
            signUp!!.visibility = View.GONE
        }

        nav_view.run {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
            menu.findItem(R.id.nav_logout).isVisible = isLogin
            bgImg = getHeaderView(0).findViewById(R.id.navHeaderBgImage)
            // 这里应该从网络取图片
            if (user?.avatar != null) {
                // 说明有头像存储
                val path = SharePreferencesUtil.getUserImgPath(ConstantConfig.KEY_USER_AVATAR)
                if (!path.isNullOrEmpty()) {
                    imgAvatar?.setImageBitmap(BitmapFactory.decodeFile(path))
                }
                PhoneUserUtils.loadAvatar(this@IndexMainActivity, user?.avatar!!, imgAvatar!!)
            }
            if (user?.bgimageurl != null) {
                // 说明此时有背景图片
                val path = SharePreferencesUtil.getUserImgPath(ConstantConfig.KEY_USER_BG)
                if (!path.isNullOrEmpty()) {
                    bgImg?.setImageBitmap(BitmapFactory.decodeFile(path))
                }
                CommonUtils.displayImgAsBitmap(this@IndexMainActivity, user?.bgimageurl!!, bgImg!!)
            }
        }
        drawer_layout.run {
            val toggle = ActionBarDrawerToggle(
                this@IndexMainActivity,
                this,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            addDrawerListener(toggle)
            toggle.syncState()
        }
        signUp!!.setOnClickListener {
            // 签到的点击事件
            signUpNow()
            drawer_layout.closeDrawers()
        }
        description.setOnClickListener {
            // 点击跳转用户编辑页
            drawer_layout.closeDrawers()
            Observable.timer(300, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (isLogin) {
                        ModifyUserInfoActivity.runActivity(this@IndexMainActivity, REQ_REFRESH)
                    } else {
                        LoginActivity.runActivity(this@IndexMainActivity, user)
                        ToastUtils.showToast(this@IndexMainActivity, resources.getString(R.string.login_tint))
                        LoginActivity.runActivity(this@IndexMainActivity, null)
                    }
                }
        }
    }

    /**
     * 签到的点击事件，网络请求
     */
    @SuppressLint("CheckResult")
    private fun signUpNow() {
        showProgress("正在签到...")
        RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
            .create(JacksonApi::class.java)
            .userSignUp(user?.userid!!, CommonUtils.getFormatDateTime(Date()))
            .compose(RxJavaUtils.applyObservableAsync())
            .subscribe({
                dismissProgress()
                if (it.result == "succeed") {
                    user = it.data
                    App.setLoginUser(it.data)
                    signUp!!.text = "已签到"
                    signUp?.isEnabled = false
                    signUp?.setBackgroundColor(ContextCompat.getColor(this@IndexMainActivity, R.color.item_date))
                    SharePreferencesUtil.saveAny(this@IndexMainActivity, ConstantConfig.SHARE_LOGIN_USER_NAME, user!!)
                    // 弹出提示框
                    val dialog = SignUpDialog(this@IndexMainActivity)
                    dialog.show()
                } else {
                    ToastUtils.showToast(this@IndexMainActivity, it.msg)
                }
            }, {
                dismissProgress()
                ToastUtils.showToast(this@IndexMainActivity, it.message.toString())
                LogUtils.e(it.message)
            })
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
                    drawer_layout.closeDrawer(GravityCompat.START)
                    if (isLogin) {
                        Observable.timer(300, TimeUnit.MILLISECONDS)
                            .subscribe {
                                CollectActivity.runActivity(this@IndexMainActivity)
                            }
                    } else {
                        ToastUtils.showToast(this, resources.getString(R.string.login_tint))
                        LoginActivity.runActivity(this, null)
                    }
                }
                R.id.nav_setting -> {
                    ToastUtils.showToast(this@IndexMainActivity, "正在开发中...")
                    drawer_layout.closeDrawer(GravityCompat.START)
                    val dialog = SignUpDialog(this@IndexMainActivity)
                    dialog.show()
//                    ThemeActivity.runActivity(this)
                }
                R.id.nav_about_us -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    Observable.timer(300, TimeUnit.MILLISECONDS)
                        .subscribe {
                            AboutMeActivity.runActivity(this@IndexMainActivity)
                        }
                }
                R.id.nav_logout -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    Observable.timer(300, TimeUnit.MILLISECONDS)
                        .subscribe {
                            logout()
                        }
                }
                R.id.nav_relax -> {      // 跳转进入音乐播放界面
                    drawer_layout.closeDrawer(GravityCompat.START)
                    Observable.timer(300, TimeUnit.MILLISECONDS)
                        .subscribe {
                            LocalMusicActivity.runActivity(this@IndexMainActivity)
//                               MusicIndexActivity.runActivity(this@IndexMainActivity)
                        }
                }
                R.id.nav_feedback -> {   // 向我反馈
                    drawer_layout.closeDrawer(GravityCompat.START)
                    if (isLogin) {
                        FeedBackActivity.runActivity(this@IndexMainActivity)
                    } else {
                        ToastUtils.showToast(this, resources.getString(R.string.login_tint))
                        LoginActivity.runActivity(this, null)
                    }
                }
                R.id.nav_todo -> {
                    drawer_layout.closeDrawer(GravityCompat.START)
                    Observable.timer(300, TimeUnit.MILLISECONDS)
                        .subscribe {
                            if (isLogin) {
                                TaskListActivity.runActivity(this@IndexMainActivity)
                            } else {
                                ToastUtils.showToast(this@IndexMainActivity, resources.getString(R.string.login_tint))
                                LoginActivity.runActivity(this@IndexMainActivity, null)
                            }
                        }
                }
            }
            true
        }

    /**
     * 退出登陆方法
     */
    private fun logout() {
        App.setLoginUser(null)
        LoginActivity.runActivity(this, null)
        finish()
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
                /** 增加共享动画元素 **/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {   // 系统版本在 5.0 以上开启共享动画
                    val searchMenuView: View? = toolbar?.findViewById(R.id.action_search)
                    searchMenuView?.visibility = View.GONE    // 5.0 以上为了解决跳转有重叠的问题所以设置 GONE
                    val intent = Intent(this, SearchActivity::class.java)
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        this, searchMenuView,
                        getString(R.string.transition_search_back)
                    ).toBundle()
                    startActivity(intent, options)
                } else {
                    Intent(this, SearchActivity::class.java).run {
                        startActivity(this)
                    }
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
                ToastUtils.showToast(applicationContext, resources.getString(R.string.exit_tip))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == REQ_REFRESH) {
            this.user = App.getLoginUser()

            userName?.text = user?.username
            if (user?.profile.isNullOrEmpty()) {
                introduction?.text = "简介："
            } else {
                introduction?.text = user?.profile
            }

            if (user?.avatar != null) {
                // 说明有头像存储
                val path = SharePreferencesUtil.getUserImgPath(ConstantConfig.KEY_USER_AVATAR)
                if (!path.isNullOrEmpty()) {
                    imgAvatar?.setImageBitmap(BitmapFactory.decodeFile(path))
                }
                PhoneUserUtils.loadAvatar(this@IndexMainActivity, user?.avatar!!, imgAvatar!!)
            }
            if (user?.profile != null) {
                // 说明此时有背景图片
                val path = SharePreferencesUtil.getUserImgPath(ConstantConfig.KEY_USER_BG)
                if (!path.isNullOrEmpty()) {
                    bgImg?.setImageBitmap(BitmapFactory.decodeFile(path))
                }
                CommonUtils.displayImgAsBitmap(this@IndexMainActivity, user?.bgimageurl!!, bgImg!!)
            }
        }
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