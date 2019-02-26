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

        /**
         * 参数和图片一起上传的时候两种方式，这是第一种，第二种是使用 query 关键字
          */
        val idBody = RequestBody.create(MediaType.parse("multipart/form-data"), userid)
        LogUtils.e("UploadUtils" + file.name)
        LogUtils.e("UploadUtils" + file.absolutePath)

        RetrofitManager.provideClient(ConstantConfig.JACKSON_BASE_URL)
            .create(JacksonApi::class.java)
            .uploadAvatar(idBody, body)
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

    @SuppressLint("CheckResult")
    fun uploadMusic(userid: String, imgPath: String, listener: UploadMusicListener) {   // true 为上传成功，false 为上传失败

    }

    interface UploadImageListener {
        fun uploadSuccess(user: LoginUser)
        fun uploadFailed(msg: String)
    }

    interface UploadMusicListener {
        fun uploadSuccess()
        fun uploadFailed(msg: String)
    }
}