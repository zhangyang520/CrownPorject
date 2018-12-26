package com.jh.lottery.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.DashengTopicModel;
import com.jh.lottery.utils.XDateUtils;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/8/3.
 */

public class AnalysisDetailsActivity extends BaseActivity {
    private TextView title;
    private TextView timer;
    private TextView content;
    private DashengTopicModel model;
    @Override
    public int getLayoutId() {
        return R.layout.activity_analys_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        model = (DashengTopicModel) getIntent().getSerializableExtra("model");
    }

    @Override
    public void initView() {

        showBackButton();
        setTitle("大神推荐");

        title = find(R.id.title);
        timer = find(R.id.timer);
        content = find(R.id.content);

        title.setText(model.getManitoArticleTitle());
        timer.setText(XDateUtils.millis2String(model.getManitoArticleCreateDate(),"yyyy-MM-dd HH:mm:ss"));
        content.setText(model.getManitoArticleContent());
    }
















}
