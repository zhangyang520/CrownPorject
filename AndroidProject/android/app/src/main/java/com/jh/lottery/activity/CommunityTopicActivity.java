package com.jh.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import allen.com.rntestproject.R;
import com.jh.lottery.adapter.CommunityTopicAdapter;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.BlockModel;
import com.jh.lottery.model.TopicModel;
import com.jh.lottery.utils.HttpTool;

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

public class CommunityTopicActivity extends BaseActivity {
    private ListView listView;
    private BlockModel model;
    private CommunityTopicAdapter adapter;
    private List<TopicModel> models = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_community_topic;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        model = (BlockModel) getIntent().getSerializableExtra("model");
        loadData();
    }

    @Override
    public void initView() {
        showBackButton();
        setTitle(model.getPlateName());
        listView = find(R.id.listView);
        adapter = new CommunityTopicAdapter(this,models);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CommunityTopicActivity.this,CommunityTopicDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("model",models.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadData(){

        Map<String,String> map = new HashMap<>();
        map.put("plateCode",model.getPlateCode()+"");

        HttpTool.GET(AppConfig.getTopicListUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<TopicModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<TopicModel>>(){}.getType());
                    models.addAll(list);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {

                }
            }
        });
    }
}
