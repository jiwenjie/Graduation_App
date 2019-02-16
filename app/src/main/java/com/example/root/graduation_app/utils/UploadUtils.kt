package com.example.root.graduation_app.utils

import android.annotation.SuppressLint
import android.net.Uri
import com.example.base_library.RetrofitManager
import com.example.base_library.base_utils.LogUtils
import com.example.base_library.base_views.BaseActivity
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.LoginUser
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.URI
import java.net.URISyntaxException

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2019/02/09
 *  desc:上传文件图片的工具类
 *  version:1.0
 */
object UploadUtils {

    const val TYPE_AVATAR = 12036   // 头像标志
    const val TYPE_BGIMAGE = 12031  // 背景图片标志

    @SuppressLint("CheckResult")
    fun uploadAvatar(uri: Uri, listener: UploadImageListener) {   // true 为上传成功，false 为上传失败
        var file: File? = null
        try {
            file = File(URI(uri.toString()))
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file!!)
//        val requestBody = RequestBody.create(MediaType.parse("image/png"), file!!)
        val body = MultipartBody.Part.createFormData("image", file.name, requestBody)
        LogUtils.e("UploadUtils" + file.name)
        LogUtils.e("UploadUtils" + file.absolutePath)

//        RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
//            .create(JacksonApi::class.java)
//            .uploadAvatar(body)
//            .compose(RxJavaUtils.applyObservableAsync())
//            .subscribe({
//                LogUtils.e("UploadUtils" + it.string())
//                listener.uploadSuccess(TYPE_AVATAR)
//            }, {
//                LogUtils.e("UploadUtils$it")
//                listener.uploadFailed(TYPE_AVATAR)
//            })
    }

    @SuppressLint("CheckResult")
    fun uploadAvatar(userid: String, imgPath: String, listener: UploadImageListener) {   // true 为上传成功，false 为上传失败
        var file: File? = null
        try {
            file = File(imgPath)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file!!)
        val body = MultipartBody.Part.createFormData("image", file.name, requestBody)   // 这里的 key 要和后台接收的 body 名字相同才行
        LogUtils.e("UploadUtils" + file.name)
        LogUtils.e("UploadUtils" + file.absolutePath)

        RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
            .create(JacksonApi::class.java)
            .uploadAvatar(userid, body)
            .compose(RxJavaUtils.applyObservableAsync())
            .subscribe({
                if (it.result == "succeed") {
                    listener.uploadSuccess(it.data)
                } else {
                    listener.uploadFailed(it.msg)
                }

            }, {
                listener.uploadFailed(it.message.toString())
            })
    }

    interface UploadImageListener {

        fun uploadSuccess(user: LoginUser)

        fun uploadFailed(msg: String)
    }
}