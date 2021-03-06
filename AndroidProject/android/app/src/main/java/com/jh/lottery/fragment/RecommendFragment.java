package com.jh.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import com.jh.lottery.activity.AnalysisDetailsActivity;
import com.jh.lottery.activity.CommunityActivity;
import com.jh.lottery.activity.DaShengDetailsActivity;
import com.jh.lottery.adapter.DaShenTopicAdapter;
import com.jh.lottery.adapter.DaShengHaderViewAdapter;
import com.jh.lottery.base.BaseFragement;
import com.jh.lottery.model.DaShengModel;
import com.jh.lottery.model.DashengTopicModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/7/26.
 */

public class RecommendFragment extends BaseFragement {

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
        return R.layout.fragment_dashen;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void initView() {
        progressBar = find(R.id.re_progressBar);
//        progressBar.setVisibility(View.GONE);
        listView = find(R.id.re_listView);
        adapter = new DaShenTopicAdapter(getActivity(), models, false);
        listView.setAdapter(adapter);
        listView.addHeaderView(headerView());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DashengTopicModel model = models.get(position);
                Intent intent = new Intent(getActivity(), AnalysisDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("model", model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        HttpTool.GET(AppConfig.getDaShengListUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
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
                listView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
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

    public View headerView() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dashen_headerview, null);
        dsjptj = view.findViewById(R.id.dsjptj);
        dsjptj.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.recycler_view);
        headerAdapter = new DaShengHaderViewAdapter(getActivity(), daShengModels);
        headerAdapter.setOnItemClickListener(new DaShengHaderViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DaShengModel model) {
                if (model.isDashen()) {
                    Intent intent = new Intent(getActivity(), DaShengDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("model", model);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), CommunityActivity.class);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(headerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.addItemDecoration(new SpacesItemDecoration(getActivity(), 1));
        return view;
    }
}
