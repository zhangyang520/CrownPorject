package com.example.administrator.chengnian444.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.adapter.MoveAdapter;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.bean.MoveListBean;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class MoveListActivity extends BaseActivity {
    @Bind(R.id.recycler_list)
    RecyclerView recyclerList;
    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;
    @Bind(R.id.title)
    TextView tv_title;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.home_iv)
    ImageView homeIv;
    private int page = 1;
    private List<MoveListBean.DataBean.DataListBean> data;
    private BaseQuickAdapter moveAdapter;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_list);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.main_bg_color));
        recyclerList.setLayoutManager(new GridLayoutManager(this, 3));
        moveAdapter = new MoveAdapter(R.layout.test, data, this);
        recyclerList.setAdapter(moveAdapter);

        Intent intent = getIntent();
        type = intent.getStringExtra("typenum");
        String title = intent.getStringExtra("title");
        tv_title.setText(title);
        httpgetDate();
        //type = this.getArguments().getString("typenum");


        refresh.setRefreshHeader(new ClassicsHeader(this));
        refresh.setRefreshFooter(new ClassicsFooter(this));

        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                httpgetDate();
                refreshlayout.finishRefresh();
            }
        });

        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                httpgetDate();
                refreshlayout.finishLoadMore();//传入false表示加载失败
            }
        });

    }

    private void httpgetDate() {
        dialogShow();
        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.MOVELIST)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("loginToken", SPUtils.getInstance(this).getString("loginToken"))
                .addParams("typeNum", type)
                .addParams("currentPage", page + "")
                .addParams("pageSize", "18")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialogDismiss();
                        homeIv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialogDismiss();
                        MoveListBean homeBean = JSON.parseObject(response, MoveListBean.class);
                        if (homeBean.getCode() == 200) {

                            data = homeBean.getData().getDataList();
                            if (data==null){
                                homeIv.setVisibility(View.VISIBLE);
                            }
                            if (page == 1) {

                                moveAdapter.setNewData(data);
                            } else {
                                moveAdapter.addData(data);
                            }
                        } else if (homeBean.getCode() == 301){
                            exitDialog();
                        }else {
                            // Toast.makeText(getActivity(), homeBean.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @OnClick({R.id.back, R.id.home_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.home_iv:
                httpgetDate();
                break;
        }
    }
}
