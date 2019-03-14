@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.root.graduation_app.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextUtils
import android.view.View
import com.example.base_library.PermissionListener
import com.example.base_library.RetrofitManager
import com.example.base_library.RxBus
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_utils.ToastUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.App
import com.example.root.graduation_app.R
import com.example.root.graduation_app.utils.*
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_modify_user_info.*
import java.io.File
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.LoginUser
import com.example.root.graduation_app.rxbusevent.UserInfoChangeEvent


/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/01/29
 *  desc:修改用户信息的活动
 *  version:1.0
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ModifyUserInfoActivity : BaseActivity() {

    companion object {
        private const val REQUEST_TAKE_PHOTO = 0x110    // 拍照 requestCode
        private const val REQUEST_CHOOSE_ALBUM = 0x112  // 选择图片 requestCode

        private var AVATAR_OR_BGIMAGE = -1     // 0 表示选择头像，1 表示选择背景图片

        private const val TAKE_AVATAR_PICTURE = 0
        private const val TAKE_BG_IMAGE_PICTURE = 1

        const val REQUEST_CODE_CAPTURE = 3
        const val REQUEST_CODE_CAPTURE_CROP = 4
        const val REQUEST_CODE_ALBUM = 5

        private var srcNickname: String? = ""
        private var srcAvatar: String? = ""
        private var srcBgImage: String? = ""
        private var srcDescription: String? = ""
        private var userAvatarPath: String? = ""
        private var userBgImagePath: String? = ""

        var imgUri: Uri? = null
        var imageFile: File? = null
        var imageCropFile: File? = null

        @JvmStatic
        fun runActivity(activity: Activity) {
            val intent = Intent(activity, ModifyUserInfoActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun initActivity(savedInstanceState: Bundle?) {
        StatusBarUtil.setColor(
            this@ModifyUserInfoActivity,
            ContextCompat.getColor(this@ModifyUserInfoActivity, R.color.colorPrimary), 0
        )
        initView()
        initEvent()
    }

    private fun initView() {
        srcNickname = App.getLoginUser()?.username   // 从本地读取 nickName
        srcAvatar = App.getLoginUser()?.avatar       // 本地读取头像
        srcDescription = App.getLoginUser()?.profile
        // todo 暂时注释，这里需要注意逻辑部分
        if (srcAvatar != null) {
            PhoneUserUtils.loadAvatar(this@ModifyUserInfoActivity, srcAvatar!!, userAvatar)
        }
        userNickname.text = srcNickname
        nicknameEdit.setText(srcNickname)

        if (TextUtils.isEmpty(srcDescription)) {
            userDescription.visibility = View.GONE
        } else {
            descriptionEdit.setText(srcDescription)
            userDescription.visibility = View.VISIBLE
            userDescription.text = String.format(resources.getString(R.string.description_content), srcDescription)
        }

        // 设置初始化的文字
        nicknameLimitText.text =
                String.format(resources.getString(R.string.nickname_length_limit), nicknameEdit.text!!.length)
        descriptionLimitText.text =
                String.format(resources.getString(R.string.description_length_limit), descriptionEdit.text!!.length)

        // 输入昵称的 text change 监听
        nicknameEdit.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val length = nicknameEdit.text.toString().trim { it <= ' ' }.length
                nicknameLimitText.text = String.format(resources.getString(R.string.nickname_length_limit), length)
                userNickname.text = s.toString().trim { it <= ' ' }
            }
        })
        // 输入简介的 text change 监听
        descriptionEdit.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val length = descriptionEdit.text.toString().trim { it <= ' ' }.length
                descriptionLimitText.text =
                        String.format(resources.getString(R.string.description_length_limit), length)
                if (TextUtils.isEmpty(s.toString().trim { it <= ' ' })) {
                    userDescription.text = ""
                    userDescription.visibility = View.GONE
                } else {
                    userDescription.text = String.format(
                        resources.getString(R.string.description_content),
                        s.toString().trim { it <= ' ' })
                    userDescription.visibility = View.VISIBLE
                }
            }
        })
        loadBg()
    }

    private fun loadBg() {

    }

    private fun initEvent() {
        activity_modify_user_info_return.setOnClickListener {
            finish()
        }

        save.setOnClickListener {
            // 保存按钮
            saveUserInfo()
        }

        userAvatar.setOnClickListener {
            // 点击拍照或者选择相册（选择头像图片）
            dialogSelect(TAKE_AVATAR_PICTURE)
        }

        bgImageCamera.setOnClickListener {
            // 选择封面图片，拍照，你的相册
            dialogSelect(TAKE_BG_IMAGE_PICTURE)
        }
    }

    @SuppressLint("CheckResult")
    private fun saveUserInfo() {
        if (!validateNickname()) {
            return
        }
        if (isUserInfoChanged) {
            val nickname = nicknameEdit.text.toString().trim()
            val description = descriptionEdit.text.toString().trim()
            val avatarFilePath = if (TextUtils.isEmpty(userAvatarPath)) "" else userAvatarPath
            val bgImageFilePath = if (TextUtils.isEmpty(userBgImagePath)) "" else userBgImagePath

            // todo 开始把信息上传服务器，成功后使用 RxBus 通知首页更改信息
            showProgress("信息修改中...")
            RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
                .create(JacksonApi::class.java)
                .changeNickNameAndDescription(App.getLoginUser()?.userid!!, nickname, description)
                .compose(RxJavaUtils.applyObservableAsync())
                .subscribe({
                    dismissProgress()
                    if (it.result == "succeed") {
                        // 返回值表示成功后，使用 RxBus 更新数据，之后直接把当前活动 finish 掉即可。
                        ToastUtils.showToast(this@ModifyUserInfoActivity, "信息更改成功")
                        val user = it.data
                        LogUtils.e(user.profile + user.userphone)
                        App.setLoginUser(user)
                        RxBus.mBus.post(UserInfoChangeEvent())
                        finish()
                    } else {
                        ToastUtils.showToast(this@ModifyUserInfoActivity, it.msg)
                    }
                }, {
                    dismissProgress()
                    ToastUtils.showToast(this@ModifyUserInfoActivity, it.message.toString())
                })
        } else {
            // 这里不做什么特殊处理，直接过去
            IndexMainActivity.runActivity(this)
            finish()
            overridePendingTransition(0, 0)
        }
    }

    /**
     * 判断用户是否修改了个人信息。
     * @return 修改了个人信息返回 true，否则返回 false。
     */
    private val isUserInfoChanged: Boolean
        get() = !(nicknameEdit.text.toString() == srcNickname
                && descriptionEdit.text.toString() == srcDescription
                && TextUtils.isEmpty(userAvatarPath)
                && TextUtils.isEmpty(userBgImagePath))


    /**
     * 判断用户昵称是否合法。用户昵称长度必须在2-30个字符之间，并且只能包含中英文、数字、下划线和横线。
     *
     * @return 昵称合法返回true，不合法返回false。
     */
    private fun validateNickname(): Boolean {
        val nickname = nicknameEdit.text.toString().trim { it <= ' ' }
        if (nickname.length < 2) {
            ToastUtils.showToast(this@ModifyUserInfoActivity, resources.getString(R.string.nickname_length_invalid))
            return false
        } else if (!nickname.matches(CommonUtils.NICK_NAME_REG_EXP.toRegex())) {
            ToastUtils.showToast(this@ModifyUserInfoActivity, resources.getString(R.string.nickname_invalid))
            return false
        }
        return true
    }

    private fun dialogSelect(action: Int) {
        val items = arrayOf<CharSequence>(getString(R.string.take_photo), getString(R.string.your_album))
        val builder = AlertDialog.Builder(this)
        AVATAR_OR_BGIMAGE = when (action) {
            TAKE_AVATAR_PICTURE -> {
                builder.setTitle(getString(R.string.select_avatar))
                0
            }
            TAKE_BG_IMAGE_PICTURE -> {
                builder.setTitle(getString(R.string.select_bg_image))
                1
            }
            else -> return
        }
        builder.setItems(items) { _, which ->
            when (which) {
                0 -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 判断大于 6.0 进行权限请求
                        onRuntimePermissionsAsk(arrayOf(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), object : PermissionListener {
                            override fun onGranted() {
                                takePhoto()
                            }

                            override fun onDenied(deniedPermissions: List<String>) {
                                showDialog()
                            }
                        })
                    } else {    // 低于 6.0 的时候直接访问就可以
                        takePhoto()
                    }
                }
                1 -> {
                    chooseFromAlbum()
                }
                else -> {
                }
            }
        }
        builder.show()
    }

    /**
     * 打开摄像头拍照 + 裁剪
     */
    private fun takePhoto() {
        imageFile = FileUtil.createImageFile()
        imageFile?.let {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imgUri = FileProvider.getUriForFile(this, ConstantConfig.FILE_AUTHORITY, it)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri)
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(it))
            }
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            intent.resolveActivity(packageManager)?.let {
                startActivityForResult(intent, REQUEST_CODE_CAPTURE)
            }
        }
    }

    // 裁剪
    private fun gotoCrop(sourceUri: Uri) {
        imageCropFile = FileUtil.createImageFile(true)
        imageCropFile?.let {
            val intent = Intent("com.android.camera.action.CROP")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)  //添加这一句表示对目标应用临时授权该 Uri 所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.setDataAndType(sourceUri, "image/*")  //设置数据源
            intent.putExtra("crop", "true")
            intent.putExtra("aspectX", 1)    //X方向上的比例
            intent.putExtra("aspectY", 1)    //Y方向上的比例
            intent.putExtra("outputX", 500)  //裁剪区的宽
            intent.putExtra("outputY", 500) //裁剪区的高
            intent.putExtra("scale ", true)  //是否保留比例
            intent.putExtra("return-data", false)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(it))
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CROP)
        }
    }

    /**
     *  从相册中选择图片 + 裁剪
     */
    private fun chooseFromAlbum() {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, REQUEST_CODE_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CAPTURE -> { //拍照成功后，裁剪
                    val sourceUri = FileProvider.getUriForFile(
                        this,
                        ConstantConfig.FILE_AUTHORITY,
                        imageFile!!
                    ) //通过FileProvider创建一个content类型的Uri
                    gotoCrop(sourceUri)
                }
                REQUEST_CODE_ALBUM -> { //从相册选择照片后，裁剪
                    data?.let {
                        gotoCrop(it.data)
                    }
                }
                REQUEST_CODE_CAPTURE_CROP -> {   //裁剪成功后，显示结果
                    if (AVATAR_OR_BGIMAGE == 0) {   // 选择头像图片
                        uploadAvatar(imageCropFile?.absolutePath!!)
                    } else if (AVATAR_OR_BGIMAGE == 1) {    // 选择背景图片
                        uploadBgImg(imageCropFile?.absolutePath!!)
                    }
                }
            }
        } else {
            LogUtils.d("tag 错误码 $resultCode")
        }
    }

    // 上传头像图片
    private fun uploadAvatar(path: String) {
        UploadUtils.uploadAvatar(App.getLoginUser()?.userid!!, path, object : UploadUtils.UploadImageListener {
            override fun uploadSuccess(user: LoginUser) {
                ToastUtils.showToast(this@ModifyUserInfoActivity, "上传成功")
                App.setLoginUser(user)  // 保存 loginUser
                userAvatar.setImageBitmap(BitmapFactory.decodeFile(path))
                LogUtils.e("上传成功")
            }

            override fun uploadFailed(msg: String) {
                ToastUtils.showToast(this@ModifyUserInfoActivity, "上传失败")
                LogUtils.e(msg)
            }
        })
    }

    //上传背景图片
    private fun uploadBgImg(path: String) {
        UploadUtils.uploadBgImg(App.getLoginUser()?.userid!!, path, object : UploadUtils.UploadListener {
            override fun uploadSuccess() {
                runOnUiThread {
                    userBgImage.setImageBitmap(BitmapFactory.decodeFile(path))
                }
                LogUtils.e("ModifyUserInfo 成功")
            }

            override fun uploadFailed(msg: String) {
                LogUtils.e("ModifyUserInfo" + msg)
            }
        })
    }

    private fun showDialog() {
        AlertDialog.Builder(this)
            .setTitle("提示")
            .setMessage("当前操作所需权限已被禁止。\n\n请点击\"设置\"-\"权限\"-打开所需权限。")
            .setNegativeButton("取消", null)
            .setPositiveButton("设置") { dialog, which ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .show()
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int = R.layout.activity_modify_user_info
}