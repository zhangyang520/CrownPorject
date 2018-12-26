package com.example.administrator.chengnian933.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian933.MainActivity;
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.base.BaseActivity;
import com.example.administrator.chengnian933.base.MyApplication;
import com.example.administrator.chengnian933.bean.AuthBean;
import com.example.administrator.chengnian933.http.Constant;
import com.example.administrator.chengnian933.utils.CommonUtils;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.example.administrator.chengnian933.utils.StatusBarCompat.StatusBarCompat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SplashActivity extends BaseActivity {
    @Bind(R.id.iv_shlash)
    ImageView ivShlash;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
       /* StatusBarCompat.translucentStatusBar(this);
        int statusBarHeight = CommonUtils.getStatusBarHeight(this);
        ivShlash.setPadding(0, statusBarHeight, 0, 0);*/
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.black));
        if (isNetworkConnected(this)) {
            getHttpToken();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }

    }

    private void getHttpToken() {
        OkHttpUtils.post()
                .url(Constant.TOKEN)
                .addParams("userName", "admin")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //Log.d("hcy",e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("hcy",response.toString());

                        AuthBean authBean = JSON.parseObject(response, AuthBean.class);
                        String token = authBean.getToken();
                        SPUtils.getInstance(SplashActivity.this).put("token", "Bearer " + token);
                    }
                });
    }

    Runnable goNextActivityRunnable = new Runnable() {
        @Override
        public void run() {
            goNextActivity();
        }
    };

    private void goNextActivity() {
        //是否已经进入过主界面（默认没有进入过）
        boolean isEnterMain = SPUtils.getInstance(this).getBoolean("isEnterMain", false);
        if (isEnterMain) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, GuideActivity.class));
            SPUtils.getInstance(this).put("isEnterMain", true);
        }
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyApplication.handler.postDelayed(goNextActivityRunnable, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.handler.removeCallbacks(goNextActivityRunnable);
    }

}
