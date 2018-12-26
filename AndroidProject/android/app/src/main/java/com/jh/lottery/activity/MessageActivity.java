package com.jh.lottery.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import allen.com.rntestproject.R;
import com.jh.lottery.adapter.MessageAdapter;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.MessageModel;
import com.jh.lottery.model.NewsModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.XLoadingDialog;
import com.jh.lottery.view.XToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity {

    private ListView listView;
    private List<MessageModel> models = new ArrayList<>();
    private MessageAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        showBackButton();
        setTitle("站内信息");
        listView = find(R.id.listView);
        adapter = new MessageAdapter(this,models);
        listView.setAdapter(adapter);
        loadData();
    }


    private void loadData(){


        HttpTool.GET(AppConfig.getLetterListUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {

                try {
                    JSONArray jsonArray = json.getJSONObject("data").getJSONArray("noticeList");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<MessageModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<MessageModel>>(){}.getType());
                    models.addAll(list);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
