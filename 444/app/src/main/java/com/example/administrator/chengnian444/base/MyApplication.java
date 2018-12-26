package com.example.administrator.chengnian444.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * Created by Administrator on 2018/8/26.
 */

public class MyApplication extends Application {
    public static Context context;
    public static Handler handler;
    public static Thread mainThread;
    public static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        context = getApplicationContext();
        //当前线程
        mainThread = Thread.currentThread();
        //当前线程ID
        mainThreadId = android.os.Process.myTid();
        initOkHttpClient();
    }

    private void initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                      .connectTimeout(10000L, TimeUnit.MILLISECONDS) //链接超时
                    .readTimeout(10000L, TimeUnit.MILLISECONDS) //读取超时
                    .build(); //其他配置

             OkHttpUtils.initClient(okHttpClient);
            }



}
