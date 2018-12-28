package com.example.administrator.chengnian444.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.adapter.DownAdapter;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.bean.YingyongBean;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class YingYongActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.down_recylce)
    RecyclerView downRecylce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuijian);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.black));
        downRecylce.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        httpYingyongList();

    }

    private void httpYingyongList() {
        OkHttpUtils.post()
                .url(Constant.YINGYONGLIST)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("loginToken", SPUtils.getInstance(this).getString("loginToken"))
                .addParams("appType", "001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("hcy",response.toString());
                        YingyongBean yingyongBean = JSON.parseObject(response, YingyongBean.class);
                        if (yingyongBean.getCode()==200){
                            List<YingyongBean.DataBean> data = yingyongBean.getData();
                            DownAdapter adapter = new DownAdapter(R.layout.item_yingyong,data,YingYongActivity.this);
                            downRecylce.setAdapter(adapter);
                        }else if (yingyongBean.getCode()==301){
                            exitDialog();
                        }
                    }
                });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
