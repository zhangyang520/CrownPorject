package com.jh.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.jh.lottery.utils.GlideUtils;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sangcixiang on 2018/8/3.
 */

public class DaShengDetailsActivity extends BaseActivity {
    private ImageView avatar;
    private TextView name;
    private TextView details;
    private LinearLayout starts;
    private ListView listView;
    private DaShenTopicAdapter adapter;
    private List<DashengTopicModel> models = new ArrayList<>();
    private DaShengModel daShengModel;
    @Override
    public int getLayoutId() {
        return R.layout.activity_dasheng_details;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        daShengModel = (DaShengModel) getIntent().getSerializableExtra("model");
        loadData();
    }

    @Override
    public void initView() {

        showBackButton();
        setTitle("大神推荐");

        listView = find(R.id.listView);
        adapter = new DaShenTopicAdapter(this,models,true);
        listView.setAdapter(adapter);
        listView.addHeaderView(headerView());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DashengTopicModel model = models.get(position);
                Intent intent = new Intent(DaShengDetailsActivity.this,AnalysisDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("model",model);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadData(){
        Map<String,String> map = new HashMap<>();
        map.put("graspid",daShengModel.getId()+"");
        HttpTool.GET(AppConfig.getDsDetailsUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONArray jsonArray = json.getJSONObject("data").getJSONArray("manitoArticleBases");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<DashengTopicModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<DashengTopicModel>>(){}.getType());
                    models.addAll(list);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public View headerView(){

        View view = LayoutInflater.from(this).inflate(R.layout.layout_dashen_details,null);
        avatar = view.findViewById(R.id.avatar);
        name = view.findViewById(R.id.name);
        details = view.findViewById(R.id.details);
        starts = view.findViewById(R.id.starts);

        GlideUtils.loadAvatar(this,daShengModel.getHeadImageUrl(),avatar);
        name.setText(daShengModel.getGodName());
        details.setText(daShengModel.getGodIntro());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 25);
        layoutParams.setMargins(0,0,5,0);
        for(int i=0;i<daShengModel.getGodStar();i++){
            ImageView start = new ImageView(this);
            start.setImageDrawable(getResources().getDrawable(R.mipmap.star_big3));
            start.setLayoutParams(layoutParams);
            starts.addView(start);
        }
        for (int i=daShengModel.getGodStar();i<5;i++){
            ImageView start = new ImageView(this);
            start.setImageDrawable(getResources().getDrawable(R.mipmap.star_big1));
            start.setLayoutParams(layoutParams);
            starts.addView(start);
        }

        return view;
    }

}
