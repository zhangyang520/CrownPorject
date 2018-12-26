package com.example.administrator.chengnian933.activity;

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
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.base.BaseActivity;
import com.example.administrator.chengnian933.bean.MessageCodeBean;
import com.example.administrator.chengnian933.http.Constant;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.example.administrator.chengnian933.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian933.utils.Validator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ForgetPwdActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.icon_lock)
    ImageView iconLock;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.get_code)
    Button getCode;
    @Bind(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.white));
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
                            .addParams("account",et_code)
                            .addParams("type","2")
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
                            Toast.makeText(ForgetPwdActivity.this,message,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                TimeCount timeCount = new TimeCount(60000, 1000);
                timeCount.start();
                break;
            case R.id.btn_register:
                String phone = etPhone.getText().toString().trim();
                final String code = etPwd.getText().toString().trim();
                String pwd1 = password.getText().toString().trim();


                if (TextUtils.isEmpty(pwd1)){
                    Toast.makeText(this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpUtils.post().url(Constant.UPDATAPASSWORD)
                        .addHeader("Content-Type","application/json")
                        .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                        .addParams("account",phone)
                        .addParams("newPassword",pwd1)
                        .addParams("code",code)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {

                            }

                            @Override
                            public void onResponse(String response, int id) {
                                JSONObject jsonObject = JSON.parseObject(response);
                                int code1 = (int) jsonObject.get("code");
                                String message = (String) jsonObject.get("message");
                                if (code1== 200){
                                 Toast.makeText(ForgetPwdActivity.this,message,Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(ForgetPwdActivity.this,message,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }

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
