package com.example.root.graduation_app.utils;

import kotlin.jvm.JvmStatic;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2019/01/21
 * desc:
 * version:1.0
 */
public class HttpUtils {

    @JvmStatic
    public static void sendRequestWidthOkHttp(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);      //异步
    }
    /**
     * 异步请求
     * @param address
     * @param callback
     */
    public static void getRequestOkHttp(String address, RequestBody body, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(address)
                .post(body).build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 同步请求
     * @param address
     * @return
     */
    public static String sendRequestWithOkhttp(String address) {
        OkHttpClient client=new OkHttpClient();
        String message= null;
        try {
            Request request = new Request.Builder().url(address) .build();
            Response response= client.newCall(request).execute();
            assert response.body() != null;
            message = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
