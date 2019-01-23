package com.jh.lottery.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import com.jh.lottery.adapter.CommunityAdapter;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.BannerModel;
import com.jh.lottery.model.BlockModel;
import com.jh.lottery.utils.GlideImageLoader;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.FlowLayout;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/8/4.
 */

public class CommunityActivity extends BaseActivity {

    private ListView listView;
    private List<Object> models = new ArrayList<>();
    private List<BlockModel> hotModels = new ArrayList<>();
    private CommunityAdapter adapter;
    private Banner banner;
    private TextView headerTitle;
    private FlowLayout flowLayout;
    @Override
    public int getLayoutId() {
        return R.layout.activity_community;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void initView() {
        showBackButton();
        setTitle("社区");

        listView = find(R.id.listView);
        listView.addHeaderView(headerView());
        adapter = new CommunityAdapter(this,models);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BlockModel model = (BlockModel) models.get(position - 1);
                details(model);
            }
        });
    }


    public void loadData(){

        HttpTool.GET(AppConfig.getHotBlockUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONArray jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<BlockModel> lists = gson.fromJson(jsonArray.toString(),new TypeToken<List<BlockModel>>(){}.getType());
                    hotModels.addAll(lists);
                    if (hotModels.size() > 0){
                        flowLayout.setVisibility(View.VISIBLE);
                        headerTitle.setVisibility(View.VISIBLE);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0,0,15,15);
                        for (int i=0;i<hotModels.size();i++) {
                            BlockModel model = hotModels.get(i);
                            TextView tag = new TextView(CommunityActivity.this);
                            tag.setText(model.getPlateName());
                            tag.setBackground(getResources().getDrawable(R.drawable.shape_tv_blue));
                            tag.setTextColor(getResources().getColor(R.color.color444));
                            tag.setLayoutParams(layoutParams);
                            tag.setTextSize(16);
                            tag.setPadding(20,10,20,10);
                            tag.setClickable(true);
                            tag.setTag(i);
                            tag.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    details(hotModels.get((int) v.getTag()));
                                }
                            });
                            flowLayout.addView(tag);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



        HttpTool.GET(AppConfig.getCommunityBannerUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<BannerModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<BannerModel>>(){}.getType());
                    List<String> banners = new ArrayList<>();
                    for (BannerModel model : list) {
                        banners.add(model.getImgUrl());
                    }
                    banner.setImages(banners).setImageLoader(new GlideImageLoader()).start();

                } catch (JSONException e) {

                }
            }
        });

        HttpTool.GET(AppConfig.getOtherBlockUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    JSONArray jsonArray = json.getJSONArray("data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject obj = (JSONObject) jsonArray.get(i);
                        models.add(obj.getString("nameCn"));
                        JSONArray list = obj.getJSONArray("list");
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        List<BlockModel> lists = gson.fromJson(list.toString(),new TypeToken<List<BlockModel>>(){}.getType());
                        models.addAll(lists);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void details(BlockModel model){

        Intent intent = new Intent(this,CommunityTopicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("model",model);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private View headerView(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_community_header_view,null);
        banner = view.findViewById(R.id.banner);
        WindowManager wm = getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) banner.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = width/2;
        banner.setLayoutParams(linearParams);
        banner.setDelayTime(5000);

        headerTitle = view.findViewById(R.id.title);
        flowLayout = view.findViewById(R.id.flowLayout);
        flowLayout.setVisibility(View.GONE);
        headerTitle.setVisibility(View.GONE);
        return view;
    }

}
