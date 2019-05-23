package com.example.root.graduation_app.utils

import android.annotation.SuppressLint
import com.alibaba.fastjson.util.JavaBeanInfo.build
import com.example.base_library.RetrofitManager
import com.example.base_library.base_utils.LogUtils
import com.example.root.graduation_app.base.api.JacksonApi
import com.example.root.graduation_app.bean.LoginUser
import okhttp3.*
import java.io.File
import java.io.IOException
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

    // 上传背景图片的方法
    fun uploadBgImg(userid: String, imgPath: String, listener: UploadListener) {
        var file: File? = null
        try {
            file = File(imgPath)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        val mOkHttpClent = OkHttpClient()
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("userid", userid)  // 上传参数
            .addFormDataPart(
                "bgImg", file?.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), file!!)
            )   // 上传文件
            .build()

        val request = Request.Builder()
            .url(ConstantConfig.JACKSON_BASE_URL + "phoneUser/uploadBgImg")
            .post(requestBody)
            .build()
        val call = mOkHttpClent.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                LogUtils.e("yyy" + e.message)
                listener.uploadFailed(e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    listener.uploadSuccess()
                } else {
                    listener.uploadSuccess()
                    LogUtils.e("tttttt" + response.code() + response.message())
                }
            }
        })
    }

    interface UploadImageListener {
        fun uploadSuccess(user: LoginUser)
        fun uploadFailed(msg: String)
    }

    interface UploadListener {
        fun uploadSuccess()
        fun uploadFailed(msg: String)
    }
}