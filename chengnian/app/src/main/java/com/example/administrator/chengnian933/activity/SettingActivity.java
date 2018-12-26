package com.example.administrator.chengnian933.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.chengnian933.MainActivity;
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.base.BaseActivity;
import com.example.administrator.chengnian933.utils.FileCacheUtils;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.example.administrator.chengnian933.utils.StatusBarCompat.StatusBarCompat;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.modify_pwd)
    RelativeLayout modifyPwd;
    @Bind(R.id.size)
    TextView size;
    @Bind(R.id.huanchun)
    RelativeLayout huanchun;
    @Bind(R.id.btn_exit)
    Button btnExit;
    private String sdPath;
    private File outFilePath;
    private File outCachePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.black));
        if (!SPUtils.getInstance(this).getBoolean("isLogin")){
            btnExit.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.back, R.id.modify_pwd, R.id.huanchun, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.modify_pwd:
                if (SPUtils.getInstance(this).getBoolean("isLogin")){
                    startActivity(new Intent(this,UpdateActivity.class));
                }else {
                    startActivity(new Intent(this,LoginActivity.class));
                }

                break;
            case R.id.huanchun:
                FileCacheUtils.deleteFile(getCacheDir(), true);
                initData();
                break;
            case R.id.btn_exit:
                SPUtils.getInstance(this).put("isLogin",false);
                startActivity(new Intent(this, MainActivity.class));

                break;
        }
    }

    private void initData() {
        //获取缓存大小
        sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        outCachePath = getCacheDir();
        outFilePath = getExternalFilesDir(Environment.DIRECTORY_ALARMS);
        try {
            String outCacheSize = FileCacheUtils.getCacheSize(outCachePath);
            String outFileSize = FileCacheUtils.getCacheSize(outFilePath);

            size.setText(outCacheSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
