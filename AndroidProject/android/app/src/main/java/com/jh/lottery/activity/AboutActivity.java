package com.jh.lottery.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.base.BaseWebViewActivity;

/**
 * Created by sangcixiang on 2018/8/7.
 */

public class AboutActivity extends BaseWebViewActivity {

    @Override
    public void initView() {
        super.initView();
        setTitle("关于我们");
        webView.loadUrl("file:///android_asset/about_frame.html");
    }
}
