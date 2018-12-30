package com.example.administrator.chengnian444.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
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
    public static DbUtils dbUtils;

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
        //初始化数据库的设置
        initDataBase();
    }


    /**
     * 初始化数据库的设置
     */
    private void initDataBase() {
        //
        DbUtils.DaoConfig daoConfig=new DbUtils.DaoConfig(this);
        daoConfig.setDbName("baTv.db");
        daoConfig.setDbVersion(ConstantTips.dbVersion);
        daoConfig.setDbUpgradeListener(new DbUtils.DbUpgradeListener(){
            @Override
            public void onUpgrade(DbUtils dbUtils, int i, int i1) {

            }
        });
        //创建 dbUtils
        dbUtils= DbUtils.create(daoConfig);
        try {
            dbUtils.createTableIfNotExist(UserBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        dbUtils.configDebug(true);
    }

    private void initOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                      .connectTimeout(10000L, TimeUnit.MILLISECONDS) //链接超时
                    .readTimeout(10000L, TimeUnit.MILLISECONDS) //读取超时
                    .build(); //其他配置

             OkHttpUtils.initClient(okHttpClient);
            }



}
