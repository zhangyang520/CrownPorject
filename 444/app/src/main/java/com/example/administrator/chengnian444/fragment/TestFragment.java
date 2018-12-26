package com.example.administrator.chengnian444.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.adapter.MoveAdapter;
import com.example.administrator.chengnian444.base.BaseFragment;
import com.example.administrator.chengnian444.bean.MoveListBean;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

public class TestFragment extends BaseFragment {
    @Bind(R.id.recycler)
    RecyclerView recycler;
    @Bind(R.id.refresh)
    RefreshLayout refresh;
    @Bind(R.id.home_iv)
    ImageView homeIv;

    private int page = 1;
    private String type;
    //List<MoveListBean.DataBean> list = new ArrayList<>();
    private MoveAdapter moveAdapter;
    private List<MoveListBean.DataBean.DataListBean> data;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_movelist;
    }

    @Override
    protected void initView(View childView) {
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        moveAdapter = new MoveAdapter(R.layout.test, data, getActivity());
        recycler.setAdapter(moveAdapter);

        String name = this.getArguments().getString("title");
        type = this.getArguments().getString("typenum");

        httpgetDate();
        refresh.setRefreshHeader(new ClassicsHeader(getActivity()));
        refresh.setRefreshFooter(new ClassicsFooter(getActivity()));

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
        OkHttpUtils.get()
                .url(Constant.MOVELIST)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("loginToken", SPUtils.getInstance(getActivity()).getString("loginToken"))
                .addParams("typeNum", type)
                .addParams("currentPage", page + "")
                .addParams("pageSize", "18")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialogDismiss();
                        homeIv.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialogDismiss();
                        MoveListBean homeBean = JSON.parseObject(response, MoveListBean.class);
                        if (homeBean.getCode() == 200) {

                            data = homeBean.getData().getDataList();
                            //list.addAll(data);
                            Log.d("hcy", page + "");
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


    @OnClick(R.id.home_iv)
    public void onViewClicked() {
        httpgetDate();
    }
}

