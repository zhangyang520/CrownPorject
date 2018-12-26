package com.example.administrator.chengnian444.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.adapter.PresentationRecylerviewAdapter;
import com.example.administrator.chengnian444.bean.BannerBean;
import com.example.administrator.chengnian444.bean.InComeBean;
import com.example.administrator.chengnian444.bean.PresentationBalanceBean;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

import java.util.ArrayList;
import java.util.List;

/**
 *  提现明细 的 fragment
 */
public class DetailofPresentationFragment extends Fragment {

    @Bind(R.id.refresh)
    SmartRefreshLayout refresh;

    @Bind(R.id.recycler)
    RecyclerView recycler;

    private View contentView;
    public DetailofPresentationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println(" DetailofBalanceFragment onCreateView ");
        if(contentView==null){
            contentView=View.inflate(getContext(),R.layout.fragment_detailof_balance,null);
            ButterKnife.bind(this,contentView);
        }
        return contentView;
    }


    private PresentationRecylerviewAdapter presentationRecylerviewAdapter;
    //集合的数据
    private List<PresentationBalanceBean> datas=new ArrayList<PresentationBalanceBean>();

    //页码数
    private int page=1;

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("DeitalofBalance fRAGMENT onResume");
        //进行初始化数据 也可以说 请求数据H
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        datas=getDatas();
        presentationRecylerviewAdapter = new PresentationRecylerviewAdapter(getContext(), datas);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(presentationRecylerviewAdapter);


        //初始化数据
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


    /**
     * http请求的数据
     */
    private void httpgetDate() {
        OkHttpUtils.get()
                .url(Constant.BANNER)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("type", "10002")
                .addParams("appType","001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BannerBean bannerBean = JSON.parseObject(response, BannerBean.class);
                        if (bannerBean.getCode() == 200) {

//                            bannerBeanData = bannerBean.getData();
//                            for (int i = 0; i < bannerBeanData.size(); i++) {
//                                String image = bannerBeanData.get(i).getImage();
//                                images1.add(image);
//                            }
//
//                            bannerMove.setImagesUrl(images1);
                        } else {
                            Toast.makeText(getActivity(), bannerBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * |模拟的数据
     * @return
     */
    public List<PresentationBalanceBean> getDatas(){

        List<PresentationBalanceBean> datqs=new ArrayList<PresentationBalanceBean>();
        for (int i = 0; i < 20; ++i) {
            PresentationBalanceBean data=new PresentationBalanceBean();
            data.setBalance(210.5+i);
            data.setDate("2019-12-1");
            data.setIncome(310.5 + i);
            data.setName("一级推广收益");
            datqs.add(data);
        }
        return datqs;
    }
}
