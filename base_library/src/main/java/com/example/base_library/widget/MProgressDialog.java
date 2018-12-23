package com.example.base_library.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.base_library.R;
import com.example.base_library.base_utils.ScreenUtils;
import com.example.base_library.widget.config.MDialogConfig;

import java.util.Objects;

/**
 * author:Jiwenjie
 * email:278630464@qq.com
 * time:2018/12/23
 * desc:
 * version:1.0
 */
public class MProgressDialog {

   private static final String LoadingDefaultMsg = "加载中";

   private static Dialog mDialog;
   private static MDialogConfig mDialogConfig;
   //布局
   @SuppressLint("StaticFieldLeak")
   private static RelativeLayout dialog_window_background;
   @SuppressLint("StaticFieldLeak")
   private static RelativeLayout dialog_view_bg;
   private static MNHudProgressWheel progress_wheel;
   @SuppressLint("StaticFieldLeak")
   private static TextView tv_show;

   private static void initDialog(Context mContext) {
      LayoutInflater inflater = LayoutInflater.from(mContext);
      View mProgressDialogView = inflater.inflate(R.layout.dialog_progress_layout, null);// 得到加载view
      mDialog = new Dialog(mContext, R.style.MNCustomDialog);// 创建自定义样式dialog
      mDialog.setCancelable(false);// 不可以用“返回键”取消
      mDialog.setCanceledOnTouchOutside(false);
      mDialog.setContentView(mProgressDialogView);// 设置布局

      //设置整个Dialog的宽高
      Resources resources = mContext.getResources();
      DisplayMetrics dm = resources.getDisplayMetrics();
      int screenW = dm.widthPixels;
      int screenH = dm.heightPixels;

      WindowManager.LayoutParams layoutParams = Objects.requireNonNull(mDialog.getWindow()).getAttributes();
      layoutParams.width = screenW;
      layoutParams.height = screenH;
      mDialog.getWindow().setAttributes(layoutParams);
      //布局相关
      dialog_window_background = mProgressDialogView.findViewById(R.id.dialog_window_background);
      dialog_view_bg = mProgressDialogView.findViewById(R.id.dialog_view_bg);
      progress_wheel = mProgressDialogView.findViewById(R.id.progress_wheel);
      tv_show = mProgressDialogView.findViewById(R.id.tv_show);
      progress_wheel.spin();

      configView(mContext);
   }

   private static void configView(Context mContext) {
      if (mDialogConfig == null) {
         mDialogConfig = new MDialogConfig.Builder().build();
      }
      //设置动画
      if (mDialogConfig.animationID != 0 && mDialog.getWindow() != null) {
         try {
            mDialog.getWindow().setWindowAnimations(mDialogConfig.animationID);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      //点击外部可以取消
      mDialog.setCanceledOnTouchOutside(mDialogConfig.canceledOnTouchOutside);
      //window背景色
      dialog_window_background.setBackgroundColor(mDialogConfig.backgroundWindowColor);
      //弹框背景
      GradientDrawable myGrad = new GradientDrawable();
      myGrad.setColor(mDialogConfig.backgroundViewColor);
      myGrad.setStroke(ScreenUtils.dip2px(mContext, mDialogConfig.strokeWidth), mDialogConfig.strokeColor);
      myGrad.setCornerRadius(ScreenUtils.dip2px(mContext, mDialogConfig.cornerRadius));
      dialog_view_bg.setBackground(myGrad);
      dialog_view_bg.setPadding(
              ScreenUtils.dip2px(mContext, mDialogConfig.paddingLeft),
              ScreenUtils.dip2px(mContext, mDialogConfig.paddingTop),
              ScreenUtils.dip2px(mContext, mDialogConfig.paddingRight),
              ScreenUtils.dip2px(mContext, mDialogConfig.paddingBottom)
      );
      //Progress设置
      progress_wheel.setBarColor(mDialogConfig.progressColor);
      progress_wheel.setBarWidth(ScreenUtils.dip2px(mContext, mDialogConfig.progressWidth));
      progress_wheel.setRimColor(mDialogConfig.progressRimColor);
      progress_wheel.setRimWidth(mDialogConfig.progressRimWidth);
      //文字颜色设置
      tv_show.setTextColor(mDialogConfig.textColor);
      tv_show.setTextSize(mDialogConfig.textSize);
      //点击事件
      dialog_window_background.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            //取消Dialog
            if (mDialogConfig != null && mDialogConfig.canceledOnTouchOutside) {
               dismissProgress();
            }
         }
      });
   }

   public static void showProgress(Context context) {
      showProgress(context, LoadingDefaultMsg);
   }

   public static void showProgress(Context context, String msg) {
      showProgress(context, msg, null);
   }

   public static void showProgress(Context context, MDialogConfig mDialogConfig) {
      showProgress(context, LoadingDefaultMsg, mDialogConfig);
   }

   public static void showProgress(Context context, String msg, MDialogConfig dialogConfig) {
      try {
         dismissProgress();
         //设置配置
         if (dialogConfig == null) {
            dialogConfig = new MDialogConfig.Builder().build();
         }
         mDialogConfig = dialogConfig;

         initDialog(context);
         if (mDialog != null && tv_show != null) {
            if (TextUtils.isEmpty(msg)) {
               tv_show.setVisibility(View.GONE);
            } else {
               tv_show.setVisibility(View.VISIBLE);
               tv_show.setText(msg);
            }
            mDialog.show();
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static void dismissProgress() {
      try {
         if (mDialog != null && mDialog.isShowing()) {
            //判断是不是有监听
            if (mDialogConfig.onDialogDismissListener != null) {
               mDialogConfig.onDialogDismissListener.onDismiss();
               mDialogConfig.onDialogDismissListener = null;
            }
            mDialogConfig = null;
            dialog_window_background = null;
            dialog_view_bg = null;
            progress_wheel = null;
            tv_show = null;
            mDialog.dismiss();
            mDialog = null;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static boolean isShowing() {
      if (mDialog != null) {
         return mDialog.isShowing();
      }
      return false;
   }
}

