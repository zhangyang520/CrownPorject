package com.jh.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import allen.com.rntestproject.R;
import com.jh.lottery.activity.NewsDetailsActivity;
import com.jh.lottery.adapter.NewsAdapter;
import com.jh.lottery.base.BaseFragement;
import com.jh.lottery.model.NewsModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.springview.DefaultFooter;
import com.jh.lottery.view.springview.DefaultHeader;
import com.jh.lottery.view.springview.SpringView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jh.lottery.AppConfig.getNewsListUrl;

/**
 * Created by sangcixiang on 2018/7/26.
 */

public class NewsFragment extends BaseFragement{

    private ListView listView;
    private SpringView springView;
    private NewsAdapter adapter;
    private List<NewsModel> models = new ArrayList<>();
    private int page = 1;
    private ProgressBar progressBar;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void initView() {
        progressBar = find(R.id.progressBar);
        listView = find(R.id.listView);
        springView = find(R.id.springView);
        springView.setType(SpringView.Type.FOLLOW);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                models.clear();
                page = 1;
                loadData();
            }

            @Override
            public void onLoadmore() {
                page ++;
                loadData();
            }
        });
        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        adapter = new NewsAdapter(getActivity(),models);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(),NewsDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("model",models.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        loadData();
    }

    private void loadData(){
        Map<String,String> map = new HashMap<>();
        map.put("page",page+"");
        HttpTool.GET(getNewsListUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                springView.setVisibility(View.VISIBLE);
                springView.onFinishFreshAndLoad();
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<NewsModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<NewsModel>>(){}.getType());
                    models.addAll(list);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
                springView.setVisibility(View.VISIBLE);
                springView.onFinishFreshAndLoad();
                progressBar.setVisibility(View.GONE);
            }
        });

    }


}
