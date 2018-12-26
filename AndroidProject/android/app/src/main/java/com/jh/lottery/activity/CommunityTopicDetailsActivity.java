package com.jh.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import allen.com.rntestproject.R;


import com.jh.lottery.adapter.TopicReplyAdapter;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.TopicModel;
import com.jh.lottery.model.TopicReplyModel;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.utils.KeyboardStatusDetector;
import com.jh.lottery.utils.XDateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sangcixiang on 2018/8/4.
 */

public class CommunityTopicDetailsActivity extends BaseActivity {
    private ListView listView;
    private ImageView avatar;
    private TextView name;
    private TextView timer;
    private TextView title;
    private TextView content;
    private TopicModel model;
    private List<TopicReplyModel> models = new ArrayList<>();
    private TopicReplyAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_topic_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        model = (TopicModel) getIntent().getSerializableExtra("model");
        loadData();

    }

    @Override
    public void initView() {

        showBackButton();
        setTitle("详情");
        setRightTitle("回复");

        listView = find(R.id.listView);
        adapter = new TopicReplyAdapter(this,models);
        listView.setAdapter(adapter);
        listView.setVerticalScrollBarEnabled(false);
        listView.addHeaderView(headerView());


        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserModel.getUserInfo() != null){
                    Intent intent = new Intent(CommunityTopicDetailsActivity.this,CommunityReplyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("model",model);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(CommunityTopicDetailsActivity.this,LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    private View headerView(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_topic_details_header_view,null);
        avatar = view.findViewById(R.id.avatar);
        name = view.findViewById(R.id.name);
        timer = view.findViewById(R.id.timer);
        title = view.findViewById(R.id.title);
        content = view.findViewById(R.id.content);
        name.setText(model.getAdminName());
        title.setText(model.getTitle());
        content.setText(model.getContent());
        timer.setText(XDateUtils.millis2String(model.getCreateTime(),"yyyy-MM-dd HH:mm"));
        return view;
    }

    @Override
    protected void onResume() {
        super.onResume();
        models.clear();
        loadData();
    }

    private void loadData(){

        Map<String,String> map = new HashMap<>();
        map.put("invitationId",model.getId()+"");

        HttpTool.GET(AppConfig.getReplyListUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<TopicReplyModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<TopicReplyModel>>(){}.getType());
                    models.addAll(list);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {

                }
            }
        });

    }
}
