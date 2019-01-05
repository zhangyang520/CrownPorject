package com.example.administrator.chengnian444.fragment;

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
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.activity.MoveListActivity;
import com.example.administrator.chengnian444.activity.WebActivity;
import com.example.administrator.chengnian444.adapter.HomeChangerAdapter;
import com.example.administrator.chengnian444.adapter.TextAdapter;
import com.example.administrator.chengnian444.base.BaseFragment;
import com.example.administrator.chengnian444.bean.BannerBean;
import com.example.administrator.chengnian444.bean.HomeBean;
import com.example.administrator.chengnian444.bean.HomeChangeBean;
import com.example.administrator.chengnian444.bean.OneBannerBean;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.example.administrator.chengnian444.view.SonnyJackDragView;
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
    ImageView tv1;
    @Bind(R.id.tv2)
    ImageView tv2;
    @Bind(R.id.tv3)
    ImageView tv3;
    @Bind(R.id.tv4)
    ImageView tv4;
    @Bind(R.id.tv5)
    ImageView tv5;
    @Bind(R.id.tv6)
    ImageView tv6;
    @Bind(R.id.tv7)
    ImageView tv7;
    @Bind(R.id.tv8)
    ImageView tv8;
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
    TextView goucai;
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

       System.out.print("HomeFragment initView  1111");
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

    private void getHongBao() {
        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.GETONEBANNER)
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("loginToken", SPUtils.getInstance(getActivity()).getString("loginToken"))
                .addParams("appType",  Constant.platform_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        OneBannerBean oneBannerBean = JSON.parseObject(response, OneBannerBean.class);
                        if (oneBannerBean.getCode() == 200) {
                            if(oneBannerBean.getData()!=null && oneBannerBean.getData().getUrl()!=null){
                                String url = oneBannerBean.getData().getUrl();
                                Intent intent = new Intent(getActivity(), WebActivity.class);
                                intent.putExtra("url", url);
                                startActivity(intent);
                            }else{
                                ToastUtils.showToast(getActivity(), "暂无数据!");
                            }
                        }else if (oneBannerBean.getCode() == 301){
                            exitDialog();
                        }
                    }
                });
    }


    public void getHomeList() {
        dialogShow();
        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.HOMELIST)
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("loginToken", SPUtils.getInstance(getActivity()).getString("loginToken"))
                .addParams("appType", Constant.platform_id)
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
                        Log.d("hcy", response.toString());
                        homeBean = JSON.parseObject(response, HomeBean.class);
                        if (homeBean.getCode() == 200) {
                            List<HomeBean.DataBean.LiveMoviesBean> data = homeBean.getData().get(0).getLiveMovies();
                            //经典三级
                            Glide.with(getActivity()).load(getTitleImageId(homeBean.getData().get(0).getMovieType())).centerCrop().into(tv1);
                            adapter = new TextAdapter(R.layout.test, data, getActivity());
                            chinaOn.setAdapter(adapter);
                            Glide.with(getActivity()).load(homeBean.getData().get(0).getImage()).placeholder(R.mipmap.zhanwei3).centerCrop().into(image1);
                            url = homeBean.getData().get(0).getUrl();
                            SPUtils.getInstance(getActivity()).put("homebanner", url);

                            //卡通动漫
                            List<HomeBean.DataBean.LiveMoviesBean> data1 = homeBean.getData().get(1).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(1).getImage()).placeholder(R.mipmap.zhanwei3).centerCrop().into(image2);
                            Glide.with(getActivity()).load(getTitleImageId(homeBean.getData().get(1).getMovieType())).centerCrop().into(tv2);
                            adapter = new TextAdapter(R.layout.test, data1, getActivity());
                            recycler1.setAdapter(adapter);

                            //偷拍自拍
                            List<HomeBean.DataBean.LiveMoviesBean> data2 = homeBean.getData().get(2).getLiveMovies();
                            Glide.with(getActivity()).load(getTitleImageId(homeBean.getData().get(2).getMovieType())).centerCrop().into(tv3);
                            adapter = new TextAdapter(R.layout.test, data2, getActivity());
                            recycler2.setAdapter(adapter);
                            Glide.with(getActivity()).load(homeBean.getData().get(2).getImage()).placeholder(R.mipmap.zhanwei3).centerCrop().into(image3);

                            //欧美五码
                            List<HomeBean.DataBean.LiveMoviesBean> data3 = homeBean.getData().get(3).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(3).getImage()).placeholder(R.mipmap.zhanwei3).centerCrop().into(image4);
                            Glide.with(getActivity()).load(getTitleImageId(homeBean.getData().get(3).getMovieType())).centerCrop().into(tv4);
                            adapter = new TextAdapter(R.layout.test, data3, getActivity());
                            recycler3.setAdapter(adapter);

                            //中文五码
                            List<HomeBean.DataBean.LiveMoviesBean> data4 = homeBean.getData().get(4).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(4).getImage()).placeholder(R.mipmap.zhanwei3).centerCrop().into(image5);
                            Glide.with(getActivity()).load(getTitleImageId(homeBean.getData().get(4).getMovieType())).centerCrop().into(tv5);
                            adapter = new TextAdapter(R.layout.test, data4, getActivity());
                            recycler4.setAdapter(adapter);

                            //中文有吗
                            List<HomeBean.DataBean.LiveMoviesBean> data5 = homeBean.getData().get(5).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(5).getImage()).placeholder(R.mipmap.zhanwei3).centerCrop().into(image6);
                            Glide.with(getActivity()).load(getTitleImageId(homeBean.getData().get(5).getMovieType())).centerCrop().into(tv6);
                            adapter = new TextAdapter(R.layout.test, data5, getActivity());
                            recycler5.setAdapter(adapter);

                            //日本有吗
                            List<HomeBean.DataBean.LiveMoviesBean> data6 = homeBean.getData().get(6).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(6).getImage()).placeholder(R.mipmap.zhanwei3).centerCrop().into(image7);
                            Glide.with(getActivity()).load(getTitleImageId(homeBean.getData().get(6).getMovieType())).centerCrop().into(tv7);
                            adapter = new TextAdapter(R.layout.test, data6, getActivity());
                            recycler6.setAdapter(adapter);

                            //日本五码
                            List<HomeBean.DataBean.LiveMoviesBean> data7 = homeBean.getData().get(7).getLiveMovies();
                            Glide.with(getActivity()).load(homeBean.getData().get(7).getImage()).placeholder(R.mipmap.zhanwei3).centerCrop().into(image8);
                            Glide.with(getActivity()).load(getTitleImageId(homeBean.getData().get(7).getMovieType())).centerCrop().into(tv8);
                            adapter = new TextAdapter(R.layout.test, data7, getActivity());
                            recycler7.setAdapter(adapter);

                        } else if (homeBean.getCode()==301){
                            exitDialog();
                        }else {
                            ToastUtils.showToast(getActivity(), homeBean.getMessage());
                        }
                    }
                });
    }


    /**
     * 1 三级剧情
     *  2 卡通动漫
     *   3 偷拍自拍
     *    4 欧美无码
     *      5 中文无码
     *       6 中文有码
     *        7 日本有码
     *         8 日本无码
     * @param movieType
     * @return
     */
    private int getTitleImageId(String movieType){
        System.out.println("getTitleImageId:"+movieType);
        if("1".equals(movieType)){
            return R.mipmap.iv_shouye_type1;
        }else if("2".equals(movieType)){
            return R.mipmap.iv_shouye_type2;
        }else if("3".equals(movieType)){
            return R.mipmap.iv_shouye_type3;
        }else if("4".equals(movieType)){
            return R.mipmap.iv_shouye_type4;
        }else if("5".equals(movieType)){
            return R.mipmap.iv_shouye_type5;
        }else if("6".equals(movieType)){
            return R.mipmap.iv_shouye_type6;
        }else if("7".equals(movieType)){
            return R.mipmap.iv_shouye_type7;
        }else if("8".equals(movieType)){
            return R.mipmap.iv_shouye_type8;
        }
          return R.mipmap.iv_shouye_type1;
    }

    private void getHomeBanner() {
        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.BANNER)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("type", "10001")
                .addParams("appType", "001")
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
                            ToastUtils.showToast(getActivity(), bannerBean.getMessage());
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
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=1){
                    if(homeBean.getData().get(0).getMovieType()!=null){
                        getHomeChange(homeBean.getData().get(0).getMovieType(), chinaOn);
                    }else{
                        getHomeChange("1", chinaOn);
                    }
                }else{
                  getHomeChange("1", chinaOn);
                }
                break;

            case R.id.change2:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=2){
                    if(homeBean.getData().get(1).getMovieType()!=null){
                        getHomeChange(homeBean.getData().get(1).getMovieType(), recycler1);
                    }else{
                        getHomeChange("2", recycler1);
                    }
                }else{
                    getHomeChange("2", recycler1);
                }
                break;

            case R.id.change3:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=3){
                    if(homeBean.getData().get(2).getMovieType()!=null){
                        getHomeChange(homeBean.getData().get(2).getMovieType(),recycler2);
                    }else{
                        getHomeChange("3", recycler2);
                    }
                }else{
                    getHomeChange("3", recycler2);
                }
                break;

            case R.id.change4:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=4){
                    if(homeBean.getData().get(3).getMovieType()!=null){
                        getHomeChange(homeBean.getData().get(3).getMovieType(),recycler3);
                    }else{
                        getHomeChange("4", recycler3);
                    }
                }else{
                    getHomeChange("4", recycler3);
                }
                break;

            case R.id.change5:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=5){
                    if(homeBean.getData().get(4).getMovieType()!=null){
                        getHomeChange(homeBean.getData().get(4).getMovieType(),recycler4);
                    }else{
                        getHomeChange("5", recycler4);
                    }
                }else{
                    getHomeChange("5", recycler4);
                }
                break;

            case R.id.change6:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=6){
                    if(homeBean.getData().get(5).getMovieType()!=null){
                        getHomeChange(homeBean.getData().get(5).getMovieType(),recycler5);
                    }else{
                        getHomeChange("6", recycler5);
                    }
                }else{
                    getHomeChange("6", recycler5);
                }
                break;

            case R.id.change7:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=7){
                    if(homeBean.getData().get(6).getMovieType()!=null){
                        getHomeChange(homeBean.getData().get(6).getMovieType(),recycler6);
                    }else{
                        getHomeChange("7", recycler6);
                    }
                }else{
                    getHomeChange("7", recycler6);
                }
                break;

            case R.id.change8:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=8){
                    if(homeBean.getData().get(7).getMovieType()!=null){
                        getHomeChange(homeBean.getData().get(7).getMovieType(),recycler7);
                    }else{
                        getHomeChange("8", recycler7);
                    }
                }else{
                    getHomeChange("8", recycler7);
                }
                break;

            case R.id.image1:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=1){
                    Intent intent16 = new Intent(getActivity(), WebActivity.class);
                    intent16.putExtra("url", homeBean.getData().get(0).getUrl());
                    startActivity(intent16);
                }else{
                    ToastUtils.showToast(getContext(),"暂无数据");
                }
                break;

            case R.id.image2:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=2){
                    Intent intent15 = new Intent(getActivity(), WebActivity.class);
                    intent15.putExtra("url", homeBean.getData().get(1).getUrl());
                    startActivity(intent15);
                }else{
                    ToastUtils.showToast(getContext(),"暂无数据");
                }
                break;

            case R.id.image3:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=3){
                    Intent intent14 = new Intent(getActivity(), WebActivity.class);
                    intent14.putExtra("url", homeBean.getData().get(2).getUrl());
                    startActivity(intent14);
                }else{
                    ToastUtils.showToast(getContext(),"暂无数据");
                }
                break;

            case R.id.image4:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=4){
                    Intent intent13 = new Intent(getActivity(), WebActivity.class);
                    intent13.putExtra("url", homeBean.getData().get(3).getUrl());
                    startActivity(intent13);
                }else{
                    ToastUtils.showToast(getContext(),"暂无数据");
                }
                break;

            case R.id.image5:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=5){
                    Intent intent12 = new Intent(getActivity(), WebActivity.class);
                    intent12.putExtra("url", homeBean.getData().get(4).getUrl());
                    startActivity(intent12);
                }else{
                    ToastUtils.showToast(getContext(),"暂无数据");
                }
                break;

            case R.id.image6:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=6){
                    Intent intent11 = new Intent(getActivity(), WebActivity.class);
                    intent11.putExtra("url", homeBean.getData().get(5).getUrl());
                    startActivity(intent11);
                }else{
                    ToastUtils.showToast(getContext(),"暂无数据");
                }
                break;

            case R.id.image7:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=7){
                    Intent intent10 = new Intent(getActivity(), WebActivity.class);
                    intent10.putExtra("url", homeBean.getData().get(6).getUrl());
                    startActivity(intent10);
                }else{
                    ToastUtils.showToast(getContext(),"暂无数据");
                }
                break;

            case R.id.image8:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=8){
                    Intent intent9 = new Intent(getActivity(), WebActivity.class);
                    intent9.putExtra("url", homeBean.getData().get(7).getUrl());
                    startActivity(intent9);
                }else{
                    ToastUtils.showToast(getContext(),"暂无数据");
                }
                break;

            case R.id.goucai:
                Intent intent17 = new Intent(getActivity(), WebActivity.class);
                intent17.putExtra("url", url);
                startActivity(intent17);
                break;

            case R.id.more1:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=1){
                    if( homeBean.getData().get(0).getMovieType()!=null &&
                                    homeBean.getData().get(0).getTypeName()!=null){
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", homeBean.getData().get(0).getMovieType());
                        intent1.putExtra("title", homeBean.getData().get(0).getTypeName());
                        startActivity(intent1);
                    }else{
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", "1");
                        intent1.putExtra("title", "三級劇情");
                        startActivity(intent1);
                    }

                }else{
                    Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                    intent1.putExtra("typenum", "1");
                    intent1.putExtra("title", "三級劇情");
                    startActivity(intent1);
                }
                break;

            case R.id.more2:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=2){
                    if( homeBean.getData().get(1).getMovieType()!=null &&
                            homeBean.getData().get(1).getTypeName()!=null){
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", homeBean.getData().get(1).getMovieType());
                        intent1.putExtra("title", homeBean.getData().get(1).getTypeName());
                        startActivity(intent1);
                    }else{
                        Intent intent2 = new Intent(getActivity(), MoveListActivity.class);
                        intent2.putExtra("typenum", "2");
                        intent2.putExtra("title", "卡通动漫");
                        startActivity(intent2);
                    }
                }else{
                    Intent intent2 = new Intent(getActivity(), MoveListActivity.class);
                    intent2.putExtra("typenum", "2");
                    intent2.putExtra("title", "卡通动漫");
                    startActivity(intent2);
                }
                break;

            case R.id.more3:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=3){
                    if( homeBean.getData().get(2).getMovieType()!=null &&
                            homeBean.getData().get(2).getTypeName()!=null){
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", homeBean.getData().get(2).getMovieType());
                        intent1.putExtra("title", homeBean.getData().get(2).getTypeName());
                        startActivity(intent1);
                    }else{
                        Intent intent3 = new Intent(getActivity(), MoveListActivity.class);
                        intent3.putExtra("typenum", "3");
                        intent3.putExtra("title", "偷拍自拍");
                        startActivity(intent3);
                    }
                }else{
                    Intent intent3 = new Intent(getActivity(), MoveListActivity.class);
                    intent3.putExtra("typenum", "3");
                    intent3.putExtra("title", "偷拍自拍");
                    startActivity(intent3);
                }
                break;

            case R.id.more4:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=4){
                    if( homeBean.getData().get(3).getMovieType()!=null &&
                            homeBean.getData().get(3).getTypeName()!=null){
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", homeBean.getData().get(3).getMovieType());
                        intent1.putExtra("title", homeBean.getData().get(3).getTypeName());
                        startActivity(intent1);
                    }else{
                        Intent intent4 = new Intent(getActivity(), MoveListActivity.class);
                        intent4.putExtra("typenum", "4");
                        intent4.putExtra("title", "歐美無碼");
                        startActivity(intent4);
                    }
                }else{
                    Intent intent4 = new Intent(getActivity(), MoveListActivity.class);
                    intent4.putExtra("typenum", "4");
                    intent4.putExtra("title", "歐美無碼");
                    startActivity(intent4);
                }
                break;

            case R.id.more5:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=5){
                    if( homeBean.getData().get(4).getMovieType()!=null &&
                            homeBean.getData().get(4).getTypeName()!=null){
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", homeBean.getData().get(4).getMovieType());
                        intent1.putExtra("title", homeBean.getData().get(4).getTypeName());
                        startActivity(intent1);
                    }else{
                        Intent intent5 = new Intent(getActivity(), MoveListActivity.class);
                        intent5.putExtra("typenum", "5");
                        intent5.putExtra("title", "中文無碼");
                        startActivity(intent5);
                    }
                }else{
                    Intent intent5 = new Intent(getActivity(), MoveListActivity.class);
                    intent5.putExtra("typenum", "5");
                    intent5.putExtra("title", "中文無碼");
                    startActivity(intent5);
                }
                break;

            case R.id.more6:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=6){
                    if( homeBean.getData().get(5).getMovieType()!=null &&
                            homeBean.getData().get(5).getTypeName()!=null){
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", homeBean.getData().get(5).getMovieType());
                        intent1.putExtra("title", homeBean.getData().get(5).getTypeName());
                        startActivity(intent1);
                    }else{
                        Intent intent6 = new Intent(getActivity(), MoveListActivity.class);
                        intent6.putExtra("typenum", "6");
                        intent6.putExtra("title", "中文有碼");
                        startActivity(intent6);
                    }
                }else{
                    Intent intent6 = new Intent(getActivity(), MoveListActivity.class);
                    intent6.putExtra("typenum", "6");
                    intent6.putExtra("title", "中文有碼");
                    startActivity(intent6);
                }
                break;

            case R.id.more7:
                if(homeBean!=null &&  homeBean.getData()!=null && homeBean.getData().size()>=7){
                    if( homeBean.getData().get(6).getMovieType()!=null &&
                            homeBean.getData().get(6).getTypeName()!=null){
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", homeBean.getData().get(6).getMovieType());
                        intent1.putExtra("title", homeBean.getData().get(6).getTypeName());
                        startActivity(intent1);
                    }else{
                        Intent intent7 = new Intent(getActivity(), MoveListActivity.class);
                        intent7.putExtra("typenum", "7");
                        intent7.putExtra("title", "日本有碼");
                        startActivity(intent7);
                    }
                }else{
                    Intent intent7 = new Intent(getActivity(), MoveListActivity.class);
                    intent7.putExtra("typenum", "7");
                    intent7.putExtra("title", "日本有碼");
                    startActivity(intent7);
                }
                break;

            case R.id.more8:
                if(homeBean!=null && homeBean.getData()!=null && homeBean.getData().size()>=8){
                    if( homeBean.getData().get(7).getMovieType()!=null &&
                            homeBean.getData().get(7).getTypeName()!=null){
                        Intent intent1 = new Intent(getActivity(), MoveListActivity.class);
                        intent1.putExtra("typenum", homeBean.getData().get(7).getMovieType());
                        intent1.putExtra("title", homeBean.getData().get(7).getTypeName());
                        startActivity(intent1);
                    }else{
                        Intent intent8 = new Intent(getActivity(), MoveListActivity.class);
                        intent8.putExtra("typenum", "8");
                        intent8.putExtra("title", "日本无碼");
                        startActivity(intent8);
                    }
                }else{
                    Intent intent8 = new Intent(getActivity(), MoveListActivity.class);
                    intent8.putExtra("typenum", "8");
                    intent8.putExtra("title", "日本无碼");
                    startActivity(intent8);
                }
                break;
           /* case R.id.qianghongbao:
                Toast.makeText(getActivity(), "抢红包", Toast.LENGTH_SHORT).show();
                break;*/


        }
    }

    private void getHomeChange(String typenum, final RecyclerView view) {
        dialogShow();
        OkHttpUtils.get()
                .url(Constant.BASEURL+Constant.HOMECHANGE)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("loginToken", SPUtils.getInstance(getActivity()).getString("loginToken"))
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
                        } else if (homeBean.getCode() == 301){
                            exitDialog();
                        }else {
                            ToastUtils.showToast(getActivity(), homeBean.getMessage());
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
        }else{
            homeIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            HomeFragment.imageView.setVisibility(View.VISIBLE);
            if (!isNetworkConnected(getActivity())) {
                homeIv.setVisibility(View.VISIBLE);
            }else{
                homeIv.setVisibility(View.GONE);
            }
        }
    }
}
