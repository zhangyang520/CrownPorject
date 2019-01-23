package com.jh.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import com.jh.lottery.activity.DaShenActivity;
import com.jh.lottery.activity.OpenPrizeActivity;
import com.jh.lottery.base.BaseFragement;
import com.jh.lottery.model.AfficheModel;
import com.jh.lottery.model.BannerModel;
import com.jh.lottery.utils.GlideImageLoader;
import com.jh.lottery.utils.HttpTool;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import allen.com.rntestproject.R;


/**
 * Created by sangcixiang on 2018/7/26.
 */

public class HomeFragment extends BaseFragement implements View.OnClickListener{

    private Banner banner; //滚动视频
//    private MarqueeView marqueeView;
    private TextView scrollLabel;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void initView() {

        banner = find(R.id.banner);
        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) banner.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = width/2;
        banner.setLayoutParams(linearParams);
        banner.setDelayTime(5000);

        scrollLabel = find(R.id.scrollLabel);

        find(R.id.openNumber).setOnClickListener(this);
        find(R.id.dashen).setOnClickListener(this);
        find(R.id.ssqBtn).setOnClickListener(this);
        find(R.id.fc3dBtn).setOnClickListener(this);
        find(R.id.pl3Btn).setOnClickListener(this);
        find(R.id.pl5Btn).setOnClickListener(this);
        find(R.id.qxcBtn).setOnClickListener(this);
        find(R.id.qlcBtn).setOnClickListener(this);
        find(R.id.dltBtn).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
//        marqueeView.startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
//        marqueeView.stopFlipping();
    }

    private void loadData(){
        //查询Banner
        HttpTool.GET(AppConfig.homeBannerUrl, null, new HttpTool.HttpCallback() {
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
        //查询公告消息
        HttpTool.GET(AppConfig.getAfficheUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<AfficheModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<AfficheModel>>(){}.getType());
                    List<String> texts = new ArrayList<>();
                    for (AfficheModel model : list) {
                        texts.add(model.getContent());
                    }
                    if (texts.size() > 0){
                        scrollLabel.setText(texts.get(0));
                    }else {
                        scrollLabel.setText("暂无公告信息!");
                    }
                } catch (JSONException e) {

                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.openNumber:
                openNumberDetail("all","最新开奖");
                break;
            case R.id.dashen:
                Intent intent = new Intent(getActivity(), DaShenActivity.class);
                startActivity(intent);
                break;
            case R.id.ssqBtn:
                openNumberDetail("SSQ","双色球");
                break;
            case R.id.fc3dBtn:
                openNumberDetail("FC3D","福彩3D");
                break;
            case R.id.dltBtn:
                openNumberDetail("DLT","大乐透");
                break;
            case R.id.qxcBtn:
                openNumberDetail("QXC","七星彩");
                break;
            case R.id.qlcBtn:
                openNumberDetail("QLC","七乐彩");
                break;
            case R.id.pl3Btn:
                openNumberDetail("PL3","排列3");
                break;
            case R.id.pl5Btn:
                openNumberDetail("PL5","排列5");
                break;
            default:
                break;
        }
    }

    public void openNumberDetail(String type, String title){
        Intent intent = new Intent(getActivity(), OpenPrizeActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putString("type",type);
        mBundle.putString("title", title);
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}
