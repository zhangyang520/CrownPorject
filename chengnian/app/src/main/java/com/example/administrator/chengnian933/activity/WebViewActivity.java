package com.example.administrator.chengnian933.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.base.BaseActivity;
import com.just.library.AgentWeb;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {


    @Bind(R.id.ll)
    LinearLayout mLinearLayout;
    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        mAgentWeb = AgentWeb.with(this)//传入Activity or Fragment
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams ,第一个参数和第二个参数应该对应。
                .useDefaultIndicator()// 使用默认进度条
                .setIndicatorColor(R.color.colorPrimaryDark)
                // .defaultProgressBarColor() // 使用默认进度条颜色
               // .setReceivedTitleCallback(mCallback)//标题回调
                .createAgentWeb()
                .ready()
                .go("http://203.189.234.147/home/help/index");
    }
    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onDestroy();

    }

}
