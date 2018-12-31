package com.example.administrator.chengnian444.fragment;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.*;


import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.activity.*;
import com.example.administrator.chengnian444.base.BaseFragment;
import com.example.administrator.chengnian444.bean.IsLockSafetyPwdResponse;
import com.example.administrator.chengnian444.bean.OneBannerBean;
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.bean.UserInfoResponse;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;

import butterknife.Bind;
import butterknife.OnClick;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

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
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    startActivity(new Intent(getActivity(), ShareExtensionActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.rl_cash_shenqing:
                if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")){
                    //判断 是否 已经设置 了安全密码
                    try {
                        UserBean userBean=UserDao.getLocalUser();
                        if(userBean.isSafeLocked){
                            //如果已经设置
                            startActivity(new Intent(getActivity(),CashWithdrawalActivity.class));
                        }else{
                            //如果sp文件中没有 进行请求网络 请求网络成功后 将状态 设置到 sp文件中
                            //TODO 此时 调用 接口 判断 安全密码是否设置
                            validateSecurityPassword(userBean.userName);
                        }
                    } catch (ContentException e) {
                        e.printStackTrace();
                    }
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
                    try {
                        //初始化用户
                        final UserBean userBean=UserDao.getLocalUser();
                        if(!userBean.isExtendistionState){
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
                                        if(ed_original_safe_pwd.getText().toString()
                                                        .matches(ConstantTips.EXTENDITION_CODE_REGEX)){
                                                //满足条件
                                            verifyPromoteCode(userBean.userName,ed_original_safe_pwd.getText().toString(),alertDialog);
                                        }else {
                                            ToastUtils.showToast(getActivity(), "推广码必须是6位数字");
                                        }
                                    }else{
                                        ToastUtils.showToast(getActivity(),"请输入邀请人推广码");
                                    }
                                }
                            });
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                        }else{
                           ToastUtils.showToast(getActivity(),"已绑定推广码");
                        }
                    } catch (ContentException e) {
                        e.printStackTrace();
                        //初始化用户
                        ToastUtils.showToast(getActivity(),ConstantTips.USER_NO_LOGIN);
                    }
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
        }
    }

    /**
     * 验证 推广码接口
     * @param userName
     * @param safepwd
     * @param alertDialog
     */
    private void   verifyPromoteCode(String userName, String safepwd, final AlertDialog alertDialog){
        OkHttpUtils.post().url(Constant.verifyPromoteCode)
                .addHeader("ContentType", "application/json")
                .addHeader("Authorization",SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("loginToken",SPUtils.getInstance(getActivity()).getString("loginToken"))
                .addParams("promoteCode",safepwd)
                .addParams("account",userName).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(getActivity(),"提交推广码失败!");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    IsLockSafetyPwdResponse isLockSafetyPwdResponse=
                            JSON.parseObject(response,IsLockSafetyPwdResponse.class);
                    if(isLockSafetyPwdResponse.getCode()==200){
                        ToastUtils.showToast(getActivity(),isLockSafetyPwdResponse.getMessage());
                        if(isLockSafetyPwdResponse.isData()){
                            //安全密码已经设置
                            UserBean userBean=UserDao.getLocalUser();
                            userBean.isExtendistionState=true;
                            UserDao.saveUpDate(userBean);
                            alertDialog.dismiss();
                        }else{
                            UserBean userBean=UserDao.getLocalUser();
                            userBean.isExtendistionState=false;
                            UserDao.saveUpDate(userBean);
                        }
                    }else{
                        ToastUtils.showToast(getActivity(),isLockSafetyPwdResponse.getMessage());
                    }
                } catch (Exception e) {
                    ToastUtils.showToast(getActivity(),"提交推广码失败!");
                }
            }
        });
    }
    /**
     * 访问  是否绑定安全密码
     * @param userName
     */
    private void validateSecurityPassword(String userName) {
        OkHttpUtils.post().url(Constant.validateSecurityPwd)
                .addHeader("ContentType", "application/json")
                .addHeader("Authorization",SPUtils.getInstance(getActivity()).getString("token"))
                .addParams("loginToken",SPUtils.getInstance(getActivity()).getString("loginToken"))
                .addParams("account",userName).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.showToast(getActivity(),"请求验证码失败!");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    IsLockSafetyPwdResponse isLockSafetyPwdResponse=
                            JSON.parseObject(response,IsLockSafetyPwdResponse.class);
                    if(isLockSafetyPwdResponse.getCode()==200){
                        ToastUtils.showToast(getActivity(),isLockSafetyPwdResponse.getMessage());
                        if(isLockSafetyPwdResponse.isData()){
                            //安全密码已经设置
                            UserBean userBean=UserDao.getLocalUser();
                            userBean.isSafeLocked=true;
                            UserDao.saveUpDate(userBean);
                            startActivity(new Intent(getActivity(),CashWithdrawalActivity.class));
                        }else{
                            UserBean userBean=UserDao.getLocalUser();
                            userBean.isSafeLocked=false;
                            UserDao.saveUpDate(userBean);
                            showNotSettingSafePwd();
                        }
                    }else{
                        ToastUtils.showToast(getActivity(),isLockSafetyPwdResponse.getMessage());
                    }
                } catch (Exception e) {
                    ToastUtils.showToast(getActivity(),"请求验证码失败!");
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeFragment.imageView.setVisibility(View.GONE);
        if (SPUtils.getInstance(getActivity()).getBoolean("isLogin")) {
            name.setText(SPUtils.getInstance(getActivity()).getString("name"));
            //对应的初始化数据
            initData();
            loginRegister.setVisibility(View.GONE);
            ll_mine_cash.setVisibility(View.VISIBLE);

            //进行访问网络
            getPromoteInfo();
        } else {
            name.setText("游客");
            ll_mine_cash.setVisibility(View.GONE);
            loginRegister.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取推广信息接口
     */
    private void getPromoteInfo() {
        try {
            OkHttpUtils.post()
                    .url(Constant.ACCOUNT_INFO)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", SPUtils.getInstance(getActivity()).getString("token"))
                    .addParams("loginToken", SPUtils.getInstance(getActivity()).getString("loginToken"))
                    .addParams("account", UserDao.getLocalUser().userName)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            UserInfoResponse oneBannerBean = JSON.parseObject(response, UserInfoResponse.class);
                            if (oneBannerBean.getCode() == 200) {
                                //为 200的响应码
                                float balance=oneBannerBean.getData().getBalance();
                                String promoteNum=oneBannerBean.getData().getPromoteNum();
                                String eqCodeUrl=oneBannerBean.getData().getEqCodeUrl();
                                boolean lockStatus=oneBannerBean.getData().getLockStatus().equals("1")?true:false;
                                try {
                                    UserBean userBean=UserDao.getLocalUser();
                                    userBean.totalBalance=balance;
                                    userBean.zcodeImgUrl=eqCodeUrl;
                                    userBean.isExtendistionState=lockStatus;
                                    userBean.extensitionCount=Integer.parseInt(promoteNum);
                                    UserDao.saveUpDate(userBean);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //重新初始化数据
                                initData();
                            }
                        }
                    });
        } catch (Exception e) {

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
