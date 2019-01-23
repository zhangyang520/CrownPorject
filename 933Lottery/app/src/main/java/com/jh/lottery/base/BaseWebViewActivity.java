package com.jh.lottery.base;

import android.os.Bundle;
import android.webkit.WebView;

import allen.com.rntestproject.R;


/**
 * Created by sangcixiang on 2018/8/7.
 */

public class BaseWebViewActivity extends BaseActivity {
    protected WebView webView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        showBackButton();
        webView = find(R.id.webView);
        webView.loadUrl("file:///android_asset/about_frame.html");
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setJavaScriptEnabled(true);
    }


}
