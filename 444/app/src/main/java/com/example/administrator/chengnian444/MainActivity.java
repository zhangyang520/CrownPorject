package com.example.administrator.chengnian444;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;

import com.alibaba.fastjson.JSON;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.administrator.chengnian444.activity.WebActivity;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.bean.VersionBean;
import com.example.administrator.chengnian444.fragment.HomeFragment;
import com.example.administrator.chengnian444.fragment.LoanFragment;
import com.example.administrator.chengnian444.fragment.MineFragment;
import com.example.administrator.chengnian444.fragment.MoveFragment;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarUtil;
import com.example.administrator.chengnian444.utils.VersionUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @Bind(R.id.main_bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationbar;
    private ArrayList<Fragment> mFragmentList;
    private ArrayList<Fragment> hasAddedFragment;
    private FragmentTransaction mTransaction;
    private Fragment mCurrFragment;
    private String vision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.black));
        ButterKnife.bind(this);
        // 获取本版本号，是否更新
        vision = getVersionName();
        initFragment();
        //版本信息
        getHttpVersion();
    }
    private String getVersionName()
    {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    private void getHttpVersion() {
        OkHttpUtils.post()
                .url(Constant.VERSIONBYTYPE)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("type","001android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("hcy",response.toString());
                        final VersionBean versionBean = JSON.parseObject(response, VersionBean.class);
                        if (versionBean.getCode()==200){
                            String newversion = versionBean.getData().getVersion();
                            Log.d("hcy",newversion);
                            Log.d("hcy", vision);
                            Log.d("hcy",versionBean.getData().getUrl());

                            int i = VersionUtil.compareVersion(newversion, vision);
                            if (i>0){
                                AlertDialog.Builder alertDialog =new AlertDialog.Builder(MainActivity.this);
                                alertDialog.setMessage("检测到有最新版本，是否更新");
                                alertDialog.setCancelable(false);
                                alertDialog.setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(MainActivity.this,WebActivity.class);
                                        intent.putExtra("url",versionBean.getData().getUrl());
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog dialog = alertDialog.create();
                                dialog.show();
                            }
                        }
                    }
                });
    }

    private void initFragment() {

        mBottomNavigationbar
                .addItem(new BottomNavigationItem(R.mipmap.home2, "首页").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.home1)))
                .addItem(new BottomNavigationItem(R.mipmap.player2, "影库").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.player)))
                .addItem(new BottomNavigationItem(R.mipmap.move2, "精品").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.move1)))
                .addItem(new BottomNavigationItem(R.mipmap.me2, "我的").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.me1)))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationbar.setTabSelectedListener(this);

        mFragmentList = new ArrayList<>();
        hasAddedFragment = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new LoanFragment());
        mFragmentList.add(new MoveFragment());
        mFragmentList.add(new MineFragment());


        //默认fragment
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.main_fragment_container,  mFragmentList.get(0));
        mTransaction.commit();
        mCurrFragment =  mFragmentList.get(0);
        hasAddedFragment.add(mFragmentList.get(0));
    }

    @Override
    public void onTabSelected(int position) {
        addShowHideFragment( mFragmentList.get(position));
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void addShowHideFragment(Fragment fragment) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrFragment != null) {
            mTransaction.hide(mCurrFragment);
        }
        if (hasAddedFragment.contains(fragment)) {
            mTransaction.show(fragment);
        } else {
            mTransaction.add(R.id.main_fragment_container, fragment);
            hasAddedFragment.add(fragment);
        }
        mTransaction.commit();
        mCurrFragment = fragment;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}