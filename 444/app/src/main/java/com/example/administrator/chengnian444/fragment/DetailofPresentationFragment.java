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
import com.example.administrator.chengnian444.base.MyApplication;
import com.example.administrator.chengnian444.bean.*;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.DateUtil;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.ToastUtils;
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

    private final int PAGE_SIZE=20;
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
//        datas=getDatas();
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
        try {
            OkHttpUtils.post()
                    .url(Constant.BASEURL+Constant.withDrawDetail)
                    .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                    .addParams("account", UserDao.getLocalUser().userName)
                    .addParams("appType",Constant.platform_id)
                    .addParams("currentPage",page+"")
                    .addParams("pageSize",PAGE_SIZE+"")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtils.showToast(MyApplication.context,"查看提现明細失敗!");
                            if((page>1)){
                                page--;
                            }
                        }

                        @Override
                        public void onResponse(String response, int id) {
                                WithdrawDetailResponse bannerBean = JSON.parseObject(response, WithdrawDetailResponse.class);
                                if (bannerBean.getCode() == 200) {
                                    //進行轉換
                                    if(bannerBean.getData()!=null && bannerBean.getData().getDataList()!=null && bannerBean.getData().getDataList().size()>0){
                                        ArrayList<PresentationBalanceBean> inComeBeanArrayList=new ArrayList<PresentationBalanceBean>();
                                        for (WithdrawDetailResponse.WithdrawDetailBean.WithdrawDetail data : bannerBean.getData().getDataList()) {
                                            PresentationBalanceBean inComeBean=new PresentationBalanceBean();
                                            inComeBean.setName(data.getDetailEventName());
                                            inComeBean.setBalance(data.getBalance());
                                            inComeBean.setIncome(data.getMoneyCount());
                                            inComeBean.setDate(DateUtil.getYearOrMonthOrDay(data.getCreateTime()));
                                            inComeBean.setStatus(data.getStatus());
                                            inComeBeanArrayList.add(inComeBean);
                                        }
                                        if(page==1){
                                            //進行刷訊
                                            presentationRecylerviewAdapter.getIncomeBeans().clear();
                                            presentationRecylerviewAdapter.getIncomeBeans().addAll(inComeBeanArrayList);
                                        }else{
                                            presentationRecylerviewAdapter.getIncomeBeans().addAll(inComeBeanArrayList);
                                        }
                                        presentationRecylerviewAdapter.notifyDataSetChanged();
                                    }else{
                                        if(page>1){
                                            page--;
                                            ToastUtils.showToast(MyApplication.context,"暂无更多数据");
                                        }else{
                                            ToastUtils.showToast(MyApplication.context,"暂无刷新数据");
                                        }
                                    }
                                } else {
                                    ToastUtils.showToast(MyApplication.context, bannerBean.getMessage());
                                }
                        }
                    });
        } catch (ContentException e) {
            e.printStackTrace();
        }
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
