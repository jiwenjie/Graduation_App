package com.example.root.graduation_app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("ALL")
public class UnCaught implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler defaultUncaughtHandler;
    @SuppressLint("StaticFieldLeak")
    private static UnCaught unCaught;
    private Context context;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static UnCaught getInstance() {
        if (unCaught == null) {
            unCaught = new UnCaught();
        }
        return unCaught;
    }

    public void init(Context context) {
        this.context = context;
        defaultUncaughtHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handlerException(throwable) && defaultUncaughtHandler != null) {
            defaultUncaughtHandler.uncaughtException(thread, throwable);
        }
    }

    private boolean handlerException(Throwable throwable) {
        if (throwable == null || context == null) {
            return false;
        }

        final String crashReport = getCrashReport(context, throwable);
        Log.i("error", crashReport);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        save2File(crashReport);
                    }
                }
        ).start();
        return false;
    }

    private String getCrashReport(Context context, Throwable throwable) {
        PackageInfo pi = getPackageInfo(context);
        StringBuilder sb = new StringBuilder();
        sb.append("Version:").append(pi.versionName).append(" (").append(pi.versionCode).append(")\n");
        sb.append("Android:").append(Build.VERSION.RELEASE).append(" (").append(Build.MODEL).append(")\n");
        sb.append(getPrintStackTrace(throwable));
        return sb.toString();
    }

    private void save2File(String crashReport) {
        String time = format.format(new Date());
        String fileName = "crash-" + time + "-" + System.currentTimeMillis() + ".txt";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crash/";
            File fileDir = new File(filePath);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            try {
                FileWriter fileWriter = new FileWriter(new File(fileDir, fileName));
                fileWriter.write(crashReport);
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pi == null) {
            pi = new PackageInfo();
        }
        return pi;
    }

    private String getPrintStackTrace(Throwable throwable) {
        String err = "";
        Writer writer = null;
        PrintWriter pw = null;
        try {
            writer = new StringWriter();
            pw = new PrintWriter(writer);
            throwable.printStackTrace(pw);
            err = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (pw != null) {
                    pw.close();
                }
            }
        }
        return err;
    }
}
