package com.example.administrator.chengnian933.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.activity.LoginActivity;
import com.example.administrator.chengnian933.activity.SettingActivity;
import com.example.administrator.chengnian933.activity.YingYongActivity;
import com.example.administrator.chengnian933.base.BaseFragment;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.example.administrator.chengnian933.utils.StatusBarCompat.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {

    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.login_register)
    ImageView loginRegister;
    @Bind(R.id.yingyong)
    RelativeLayout yingyong;
    @Bind(R.id.setting)
    RelativeLayout setting;
    @Bind(R.id.name)
    TextView name;
   /* @Bind(R.id.banner)
    ImageView banner;*/

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View childView) {
        StatusBarCompat.setStatusBarColor(getActivity(),getResources().getColor(R.color.black));
       // banner.setImageDrawable(getResources().getDrawable(R.mipmap.me933));
    }


    @OnClick({R.id.login_register, R.id.yingyong, R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_register:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.yingyong:
                startActivity(new Intent(getActivity(), YingYongActivity.class));
                break;
            case R.id.setting:
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }

                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeFragment.imageView.setVisibility(View.GONE);
        if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")) {
            name.setText(SPUtils.getInstance(getActivity()).getString("name"));
            loginRegister.setVisibility(View.GONE);
        } else {
            name.setText("游客");
            loginRegister.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            HomeFragment.imageView.setVisibility(View.GONE);
        }
    }
}
