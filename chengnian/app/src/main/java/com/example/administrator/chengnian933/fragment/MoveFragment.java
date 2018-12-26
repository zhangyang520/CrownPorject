package com.example.administrator.chengnian933.fragment;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.activity.WebActivity;
import com.example.administrator.chengnian933.adapter.QualityAdapter;
import com.example.administrator.chengnian933.base.BaseFragment;
import com.example.administrator.chengnian933.bean.BannerBean;
import com.example.administrator.chengnian933.bean.MoveQuality;
import com.example.administrator.chengnian933.http.Constant;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.recker.flybanner.FlyBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import okhttp3.Call;

public class MoveFragment extends BaseFragment {
    @Bind(R.id.banner_quality)
    FlyBanner bannerQuality;
    @Bind(R.id.recycler_quality)
    RecyclerView recyclerQuality;
    @Bind(R.id.home_iv)
    ImageView homeIv;
    private List<BannerBean.DataBean> bannerBeanData;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_move;
    }

    @Override
    protected void initView(View childView) {
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layout.setSmoothScrollbarEnabled(true);
        recyclerQuality.setLayoutManager(layout);
        recyclerQuality.setHasFixedSize(true);
        recyclerQuality.setNestedScrollingEnabled(false);
        getBannerData();
        getData();
        bannerQuality.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",bannerBeanData.get(position).getUrl());
                startActivity(intent);
            }
        });

    }

    private void getBannerData() {
        OkHttpUtils.post()
                .url(Constant.BANNER)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("type", "10003")
                .addParams("appType","933")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BannerBean bannerBean = JSON.parseObject(response, BannerBean.class);
                        if (bannerBean.getCode() == 200) {
                            List<String> images = new ArrayList<>();
                            bannerBeanData = bannerBean.getData();
                            for (int i = 0; i < bannerBeanData.size(); i++) {
                                String image = bannerBeanData.get(i).getImage();
                                images.add(image);
                            }
                            bannerQuality.setImagesUrl(images);
                        } else {
                            Toast.makeText(getActivity(), bannerBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getData() {
        dialogShow();
        OkHttpUtils.post()
                .url(Constant.MOVEQUALITY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialogDismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialogDismiss();
                        Log.d("hcy",response.toString());
                        MoveQuality moveQuality = JSON.parseObject(response, MoveQuality.class);
                        if (moveQuality.getCode() == 200) {
                            List<MoveQuality.DataBean.DataListBean> dataList = moveQuality.getData().getDataList();
                            if (dataList==null){
                               homeIv.setVisibility(View.VISIBLE);
                            }else {
                                QualityAdapter qualityAdapter = new QualityAdapter(R.layout.item_quality, dataList, getActivity());
                                recyclerQuality.setAdapter(qualityAdapter);
                            }

                        } else {
                            Toast.makeText(getActivity(), moveQuality.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeFragment.imageView.setVisibility(View.GONE);
        boolean mobileConnected = isNetworkConnected(getActivity());
        if (!mobileConnected) {
            homeIv.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            HomeFragment.imageView.setVisibility(View.GONE);
            if (!isNetworkConnected(getActivity())) {
                homeIv.setVisibility(View.VISIBLE);
            } else {
                homeIv.setVisibility(View.GONE);
            }
        }
    }
}
