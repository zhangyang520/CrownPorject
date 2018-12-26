package com.jh.lottery.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import allen.com.rntestproject.R;
import com.jh.lottery.adapter.OpenPrizeAdapter;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.LotteryModel;
import com.jh.lottery.model.NewsModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.springview.DefaultHeader;
import com.jh.lottery.view.springview.SpringView;
import com.jh.lottery.view.XLoadingDialog;
import com.jh.lottery.view.XToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sangcixiang on 2018/8/2.
 */

public class OpenPrizeActivity extends BaseActivity {

    private ListView listView;
    private SpringView springView;
    private OpenPrizeAdapter adapter;
    private List<LotteryModel> models = new ArrayList<>();

    private String type = "";
    private String title = "";
    private ProgressBar progressBar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_open_prize;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        loadData();
    }

    @Override
    public void initView() {

        showBackButton();
        if (type.equals("all")){
            setTitle("最新开奖");
        }else {
            setTitle(title);
        }
        progressBar = find(R.id.progressBar);

        listView = find(R.id.listView);
        springView = find(R.id.springView);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                models.clear();
                loadData();
            }
            @Override
            public void onLoadmore() {}
        });
        springView.setHeader(new DefaultHeader(this));

        adapter = new OpenPrizeAdapter(this,models,type);
        listView.setAdapter(adapter);
    }

    private void loadData(){
        String url = "";
        Map<String,String> map = new HashMap<>();
        if (type.equals("all")){

            url = AppConfig.getOpenNumberUrl;
        }else {
            map.put("color",type);
            url = AppConfig.getLettoryNumberUrl;
        }

        HttpTool.GET(url, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                springView.onFinishFreshAndLoad();
                progressBar.setVisibility(View.GONE);
                springView.setVisibility(View.VISIBLE);
                setData(json);
            }

            @Override
            public void onError(String msg) {
                springView.onFinishFreshAndLoad();
                progressBar.setVisibility(View.GONE);
                springView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setData(JSONObject json){
        if (type.equals("all")){
            try {
                JSONObject data = json.getJSONObject("data");
                Iterator<String> keys = data.keys();
                List<LotteryModel> lettorys = new ArrayList<>();
                while (keys.hasNext()){
                    String key = keys.next();
                    LotteryModel model = new LotteryModel();
                    model.setLotteryCode(key);
                    model.setLotteryDate(data.getJSONObject(key).getString("lotteryOpenDate"));
                    model.setLotteryOpenNumber(data.getJSONObject(key).getString("lotteryOpenNumber"));
                    model.setLotteryPeriods(data.getJSONObject(key).getString("lotteryPeriods"));
                    lettorys.add(model);
                }
                models.addAll(lettorys);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            JSONArray jsonArray = null;
            try {
                jsonArray = json.getJSONArray("data");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                List<LotteryModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<LotteryModel>>(){}.getType());
                models.addAll(list);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
