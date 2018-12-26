package com.example.administrator.chengnian933.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.activity.MoveListActivity;
import com.example.administrator.chengnian933.activity.WebActivity;
import com.example.administrator.chengnian933.adapter.HomeChangerAdapter;
import com.example.administrator.chengnian933.adapter.TextAdapter;
import com.example.administrator.chengnian933.base.BaseFragment;
import com.example.administrator.chengnian933.bean.BannerBean;
import com.example.administrator.chengnian933.bean.HomeBean;
import com.example.administrator.chengnian933.bean.HomeChangeBean;
import com.example.administrator.chengnian933.bean.OneBannerBean;
import com.example.administrator.chengnian933.http.Constant;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.example.administrator.chengnian933.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian933.view.SonnyJackDragView;
import com.recker.flybanner.FlyBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.Call;

public class HomeFragment extends BaseFragment {
    @Bind(R.id.china_on)
    RecyclerView chinaOn;
    @Bind(R.id.home_banner)
    FlyBanner homeBanner;
    @Bind(R.id.recycler1)
    RecyclerView recycler1;
    @Bind(R.id.recycler2)
    RecyclerView recycler2;
    @Bind(R.id.recycler3)
    RecyclerView recycler3;
    @Bind(R.id.recycler4)
    RecyclerView recycler4;
    @Bind(R.id.recycler5)
    RecyclerView recycler5;
    @Bind(R.id.recycler6)
    RecyclerView recycler6;
    @Bind(R.id.recycler7)
    RecyclerView recycler7;
    @Bind(R.id.tv1)
    TextView tv1;
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.tv3)
    TextView tv3;
    @Bind(R.id.tv4)
    TextView tv4;
    @Bind(R.id.tv5)
    TextView tv5;
    @Bind(R.id.tv6)
    TextView tv6;
    @Bind(R.id.tv7)
    TextView tv7;
    @Bind(R.id.tv8)
    TextView tv8;
    @Bind(R.id.change1)
    ImageView change1;
    @Bind(R.id.change2)
    ImageView change2;
    @Bind(R.id.change3)
    ImageView change3;
    @Bind(R.id.change4)
    ImageView change4;
    @Bind(R.id.change5)
    ImageView change5;
    @Bind(R.id.change6)
    ImageView change6;
    @Bind(R.id.change7)
    ImageView change7;
    @Bind(R.id.change8)
    ImageView change8;
    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.image2)
    ImageView image2;
    @Bind(R.id.image3)
    ImageView image3;
    @Bind(R.id.image4)
    ImageView image4;
    @Bind(R.id.image5)
    ImageView image5;
    @Bind(R.id.image6)
    ImageView image6;
    @Bind(R.id.image7)
    ImageView image7;
    @Bind(R.id.image8)
    ImageView image8;
    @Bind(R.id.goucai)
    ImageView goucai;
    @Bind(R.id.more1)
    ImageView more1;
    @Bind(R.id.more2)
    ImageView more2;
    @Bind(R.id.more3)
    ImageView more3;
    @Bind(R.id.more4)
    ImageView more4;
    @Bind(R.id.more5)
    ImageView more5;
    @Bind(R.id.more6)
    ImageView more6;
    @Bind(R.id.more7)
    ImageView more7;
    @Bind(R.id.more8)
    ImageView more8;
    @Bind(R.id.home_iv)
    ImageView homeIv;

    private TextAdapter adapter;
    private HomeBean homeBean;
    private String url;
    private SonnyJackDragView mSonnyJackDragView;
    public static ImageView imageView;
    private List<BannerBean.DataBean> bannerBeanData;
    private long lastTime;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View childView) {
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.black));
        getHomeBanner();
        WindowManager wm = getActivity().getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();



        imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.qianghongbao);
        mSonnyJackDragView = new SonnyJackDragView.Builder()
                .setActivity(getActivity())
                .setDefaultLeft(width/2)
                .setDefaultTop(height/2)
                .setNeedNearEdge(true)
                .setSize(250)
                .setView(imageView)
                .build();
       /* boolean needNearEdge = mSonnyJackDragView.getNeedNearEdge();
        mSonnyJackDragView.setNeedNearEdge(!needNearEdge);*/
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (onMoreClick()) {
                    getHongBao();
                }*/
                getHongBao();
            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        chinaOn.setLayoutManager(gridLayoutManager);

        recycler1.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler1.setHasFixedSize(true);
        recycler1.setNestedScrollingEnabled(false);
        recycler2.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler2.setHasFixedSize(true);
        recycler2.setNestedScrollingEnabled(false);

        recycler3.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler3.setHasFixedSize(true);
        recycler3.setNestedScrollingEnabled(false);

        recycler4.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler4.setHasFixedSize(true);
        recycler4.setNestedScrollingEnabled(false);

        recycler5.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler5.setHasFixedSize(true);
        recycler5.setNestedScrollingEnabled(false);

        recycler6.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler6.setHasFixedSize(true);
        recycler6.setNestedScrollingEnabled(false);

        recycler7.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycler7.setHasFixedSize(true);
        recycler7.setNestedScrollingEnabled(false);


        homeBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url", bannerBeanData.get(position).getUrl());
                startActivity(intent);
            }
        });

        getHomeList();

    }


   /* public boolean onMoreClick() {
        boolean flag = true;
        long time = System.currentTimeMillis() - lastTime;
        if (time < 2000) {
            flag = false;
        }
        lastTime = System.currentTimeMillis();
        return flag;
    }*/

    private void getHongBao() {
        OkHttpUtils.post()
                .url(Constant.GETONEBANNER)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("type", "20001")
                .addParams("appType", "933")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        OneBannerBean oneBannerBean = JSON.parseObject(response, OneBannerBean.class);
                        if (oneBannerBean.getCode() == 200) {
                            String url = oneBannerBean.getData().getUrl();
                            Intent intent = new Intent(getActivity(), WebActivity.class);
                            intent.putExtra("url", url);
                            startActivity(intent);
                        }
                    }
                });
    }


    private void getHomeList() {
        dialogShow();
        OkHttpUtils.post()
                .url(Constant.HOMELIST)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("appType", "933")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.d("hcy", e.toString());
                        dialogDismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialogDismiss();
                        homeBean = JSON.parseObject(response, HomeBean.class);
                        if (homeBean.getCode() == 200) {
                            List<HomeBean.DataBean.LiveMoviesBean> data = homeBean.getData().get(0).getLiveMovies();
                            tv1.setText(data.get(0).getType());
                            adapter = new TextAdapter(R.layout.test, data, getActivity());
                            chinaOn.setAdapter(adapter);
                            Glide.with(getActivity()).load(homeBean.getData().get(0).getImage()).into(image1);
                            url = homeBean.getData().get(0).getUrl();
                            SPUtils.getInstance(getActivity()).put("homebanner", url);


                            List<HomeBean.DataBean.LiveMoviesBean> data1 = homeBean.getData().get(1).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(1).getImage()).into(image2);
                            tv2.setText(data1.get(0).getType());
                            adapter = new TextAdapter(R.layout.test, data1, getActivity());
                            recycler1.setAdapter(adapter);

                            List<HomeBean.DataBean.LiveMoviesBean> data2 = homeBean.getData().get(2).getLiveMovies();
                            tv3.setText(data2.get(0).getType());
                            adapter = new TextAdapter(R.layout.test, data2, getActivity());
                            recycler2.setAdapter(adapter);
                            Glide.with(getActivity()).load(homeBean.getData().get(2).getImage()).into(image3);
                            List<HomeBean.DataBean.LiveMoviesBean> data3 = homeBean.getData().get(3).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(3).getImage()).into(image4);
                            tv4.setText(data3.get(0).getType());
                            adapter = new TextAdapter(R.layout.test, data3, getActivity());
                            recycler3.setAdapter(adapter);
                            List<HomeBean.DataBean.LiveMoviesBean> data4 = homeBean.getData().get(4).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(4).getImage()).into(image5);
                            tv5.setText(data4.get(0).getType());
                            adapter = new TextAdapter(R.layout.test, data4, getActivity());
                            recycler4.setAdapter(adapter);
                            List<HomeBean.DataBean.LiveMoviesBean> data5 = homeBean.getData().get(5).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(5).getImage()).into(image6);
                            tv6.setText(data5.get(0).getType());
                            adapter = new TextAdapter(R.layout.test, data5, getActivity());
                            recycler5.setAdapter(adapter);
                            List<HomeBean.DataBean.LiveMoviesBean> data6 = homeBean.getData().get(6).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(6).getImage()).into(image7);
                            tv7.setText(data6.get(0).getType());
                            adapter = new TextAdapter(R.layout.test, data6, getActivity());
                            recycler6.setAdapter(adapter);
                            List<HomeBean.DataBean.LiveMoviesBean> data7 = homeBean.getData().get(7).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(7).getImage()).into(image8);
                            tv8.setText(data7.get(0).getType());
                            adapter = new TextAdapter(R.layout.test, data7, getActivity());
                            recycler7.setAdapter(adapter);

                        } else {
                            Toast.makeText(getActivity(), homeBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void getHomeBanner() {
        OkHttpUtils.post()
                .url(Constant.BANNER)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("type", "10001")
                .addParams("appType", "933")
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
                            homeBanner.setImagesUrl(images);
                        } else {
                            Toast.makeText(getActivity(), bannerBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @OnClick({R.id.change1, R.id.change2, R.id.change3,
            R.id.change4, R.id.change5, R.id.change6,
            R.id.change7, R.id.change8, R.id.goucai,
            R.id.image1, R.id.image2, R.id.image3,
            R.id.image4, R.id.image5, R.id.image6, R.id.image7
            , R.id.image8, R.id.more1, R.id.more2, R.id.more3, R.id.more4
            , R.id.more5, R.id.more6, R.id.more7, R.id.more8
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change1:
                getHomeChange("1", chinaOn);
                break;
            case R.id.change2:
                getHomeChange("2", recycler1);
                break;
            case R.id.change3:
                getHomeChange("3", recycler2);
                break;
            case R.id.change4:
                getHomeChange("4", recycler3);
                break;
            case R.id.change5:
                getHomeChange("5", recycler4);
                break;
            case R.id.change6:
                getHomeChange("6", recycler5);
                break;
            case R.id.change7:
                getHomeChange("7", recycler6);
                break;
            case R.id.change8:
                getHomeChange("8", recycler7);
                break;
            case R.id.image1:
                Intent intent16 = new Intent(getActivity(), WebActivity.class);
                intent16.putExtra("url", url);
                startActivity(intent16);
                break;
            case R.id.image2:
                Intent intent15 = new Intent(getActivity(), WebActivity.class);
                intent15.putExtra("url", url);
                startActivity(intent15);
                break;
            case R.id.image3:
                Intent intent14 = new Intent(getActivity(), WebActivity.class);
                intent14.putExtra("url", url);
                startActivity(intent14);
                break;
            case R.id.image4:
                Intent intent13 = new Intent(getActivity(), WebActivity.class);
                intent13.putExtra("url", url);
                startActivity(intent13);
                break;
            case R.id.image5:
                Intent intent12 = new Intent(getActivity(), WebActivity.class);
                intent12.putExtra("url", url);
                startActivity(intent12);
                break;
            case R.id.image6:
                Intent intent11 = new Intent(getActivity(), WebActivity.class);
                intent11.putExtra("url", url);
                startActivity(intent11);
                break;
            case R.id.image7:
                Intent intent10 = new Intent(getActivity(), WebActivity.class);
                intent10.putExtra("url", url);
                startActivity(intent10);
                break;
            case R.id.image8:
                Intent intent9 = new Intent(getActivity(), WebActivity.class);
                intent9.putExtra("url", url);
                startActivity(intent9);
                break;
            case R.id.goucai:
                Intent intent17 = new Intent(getActivity(), WebActivity.class);
                intent17.putExtra("url", url);
                startActivity(intent17);
                break;
            case R.id.more1:
                Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                intent1.putExtra("typenum", "1");
                intent1.putExtra("title", "三級劇情");
                startActivity(intent1);
                break;
            case R.id.more2:
                Intent intent2 = new Intent(getActivity(), MoveListActivity.class);
                intent2.putExtra("typenum", "2");
                intent2.putExtra("title", "卡通动漫");
                startActivity(intent2);
                break;
            case R.id.more3:
                Intent intent3 = new Intent(getActivity(), MoveListActivity.class);
                intent3.putExtra("typenum", "3");
                intent3.putExtra("title", "偷拍自拍");
                startActivity(intent3);
                break;
            case R.id.more4:
                Intent intent4 = new Intent(getActivity(), MoveListActivity.class);
                intent4.putExtra("typenum", "4");
                intent4.putExtra("title", "歐美無碼");
                startActivity(intent4);
                break;
            case R.id.more5:
                Intent intent5 = new Intent(getActivity(), MoveListActivity.class);
                intent5.putExtra("typenum", "5");
                intent5.putExtra("title", "中文無碼");
                startActivity(intent5);
                break;
            case R.id.more6:
                Intent intent6 = new Intent(getActivity(), MoveListActivity.class);
                intent6.putExtra("typenum", "6");
                intent6.putExtra("title", "中文有碼");
                startActivity(intent6);
                break;
            case R.id.more7:
                Intent intent7 = new Intent(getActivity(), MoveListActivity.class);
                intent7.putExtra("typenum", "7");
                intent7.putExtra("title", "日本有碼");
                startActivity(intent7);
                break;
            case R.id.more8:
                Intent intent8 = new Intent(getActivity(), MoveListActivity.class);
                intent8.putExtra("typenum", "8");
                intent8.putExtra("title", "日本无碼");
                startActivity(intent8);
                break;
           /* case R.id.qianghongbao:
                Toast.makeText(getActivity(), "抢红包", Toast.LENGTH_SHORT).show();
                break;*/


        }
    }

    private void getHomeChange(String typenum, final RecyclerView view) {
        dialogShow();
        OkHttpUtils.get()
                .url(Constant.HOMECHANGE)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("typeNum", typenum)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialogDismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialogDismiss();
                        HomeChangeBean homeBean = JSON.parseObject(response, HomeChangeBean.class);
                        if (homeBean.getCode() == 200) {
                            List<HomeChangeBean.DataBean> data = homeBean.getData();
                            HomeChangerAdapter adapter = new HomeChangerAdapter(R.layout.test, data, getActivity(), false);
                            view.setAdapter(adapter);
                        } else {
                            Toast.makeText(getActivity(), homeBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeFragment.imageView.setVisibility(View.VISIBLE);
        boolean mobileConnected = isNetworkConnected(getActivity());
        if (!mobileConnected) {
            homeIv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            HomeFragment.imageView.setVisibility(View.VISIBLE);
            if (!isNetworkConnected(getActivity())) {
                homeIv.setVisibility(View.VISIBLE);
            }
        }
    }

}
