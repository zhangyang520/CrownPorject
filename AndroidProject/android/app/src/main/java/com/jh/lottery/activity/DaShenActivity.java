package com.jh.lottery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import allen.com.rntestproject.R;
import com.jh.lottery.adapter.DaShenTopicAdapter;
import com.jh.lottery.adapter.DaShengHaderViewAdapter;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.DaShengModel;
import com.jh.lottery.model.DashengTopicModel;
import com.jh.lottery.model.NewsModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.DaShenHeaderView;
import com.jh.lottery.view.SpacesItemDecoration;
import com.jh.lottery.view.XLoadingDialog;
import com.jh.lottery.view.XToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sangcixiang on 2018/8/2.
 */

public class DaShenActivity extends BaseActivity {

    private ListView listView;
    private RecyclerView recyclerView;
    private DaShenTopicAdapter adapter;
    private TextView dsjptj;
    private DaShengHaderViewAdapter headerAdapter;
    private List<DashengTopicModel> models = new ArrayList<>();
    private List<DaShengModel> daShengModels = new ArrayList<>();
    private ProgressBar progressBar;
    @Override
    public int getLayoutId() {
        return R.layout.activity_dashen;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void initView() {
        showBackButton();
        setTitle("大神推荐");
        progressBar = find(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        listView = find(R.id.listView);
        adapter = new DaShenTopicAdapter(this,models,false);
        listView.setAdapter(adapter);
        listView.addHeaderView(headerView());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DashengTopicModel model = models.get(position);
                Intent intent = new Intent(DaShenActivity.this,AnalysisDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("model",model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public View headerView(){

        View view = LayoutInflater.from(this).inflate(R.layout.layout_dashen_headerview,null);
        dsjptj = view.findViewById(R.id.dsjptj);
        dsjptj.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recycler_view);
        headerAdapter = new DaShengHaderViewAdapter(this,daShengModels);
        headerAdapter.setOnItemClickListener(new DaShengHaderViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DaShengModel model) {
                if (model.isDashen()){
                    Intent intent = new Intent(DaShenActivity.this,DaShengDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("model",model);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(DaShenActivity.this,CommunityActivity.class);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(headerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new SpacesItemDecoration(this,1));
        return view;
    }


    private void loadData(){

        HttpTool.GET(AppConfig.getDaShengListUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONArray jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<DashengTopicModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<DashengTopicModel>>(){}.getType());
                    models.addAll(list);
                    dsjptj.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        HttpTool.GET(AppConfig.getDashengPersonUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                try {

                    JSONArray jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<DaShengModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<DaShengModel>>(){}.getType());
                    daShengModels.addAll(list);

                    DaShengModel model = new DaShengModel();
                    model.setHeadImageUrl("");
                    model.setIsDashen(false);
                    model.setGodName("论坛社区");
                    daShengModels.add(model);

                    headerAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }
        });

    }


}
