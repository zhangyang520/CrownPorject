package com.jh.lottery.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.jh.lottery.AppConfig;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.XToast;
import com.rnandroidsdk.util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import allen.com.rntestproject.MainActivity;
import allen.com.rntestproject.R;
import allen.com.rntestproject.main.AppMainActivity;

public class LaunchActivity extends BaseActivity {

//    private Handler handler = new Handler();

    @Override
    public int getLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

        Boolean isUPdate = (Boolean) SPUtils.get(getApplicationContext(),SPUtils.UPADTE_FLAG,false);
        if (isUPdate == true){
            JumpToActivity(MainActivity.class);
            finish();
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("status",getLocalVersionName(this));
        map.put("fileName",this.getPackageName());
        HttpTool.GET(AppConfig.appUpdateUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONObject data = json.getJSONObject("data");
                    if (data.getBoolean("isUpdate")){
                        JumpToActivity(MainActivity.class);
                    }else {
                        JumpToActivity(AppMainActivity.class);
                    }
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    JumpToActivity(AppMainActivity.class);
                    finish();
                }
            }
            @Override
            public void onError(String msg) {
                JumpToActivity(AppMainActivity.class);
                finish();
            }
        });
    }



    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
