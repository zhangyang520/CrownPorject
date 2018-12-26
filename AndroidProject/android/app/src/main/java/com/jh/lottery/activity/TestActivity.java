package com.jh.lottery.activity;

import android.os.Bundle;
import android.view.View;

import allen.com.rntestproject.MainActivity;
import allen.com.rntestproject.R;
import com.jh.lottery.base.BaseActivity;

/**
 * Created by sangcixiang on 2018/7/31.
 */

public class TestActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

    }

    public void update(View view){
        JumpToActivity(MainActivity.class);
    }
}
