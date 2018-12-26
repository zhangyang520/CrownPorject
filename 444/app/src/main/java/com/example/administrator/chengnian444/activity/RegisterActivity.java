package com.example.administrator.chengnian444.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.chengnian444.MainActivity;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.bean.MessageCodeBean;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.Validator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class RegisterActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.get_code)
    Button getCode;
    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.btn_register)
    Button btnRegister;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.main_bg_color));
    }

    @OnClick({R.id.back, R.id.get_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.get_code:
                //获取短信验证码
                String et_code = etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(et_code)){
                    Toast.makeText(this,"请输入手机号码",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Validator.isMobile(et_code)){
                    OkHttpUtils.post().
                            url(Constant.GETCODE)
                            .addHeader("Content-Type","application/json")
                            .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                            .addParams("loginToken", SPUtils.getInstance(this).getString("loginToken"))
                            .addParams("account",et_code)
                            .addParams("type","1")
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.d("hcy",e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.d("hcy",response);
                            JSONObject jsonObject = JSON.parseObject(response);
                            String message = (String) jsonObject.get("message");
                            Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
                            int code = (int) jsonObject.get("code");
                            if (code==301){
                                exitDialog();
                            }
                        }
                    });
                }
                TimeCount timeCount = new TimeCount(60000, 1000);
                timeCount.start();
                break;
            case R.id.btn_register:
                phone = etPhone.getText().toString().trim();
                String code = etPwd.getText().toString().trim();
                String pwd1 = password.getText().toString().trim();


                if (TextUtils.isEmpty(pwd1)){
                    Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpUtils.post().url(Constant.REGISTER)
                        .addHeader("Content-Type","application/json")
                        .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                        .addParams("account", phone)
                        .addParams("password",pwd1)
                        .addParams("code",code)
                        .addParams("appType","001")
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Log.d("hcy",response);
                                MessageCodeBean messageCodeBean = JSON.parseObject(response, MessageCodeBean.class);
                                if (messageCodeBean.getCode()==200){
                                    SPUtils.getInstance(RegisterActivity.this).put("isLogin",true);
                                    SPUtils.getInstance(RegisterActivity.this).put("name", phone);

                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
                break;
        }
    }


    /**
     * 倒计时从新获取验证码
     */
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCode.setTextColor(getResources().getColor(R.color.gray_text1));
            getCode.setClickable(false);
            getCode.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            getCode.setText("重获验证码");
            getCode.setTextColor(getResources().getColor(R.color.red_wine));
            getCode.setClickable(true);

        }
    }
}
