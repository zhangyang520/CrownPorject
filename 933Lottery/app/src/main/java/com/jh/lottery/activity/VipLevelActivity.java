package com.jh.lottery.activity;

import com.jh.lottery.base.BaseWebViewActivity;

/**
 * Created by sangcixiang on 2018/8/7.
 */

public class VipLevelActivity extends BaseWebViewActivity {

    @Override
    public void initView() {
        super.initView();
        setTitle("用户等级说明");
        webView.loadUrl("file:///android_asset/vip_frame.html");
    }
}
