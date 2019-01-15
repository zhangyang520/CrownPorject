package com.example.administrator.chengnian444.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.activity.WebActivity;
import com.example.administrator.chengnian444.base.BaseFragment;
import com.example.administrator.chengnian444.bean.BannerBean;
import com.example.administrator.chengnian444.bean.LiveTypesResponseInfo;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.TabUtils;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.example.administrator.chengnian444.view.JudgeNestedScrollView;
import com.recker.flybanner.FlyBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import okhttp3.Call;

/**
 * 影库
 */
public class LoanFragment extends BaseFragment {
    @Bind(R.id.magic_indicator)
    MagicIndicator magic_indicator;
    @Bind(R.id.vp)
    ViewPager vp;
    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    List<String> images1 = new ArrayList<>();
    @Bind(R.id.banner_move)
    FlyBanner bannerMove;
    @Bind(R.id.home_iv)
    ImageView homeIv;

//    @Bind(R.id.scrollView)
//    JudgeNestedScrollView scrollView;
    //是否 有对应的网络数据
    private boolean hasInternetData=false;

    private List<BannerBean.DataBean> bannerBeanData;

    private List<LiveTypesResponseInfo.LiveTypesResponse> liveTypesResponses;
    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_loan;
    }

    @Override
    protected void initView(View childView) {
        //获取电影类型
        getBannerMove();
        //獲取 類型數據
        getTypeList();
        bannerMove.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",bannerBeanData.get(position).getUrl());
                startActivity(intent);
            }
        });
//        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                System.out.println("onScrollChange scrollX:"+scrollX+"..scrollY:"+scrollY+"..oldScrollX:"+oldScrollX+"..oldScrollY:"+oldScrollY);
//            }
//        });
        //主要通过 反射 获取 其中的tab的个数  个数 能够 获取 但是 底层的逻辑不能修改 tabIndexdicationor  的宽度 默认是 文字的宽度！
//        teb.post(new Runnable() {
//            @Override
//            public void run() {
//                 //主要 进行获取其中的个数 tabs 集合
//                try {
//                    Field field=teb.getClass().getDeclaredField("tabs");
//                    field.setAccessible(true);
//                    ArrayList<TabLayout.Tab> tabs=(ArrayList<TabLayout.Tab>) field.get(teb);
//                    for(TabLayout.Tab data:tabs){
//                        System.out.println("data:"+data.view);
//                        Class cls=Class.forName("android.support.design.widget.TabLayout$TabView");
//                        Field field1 = cls.getDeclaredField("iconView");
//                        field1.setAccessible(true);
//                        ImageView iconView =(ImageView) field1.get(data.view);
//                        int imageWith = iconView.getLayoutParams().width;
//                        if(imageWith==0){
//                            continue;
//                        }
//                        iconView.setPadding(imageWith/4,0,imageWith/4,0);
//                        iconView.requestLayout();
//
//                        Field txtField = cls.getDeclaredField("textView");
//                        txtField.setAccessible(true);
//                        TextView content = (TextView) txtField.get(data.view);
//                        System.out.println("imagewidth:"+imageWith+"..CONTENT:"+content.getText().toString());
//                    }
//                    System.out.println("tabs size:"+tabs.size());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void showData(){
        if(!hasInternetData){
            //获取电影类型
            getBannerMove();
            //獲取 類型數據
            getTypeList();
        }
    }
    /**
     * 獲取 類型的數據
     */
    private void getTypeList() {
        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.MOVETYPE)
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("type", "10002")
                .addParams("appType",Constant.platform_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hasInternetData=false;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LiveTypesResponseInfo bannerBean = JSON.parseObject(response,  LiveTypesResponseInfo.class);
                        if (bannerBean.getCode() == 200) {

                            liveTypesResponses = bannerBean.getData();
                            for (int i = 0; i < liveTypesResponses.size(); i++) {
                                list.add(liveTypesResponses.get(i).getType());
                                list1.add(liveTypesResponses.get(i).getTypeNum());
                            }
                            initData();
                            hasInternetData=true;
                        } else {
                            hasInternetData=false;
                            ToastUtils.showToast(getActivity(), bannerBean.getMessage());
                        }
                    }
                });
    }

    private void getBannerMove() {

        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.BANNER)
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("type", "10002")
                .addParams("appType","001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hasInternetData=false;
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        BannerBean bannerBean = JSON.parseObject(response, BannerBean.class);
                        if (bannerBean.getCode() == 200) {
                            if(bannerBean.getData()!=null){
                                bannerBeanData = bannerBean.getData();
                                for (int i = 0; i < bannerBeanData.size(); i++) {
                                    String image = bannerBeanData.get(i).getImage();
                                    images1.add(image);
                                }
                                bannerMove.setImagesUrl(images1);
                            }
                            hasInternetData=true;
                        } else {
                            hasInternetData=false;
                            ToastUtils.showToast(getActivity(), bannerBean.getMessage());
                        }
                    }
                });
    }

    /**
     * 初始化 數據
     */
    private void initData(){
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(myPagerAdapter);

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return list == null ? 0 : list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#bebebe"));
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(list.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(context.getResources().getColor(R.color.yellow));
//                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);  //包含内容的长度
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                //设置indicator的宽度
                indicator.setLineWidth(TabUtils.Dp2Px(context,29));
//                indicator.setLineWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                indicator.setLineHeight(TabUtils.Dp2Px(context,4));
                return indicator;
            }
        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator,vp);

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

