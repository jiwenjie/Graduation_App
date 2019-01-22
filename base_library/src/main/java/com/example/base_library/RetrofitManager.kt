@file:Suppress("DEPRECATION")

package com.example.base_library

import android.text.TextUtils
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *  author:Jiwenjie
 *  email:278630464@qq.com
 *  time:2018/12/14
 *  desc:retrofit 单例生成管理类
 *  version:1.0
 */
object RetrofitManager {

    private val TAG = RetrofitManager.javaClass.simpleName  // RetrofitManager
    private const val CONN_TIME = 5
    private const val READ_TIME = 10
    private const val WRITE_TIME = 30
    private var BASE_URL: String = ""

    private var mRetrofits: Retrofit? = null
    private var url: String? = null

    /**
     * 需要在调用 mRetrofit 之前必须优先调用设置请求的服务器地址
     */
    val mRetrofit: Retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        if (TextUtils.isEmpty(BASE_URL))
            throw IllegalStateException("Empty url and you should setBaseUrl before get retrofit")

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkClient())
            .build()
    }

    /**
     * 在调用请求前该项需要先设置
     */
    fun setBaseUrl(url: String) {
        BASE_URL = url
    }

    /**
     * 设置 okHttpClient 的基本设置并返回
     */
    private fun getOkClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor {
            HttpLoggingInterceptor.Logger { message -> Log.i(TAG, message) }
        }

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            //SSL证书
            .sslSocketFactory(TrustManager.getUnsafeOkHttpClient())
            .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
            .connectTimeout(CONN_TIME.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIME.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIME.toLong(), TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    /**
     * 设置公共参数，有些网站的请求可能用到
     */
//    private object addQueryParameterInterceptor: Interceptor {
//        override fun intercept(chain: Interceptor.Chain): Response {
//
//        }
//    }

    fun provideClient(baseUrl: String): Retrofit {
        if (!TextUtils.equals(url, baseUrl) && mRetrofits != null) {
            mRetrofits = null
            url = baseUrl
        }

        if (mRetrofits == null) {
            synchronized(RetrofitManager::class.java) {
                if (mRetrofits == null) {
                    mRetrofits = Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(getOkClient())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
            }
        }
        return mRetrofits!!
    }

}








