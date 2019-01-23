package com.jh.lottery.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.NewsModel;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/8/6.
 */

public class NewsDetailsActivity extends BaseActivity {
    private TextView title;
    private TextView content;
    private NewsModel model;
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        model = (NewsModel) getIntent().getSerializableExtra("model");
    }

    @Override
    public void initView() {
        showBackButton();
        title = find(R.id.title);
        content = find(R.id.content);

        title.setText(model.getNewsTitle());
        content.setText(model.getNewsContent());
    }
}
