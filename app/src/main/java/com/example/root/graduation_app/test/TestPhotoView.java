package com.example.root.graduation_app.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.root.graduation_app.R;

import com.example.root.graduation_app.utils.ConstantConfig;
import com.example.root.graduation_app.utils.UploadUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/28
 * desc: test photoView
 * version:1.0
 */
public class TestPhotoView extends AppCompatActivity {

    private ViewPager viewPager;
    //   private PhotoSlideAdapter adapter;
    WebView webView;

    private TextView count;

    private List<String> list;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        count = findViewById(R.id.tvCount);

//        radioGroup.check(2);
        list = new ArrayList<>();
        list.add(null);
        list.add(null);
        list.add(null);
        count.setText(String.valueOf(list.size()));

        int[] int_array = new int[3];
    }

    public static boolean isMobileNO(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表下一位为数字可以是几，"[0-9]"代表可以为0-9中的一个，"[5,7,9]"表示可以是5,7,9中的任意一位,[^4]表示除4以外的任何一个,\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    public <T> ObservableTransformer<T, T> applyObservableAsync() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    // 上传背景图片的方法
    public static void uploadBgImg(String userid, String imgPath, final UploadUtils.UploadListener listener) {
        File file = null;
        try {
            file = new File(imgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OkHttpClient mOkHttpClent = new OkHttpClient();
        assert file != null;
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userid", userid)  // 上传参数
                .addFormDataPart(
                        "bgImg", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file)
                )   // 上传文件
                .build();

        Request request = new Request.Builder()
                .url(ConstantConfig.JACKSON_BASE_URL + "phoneUser/uploadBgImg")
                .post(requestBody)
                .build();
        Call call = mOkHttpClent.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                listener.uploadFailed(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful())
                    listener.uploadSuccess();
            }
        });
    }
}
