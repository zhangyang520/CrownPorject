package com.example.administrator.chengnian933.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.activity.WebActivity;
import com.example.administrator.chengnian933.base.BaseFragment;
import com.example.administrator.chengnian933.bean.BannerBean;
import com.example.administrator.chengnian933.http.Constant;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.recker.flybanner.FlyBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoanFragment extends BaseFragment {
    @Bind(R.id.teb)
    TabLayout teb;
    @Bind(R.id.vp)
    ViewPager vp;
    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    List<String> images1 = new ArrayList<>();
    @Bind(R.id.banner_move)
    FlyBanner bannerMove;
    @Bind(R.id.home_iv)
    ImageView homeIv;
    private List<BannerBean.DataBean> bannerBeanData;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_loan;
    }

    @Override
    protected void initView(View childView) {
        //获取电影类型
        getBannerMove();

        list.add("中文有碼");
        list.add("中文無碼");
        list.add("日本有碼");
        list.add("日本無碼");
        list.add("歐美無碼");
        list.add("三級劇情");
        list.add("卡通動漫");
        list.add("偷拍自拍");
        list1.add("6");
        list1.add("5");
        list1.add("7");
        list1.add("8");
        list1.add("4");
        list1.add("1");
        list1.add("2");
        list1.add("3");

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(teb));
        vp.setAdapter(myPagerAdapter);
        teb.setupWithViewPager(vp);
        bannerMove.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",bannerBeanData.get(position).getUrl());
                startActivity(intent);
            }
        });

    }

    private void getBannerMove() {

        OkHttpUtils.get()
                .url(Constant.BANNER)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("type", "10002")
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

                            bannerBeanData = bannerBean.getData();
                            for (int i = 0; i < bannerBeanData.size(); i++) {
                                String image = bannerBeanData.get(i).getImage();
                                images1.add(image);
                            }

                            bannerMove.setImagesUrl(images1);
                        } else {
                            Toast.makeText(getActivity(), bannerBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            TestFragment testFragment = new TestFragment();
            Bundle bundle = new Bundle();
            bundle.putString("title", list.get(position));
            bundle.putString("typenum", list1.get(position));
            testFragment.setArguments(bundle);
            return testFragment;
        }

        @Override
        public int getCount() {
            return list.size();
        }
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

