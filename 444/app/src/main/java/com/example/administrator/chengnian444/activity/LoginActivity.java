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
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
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
                .url(Constant.REGISTERISOPEN)
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
                            Toast.makeText(LoginActivity.this,registerIsOpen.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @OnClick({R.id.login, R.id.register,R.id.look,R.id.forget_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                String phone = etPhone.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                httpLogin(phone, pwd);

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

    private void httpLogin(String phone, String pwd) {
        OkHttpUtils.post()
                .url(Constant.LOGIN)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("account", phone)
                .addParams("password", pwd)
                .addParams("appType","001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

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
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
