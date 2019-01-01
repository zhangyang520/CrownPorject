package com.example.administrator.chengnian444.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.administrator.chengnian444.MainActivity;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.bean.LoginBean;
import com.example.administrator.chengnian444.bean.RegisterIsOpen;
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


/**
 * Created by Administrator on 2018/8/26.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.forget_pwd)
    TextView forgetPwd;
    @Bind(R.id.look)
    ImageView look;

    @Bind(R.id.iv_top)
    ImageView iv_top;
    private LoginBean loginBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.main_bg_color));
        ButterKnife.bind(this);
        Glide.with(this).load(R.mipmap.login0).fitCenter().into(iv_top);
        httpIsOpen();
    }

    private void httpIsOpen() {
        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.REGISTERISOPEN)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("appType","001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("hcy",response.toString());
                        RegisterIsOpen registerIsOpen = JSON.parseObject(response, RegisterIsOpen.class);
                        if (registerIsOpen.getCode()==200){
                            RegisterIsOpen.DataBean data = registerIsOpen.getData();
                            String isOpen = data.getIsOpen();
                            if ("1".equals(isOpen)){
                                register.setVisibility(View.GONE);
                            }else if ("0".equals(isOpen)){
                                register.setVisibility(View.VISIBLE);
                            }
                        }else {
                            ToastUtils.showToast(LoginActivity.this,registerIsOpen.getMessage());
                        }
                    }
                });
    }

    @OnClick({R.id.login, R.id.register,R.id.look,R.id.forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                if(verifyLogin()){
                    String phone = etPhone.getText().toString().trim();
                    String pwd = etPwd.getText().toString().trim();
                    login.setClickable(false);
                    httpLogin(phone, pwd);
                }
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.forget_pwd:
                startActivity(new Intent(LoginActivity.this, ForgetPwdActivity.class));
                break;
            case  R.id.look:
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                break;

        }
    }

    /**
     * 验证登录的信息
     * @return
     */
    private boolean verifyLogin() {
        //先进行判断 是否为空
        if(!etPhone.getText().toString().equals("") &&
                     !etPwd.getText().toString().equals("")){
             //都不为空
            if(!etPhone.getText().toString().matches(ConstantTips.LOGIN_USER_NAME_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,"登录的账号不能为空");
                return false;
            }

            //验证 输入密码格式的正确性
            if(!etPwd.getText().toString().matches(ConstantTips.LOGIN_PWD_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.PWD_FORMATE_ERROR);
                return false;
            }
            return true;
        }else{
            //输入的信息不完整
            if(etPhone.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,etPhone.getHint().toString());
                return false;
            }
            //输入的信息不完整
            if(etPwd.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,etPwd.getHint().toString());
                return false;
            }
        }
        return true;
    }

    private void httpLogin(String phone, String pwd) {
        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.LOGIN)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("account", phone)
                .addParams("password", pwd)
                .addParams("appType","001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        login.setClickable(true);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        JSONObject jsonObject = JSON.parseObject(response);
                        int code = (int) jsonObject.get("code");
                        String message = (String) jsonObject.get("message");
                        if (code == 200) {
                            loginBean = JSON.parseObject(response, LoginBean.class);
                            SPUtils.getInstance(LoginActivity.this).put("isLogin", true);
                            SPUtils.getInstance(LoginActivity.this).put("name", loginBean.getData().getAccount());
                            SPUtils.getInstance(LoginActivity.this).put("loginToken", loginBean.getData().getLoginToken());

                            try {

                                UserBean userBean=UserDao.getUserToken(loginBean.getData().getLoginToken());
                                userBean.totalBalance=1000;
                                userBean.extendistinCode="123456";
                                userBean.extensitionCount=56;
                                userBean.firstPromotionBenfits=200;
                                userBean.secondPormotionBenfits=300;
                                userBean.thirdPromotionBenfits=400;
                                userBean.loginToken=loginBean.getData().getLoginToken();
                                userBean.safePwd="1234567";
                                //总金额
                                userBean.totalCash=2000;
                                userBean.userName=loginBean.getData().getAccount();
                                UserDao.updateAllUserLocalState(false);
                                userBean.isLocalUser=true;
                                UserDao.saveUpDate(userBean);
                            } catch (ContentException e) {
                                e.printStackTrace();
                                UserBean userBean=new UserBean();
                                //余额
                                userBean.totalBalance=1000;
                                userBean.extendistinCode="123456";
                                userBean.extensitionCount=56;
                                userBean.firstPromotionBenfits=200;
                                userBean.secondPormotionBenfits=300;
                                userBean.thirdPromotionBenfits=400;
                                userBean.loginToken=loginBean.getData().getLoginToken();
                                userBean.safePwd="1234567";
                                //总金额
                                userBean.totalCash=2000;
                                userBean.userName=loginBean.getData().getAccount();
                                UserDao.updateAllUserLocalState(false);
                                userBean.isLocalUser=true;
                                UserDao.saveUpDate(userBean);
                            }
                            //首先进行判断 该用户的token 是否存在
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            ToastUtils.showToast(LoginActivity.this,message);
                        }
                    }
                });
    }
}
