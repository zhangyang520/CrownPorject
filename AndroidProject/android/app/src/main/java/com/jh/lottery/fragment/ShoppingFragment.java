package com.jh.lottery.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jh.lottery.AppConfig;
import allen.com.rntestproject.R;
import com.jh.lottery.activity.HistoryActivity;
import com.jh.lottery.activity.LoginActivity;
import com.jh.lottery.adapter.ShoppingAdapter;
import com.jh.lottery.base.BaseFragement;
import com.jh.lottery.model.BannerModel;
import com.jh.lottery.model.GiftModel;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.GlideImageLoader;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.utils.UIHelper;
import com.jh.lottery.view.PopWindowUtils;
import com.jh.lottery.view.SpacesItemDecoration;
import com.jh.lottery.view.XToast;
import com.youth.banner.Banner;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.jh.lottery.AppConfig.giftListUrl;
import static com.jh.lottery.AppConfig.homeBannerUrl;

/**
 * Created by sangcixiang on 2018/7/26.
 */

public class ShoppingFragment extends BaseFragement implements View.OnClickListener{
    //历史记录
    private ImageView historyBtn;
    //抽奖
    private ImageView luckBtn;
    //游戏
    private ImageView gameBtn;
    private Banner banner;
    //礼物集合
    private RecyclerView recyclerView;
    private ShoppingAdapter adapter;
    private HeaderAndFooterWrapper headerWrapper;
    private UserModel user;
    private TextView score;
    private List<GiftModel> models = new ArrayList<>();
    private GiftModel currentModel;
    private ProgressBar progressBar;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopping;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void initView() {
        progressBar = find(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        adapter = new ShoppingAdapter(getActivity(),models);
        adapter.setOnItemClickListener(new ShoppingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GiftModel model) {
                currentModel = model;
                if (user != null){
                    if (Integer.valueOf(user.getMemberScore()) < model.getGiftIntegral()){
                        XToast.warning("当前积分不足!");
                    }else {
                        PopWindowUtils.showInputView(getActivity(), getView(), new PopWindowUtils.InputCallback() {
                            @Override
                            public void callback(Map<String, String> map) {

                                sendExchange(map);
                            }
                        });
                    }
                }else {
                    JumpToActivity(LoginActivity.class);
                }

            }
        });
        headerWrapper = new HeaderAndFooterWrapper(adapter);
        headerWrapper.addHeaderView(headerView());

        recyclerView = find(R.id.recycler_view);
        //设置布局管理者
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new SpacesItemDecoration(getActivity(),1));
        recyclerView.setAdapter(headerWrapper);
    }
    //发起礼物兑换
    private void sendExchange(Map<String,String> map){
        map.put("acctBaseId",user.getAcctBaseId());
        map.put("pointsGiftid",currentModel.getId()+"");
        HttpTool.GET(AppConfig.pointsGiftUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                int scoreValue = Integer.valueOf(user.getMemberScore())  - currentModel.getGiftIntegral();
                user.setMemberScore(scoreValue+"");
                UserModel.saveUserInfo(user);
                score.setText("我的积分："+scoreValue);
                XToast.success("兑换申请已提交!");
            }
        });
    }

    @Override
    public void resume(){
        if (isResume){
            user = UserModel.getUserInfo();
            if (null != user){
                score.setText("我的积分："+user.getMemberScore());
            }else {
                score.setText("未登录");
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.history:
                if (user != null){
                    JumpToActivity(HistoryActivity.class);
                }else {
                    JumpToActivity(LoginActivity.class);
                }

                break;
            case R.id.luck:
                UIHelper.showMessage("抽奖");
                break;
            case R.id.game:
                UIHelper.showMessage("游戏");
                break;
            default:
                break;
        }
    }

    private void loadData(){
        HttpTool.GET(homeBannerUrl, null, new HttpTool.HttpCallback() {
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
        HttpTool.GET(giftListUrl, null, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {

                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                JSONArray jsonArray = null;
                try {
                    jsonArray = json.getJSONArray("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    List<GiftModel> list = gson.fromJson(jsonArray.toString(),new TypeToken<List<GiftModel>>(){}.getType());
                    models.addAll(list);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {

                }
            }

            @Override
            public void onError(String msg) {
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

    }


    private View headerView(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_shopping_header,recyclerView);

        score = view.findViewById(R.id.score);
        historyBtn = view.findViewById(R.id.history);
        luckBtn = view.findViewById(R.id.luck);
        gameBtn = view.findViewById(R.id.game);
        historyBtn.setOnClickListener(this);
        luckBtn.setOnClickListener(this);
        gameBtn.setOnClickListener(this);

        banner = view.findViewById(R.id.banner);

        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) banner.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = width/2;
        banner.setLayoutParams(linearParams);
        banner.setDelayTime(5000);

        return view;
    }



}
