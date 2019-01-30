package com.example.root.graduation_app.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Log
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.R
import com.example.root.graduation_app.utils.ConstantConfig
import com.example.root.graduation_app.utils.FileUtil
import com.example.root.graduation_app.utils.SimpleTextWatcher
import com.jaeger.library.StatusBarUtil
import kotlinx.android.synthetic.main.activity_modify_user_info.*
import java.io.File

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
        private var AVATAR_OR_BGIMAGE = -1     // 0 表示选择头像，1 表示选择背景图片

        private const val TAKE_AVATAR_PICTURE = 0
        private const val TAKE_BG_IMAGE_PICTURE = 1

        const val REQUEST_CODE_CAPTURE = 3
        const val REQUEST_CODE_CAPTURE_CROP = 4
        const val REQUEST_CODE_ALBUM = 5

//        val ROOT_DIR = Environment.getExternalStorageDirectory().toString() + File.separator + "project"
//        val PIC_TEMP = "$ROOT_DIR/temp.jpg"

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
        initEvent()
        // 输入昵称的监听
        nicknameEdit.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
            }
        })
        // 输入简介的监听
        descriptionEdit.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
            }
        })
    }

    private fun initEvent() {
        activity_modify_user_info_return.setOnClickListener {
            finish()
        }

        save.setOnClickListener {
            // 保存按钮
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
                0 -> takePhoto()
                1 -> chooseFromAlbum()
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
            intent.putExtra("crop", "true")
            intent.putExtra("aspectX", 1)    //X方向上的比例
            intent.putExtra("aspectY", 1)    //Y方向上的比例
            intent.putExtra("outputX", 500)  //裁剪区的宽
            intent.putExtra("outputY", 500) //裁剪区的高
            intent.putExtra("scale ", true)  //是否保留比例
            intent.putExtra("return-data", false)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.setDataAndType(sourceUri, "image/*")  //设置数据源

                val imgCropUri = Uri.fromFile(it)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imgCropUri) //设置输出  不需要ContentUri,否则失败
                LogUtils.d("tag 输入 $sourceUri")
                LogUtils.d("tag 输出 ${Uri.fromFile(it)}")
            } else {
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(it))
            }
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CROP)
        }
    }

    /**
     *  从相册中选择图片 + 裁剪
     */
    private fun chooseFromAlbum() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CAPTURE -> { //拍照成功后，裁剪
                    val sourceUri = FileProvider.getUriForFile(this, ConstantConfig.FILE_AUTHORITY, imageFile!!) //通过FileProvider创建一个content类型的Uri
                    gotoCrop(sourceUri)
                }
                REQUEST_CODE_ALBUM -> { //从相册选择照片后，裁剪
                    data?.let {
                        gotoCrop(it.data)
                    }
                }
                REQUEST_CODE_CAPTURE_CROP -> {   //裁剪成功后，显示结果
                    imageCropFile?.let {
                        if (AVATAR_OR_BGIMAGE == 0) {   // 选择头像图片
                            userAvatar.setImageBitmap(BitmapFactory.decodeFile(it.absolutePath))
                        } else if (AVATAR_OR_BGIMAGE == 1) {    // 选择背景图片
                            userBgImage.setImageBitmap(BitmapFactory.decodeFile(it.absolutePath))
                        }
                    }
                }
            }
        } else {
            LogUtils.d("tag 错误码 $resultCode")
        }
    }

    override fun loadData() {

    }

    override fun getLayoutId(): Int = R.layout.activity_modify_user_info
}