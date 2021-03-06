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
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
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

    @Bind(R.id.tv_total_cash)
    TextView tv_total_cashl;

    @Bind(R.id.tv_extension_count)
    TextView tv_extension_count;

    @Bind(R.id.tv_extendition_tips)
    TextView  tv_extendition_tips;

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


    /**
     * 初始化数据
     */
    private void initData() {
        try {
            //初始化用户
            UserBean userBean=UserDao.getLocalUser();
            tv_total_cashl.setText(userBean.totalCash+"");
            tv_extension_count.setText(userBean.extensitionCount+"");
            if(userBean.isExtendistionState){
                //如果已经绑定
                tv_extendition_tips.setText("已绑定推广码");
            }else{
                tv_extendition_tips.setText("新用户即得现金");
            }
        } catch (ContentException e) {
            e.printStackTrace();
            //初始化用户
//            ToastUtils.showToast(getActivity(),ConstantTips.USER_NO_LOGIN);
        }
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
                ToastUtils.showToast(getActivity(),"开发中,敬请期待");
//                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
//                    startActivity(new Intent(getActivity(), ShareExtensionActivity.class));
//                }else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
                break;

            case R.id.rl_cash_shenqing:
                ToastUtils.showToast(getActivity(),"开发中,敬请期待");
//                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
//                    //判断 是否 已经设置 了安全密码
//                    //TODO 此时 调用 接口 判断 安全密码是否设置
//                    if(SPUtils.getInstance(getActivity()).getBoolean(ConstantTips.isSettingSafePwd)){
//                        //如果已经设置
//                        startActivity(new Intent(getActivity(),CashWithdrawalActivity.class));
//                    }else{
//                        //如果sp文件中没有 进行请求网络 请求网络成功后 将状态 设置到 sp文件中
//                        showNotSettingSafePwd();
//                    }
//                }else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
                break;

            case R.id.rl_safety_pwd:
                ToastUtils.showToast(getActivity(),"开发中,敬请期待");
//                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
//                    startActivity(new Intent(getActivity(),SafetyPwdActivity.class));
//                }else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
                break;

            case R.id.rl_extension:
                ToastUtils.showToast(getActivity(),"开发中,敬请期待");
//                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
//                    //修改密码 弹出对应的对话框
//                    try {
//                        //初始化用户
//                        UserBean userBean=UserDao.getLocalUser();
//
//                        if(!userBean.isExtendistionState){
//                            View contentView=View.inflate(getActivity(),R.layout.dialog_generalization_code,null);
//                            final AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setView(contentView).create();
//
//                            AppCompatImageView iv_mine_cancel=contentView.findViewById(R.id.iv_mine_cancel);
//                            iv_mine_cancel.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    alertDialog.dismiss();
//                                }
//                            });
//                            Button btn_cancel = contentView.findViewById(R.id.btn_cancel);
//                            btn_cancel.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    alertDialog.dismiss();
//                                }
//                            });
//
//                            final EditText ed_original_safe_pwd=contentView.findViewById(R.id.ed_original_safe_pwd);
//
//                            Button btn_ok=contentView.findViewById(R.id.btn_ok);
//                            btn_ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    if(!ed_original_safe_pwd.getText().toString().trim().equals("")){
//                                        //如果不为空
//                                        ToastUtils.showToast(getActivity(), "");
//                                        alertDialog.dismiss();
//                                    }else{
//                                        ToastUtils.showToast(getActivity(),"请输入邀请人推广码");
//                                    }
//                                }
//                            });
//                            alertDialog.setCancelable(false);
//                            alertDialog.show();
//                        }else{
//                           ToastUtils.showToast(getActivity(),"已绑定推广码");
//                        }
//                    } catch (ContentException e) {
//                        e.printStackTrace();
//                        //初始化用户
//                        ToastUtils.showToast(getActivity(),ConstantTips.USER_NO_LOGIN);
//                    }
//                }else {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeFragment.imageView.setVisibility(View.GONE);
        if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")) {
            name.setText(SPUtils.getInstance(getActivity()).getString("name"));
            //对应的初始化数据
//            initData();
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


    /**
     * 提示 请先设置 安全密码的提示框
     */
    public void showNotSettingSafePwd(){
        //修改密码 弹出对应的对话框
        View contentView=View.inflate(getActivity(),R.layout.dialog_first_setting_safepwd,null);
        final AlertDialog alertDialog=new AlertDialog.Builder(getActivity()).setView(contentView).create();
        Button btn_ok=contentView.findViewById(R.id.btn_ok);
        Button btn_cancel=contentView.findViewById(R.id.btn_cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                //点击确定 跳转到 设置安全密码界面
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    //todo  以后要修改！
                    SPUtils.getInstance(getActivity()).put(ConstantTips.isSettingSafePwd,true);

                    startActivity(new Intent(getActivity(),SafetyPwdActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
