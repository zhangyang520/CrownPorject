package com.jh.lottery.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import com.jh.lottery.adapter.HistoryAdapter;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.HistoryModel;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.HttpTool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/8/6.
 */

public class HistoryActivity extends BaseActivity {
    private ListView listView;
    private List<HistoryModel> models = new ArrayList<>();
    private HistoryAdapter adapter;
    private UserModel user;
    @Override
    public int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        user = UserModel.getUserInfo();
        loadData();
    }

    @Override
    public void initView() {

        showBackButton();
        setTitle("历史记录");

        listView = find(R.id.listView);
        adapter = new HistoryAdapter(this,models);
        listView.setAdapter(adapter);
    }


    private void loadData(){

        Map<String,String> map = new HashMap<>();
        map.put("acctBaseId",user.getAcctBaseId());
        HttpTool.GET(AppConfig.historyScoreRecordUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONArray jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<HistoryModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<HistoryModel>>(){}.getType());
                    models.addAll(list);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });


    }
}
