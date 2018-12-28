package com.example.administrator.chengnian444.fragment;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.*;


import com.bumptech.glide.Glide;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.activity.*;
import com.example.administrator.chengnian444.base.BaseFragment;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;

import butterknife.Bind;
import butterknife.OnClick;
import com.example.administrator.chengnian444.utils.ToastUtils;

public class MineFragment extends BaseFragment {

    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.login_register)
    ImageView loginRegister;
    @Bind(R.id.rl_app_recommend)
    RelativeLayout yingyong;
    @Bind(R.id.name)
    TextView name;

    @Bind(R.id.iv_setting)
    ImageView iv_setting;

    @Bind(R.id.ll_mine_cash)
    LinearLayout ll_mine_cash;

    @Bind(R.id.iv_make_money)
    ImageView iv_make_money;

    @Override
    protected int getContentLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View childView) {
        StatusBarCompat.setStatusBarColor(getActivity(),getResources().getColor(R.color.black));
        //banner.setImageDrawable(getResources().getDrawable(R.mipmap.down));
        Glide.with(getActivity()).load(R.mipmap.login_bg).fitCenter().into(iv);
    }


    @OnClick({R.id.login_register, R.id.rl_app_recommend, R.id.iv_setting,R.id.iv_make_money,
              R.id.rl_share,R.id.rl_cash_shenqing,R.id.rl_safety_pwd,R.id.rl_extension})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_register:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.rl_app_recommend:
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    startActivity(new Intent(getActivity(), YingYongActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.iv_setting:
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.iv_make_money:
                  startActivity(new Intent(getActivity(), MakeMoneyActivity.class));
                break;

            case R.id.rl_share:
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    startActivity(new Intent(getActivity(), ShareExtensionActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.rl_cash_shenqing:
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    startActivity(new Intent(getActivity(),CashWithdrawalActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.rl_safety_pwd:
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    startActivity(new Intent(getActivity(),SafetyPwdActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.rl_extension:
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    //修改密码 弹出对应的对话框
                    View contentView=View.inflate(getActivity(),R.layout.dialog_generalization_code,null);
                    final AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setView(contentView).create();

                    AppCompatImageView iv_mine_cancel=contentView.findViewById(R.id.iv_mine_cancel);
                    iv_mine_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });
                    Button btn_cancel = contentView.findViewById(R.id.btn_cancel);
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    });

                    final EditText ed_original_safe_pwd=contentView.findViewById(R.id.ed_original_safe_pwd);

                    Button btn_ok=contentView.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!ed_original_safe_pwd.getText().toString().trim().equals("")){
                                //如果不为空
                                ToastUtils.showToast(getActivity(), "");
                                alertDialog.dismiss();
                            }else{
                                ToastUtils.showToast(getActivity(),"请输入邀请人推广码");
                            }
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
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
            ll_mine_cash.setVisibility(View.VISIBLE);
        } else {
            name.setText("游客");
            ll_mine_cash.setVisibility(View.GONE);
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
