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

import butterknife.BindInt;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.chengnian444.MainActivity;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.base.MyApplication;
import com.example.administrator.chengnian444.bean.MessageCodeBean;
import com.example.administrator.chengnian444.bean.RegisterBean;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.listener.NoDoubleClickListener;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.example.administrator.chengnian444.utils.Validator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


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
    @Bind(R.id.ed_extendition_code)
    EditText ed_extendition_code;

    @Bind(R.id.btn_register)
    Button btnRegister;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.black));

        getCode.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                onViewClicked(v);
            }
        });

        btnRegister.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                onViewClicked(v);
            }
        });
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.get_code:
                //获取短信验证码
                if(verifyCode()){
                    String et_code = etPhone.getText().toString().trim();
                    OkHttpUtils.post().
                            url(Constant.BASEURL+Constant.GETCODE)
                            .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                            .addParams("loginToken", SPUtils.getInstance(this).getString("loginToken"))
                            .addParams("account",et_code)
                            .addParams("appType",Constant.platform_id)
                            .addParams("type","1")
                            .build().execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if(e instanceof ConnectException){
                                ToastUtils.showToast(MyApplication.context, "请检查网络!");
                            }else if(e instanceof SocketTimeoutException){
                                ToastUtils.showToast(MyApplication.context, "请求超时!");
                            }else{
                                ToastUtils.showToast(RegisterActivity.this, "获取验证码失败!");
                            }
                            Log.d("hcy",e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Log.d("hcy",response);
                            JSONObject jsonObject = JSON.parseObject(response);
                            if (!jsonObject.isEmpty()){
                                String message = (String) jsonObject.get("message");
                                int code = (int) jsonObject.get("code");
                                boolean flag=jsonObject.getBoolean("data");
                                if(flag){
                                    //时间的定时器
                                    TimeCount timeCount = new TimeCount(60000, 1000);
                                    timeCount.start();
                                    ToastUtils.showToast(RegisterActivity.this,message);
                                }else{
                                    ToastUtils.showToast(RegisterActivity.this,message);
                                    if (code==301){
                                        exitDialog();
                                    }else if(code==101){

                                    }
                                }
                            }else{
                                ToastUtils.showToast(RegisterActivity.this,"发送验证码数据为空!");
                            }
                        }
                    });
                }
                break;

            case R.id.btn_register:
                //点击 注册 按钮
                if(verifyRegister()){
                        phone = etPhone.getText().toString().trim();
                        String code = etPwd.getText().toString().trim();
                        String pwd1 = password.getText().toString().trim();
                        Integer extentsionCode=0;
                        if(ed_extendition_code.getText().toString().trim().equals("")){
                            OkHttpUtils.post().url(Constant.BASEURL+Constant.REGISTER)
                                    .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                                    .addParams("account", phone)
                                    .addParams("password",pwd1)
                                    .addParams("code",code)
                                    .addParams("preRememberId",extentsionCode+"")
                                    .addParams("channel",Constant.channel_id)
                                    .addParams("appType",Constant.platform_id)
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            if(e instanceof ConnectException){
                                                ToastUtils.showToast(MyApplication.context, "请检查网络!");
                                            }else if(e instanceof SocketTimeoutException){
                                                ToastUtils.showToast(MyApplication.context, "请求超时!");
                                            }else{
                                                ToastUtils.showToast(RegisterActivity.this, "注册失败!");
                                            }
                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            Log.d("hcy",response);
                                            RegisterBean messageCodeBean = JSON.parseObject(response, RegisterBean.class);
                                            if (messageCodeBean.getCode()==200){
                                                ToastUtils.showToast(RegisterActivity.this, messageCodeBean.getMessage());
                                                finish();
                                            }else {
                                                ToastUtils.showToast(RegisterActivity.this,messageCodeBean.getMessage());
                                            }
                                        }
                                    });
                        }else{
                            extentsionCode=Integer.parseInt(ed_extendition_code.getText().toString().trim());
                            OkHttpUtils.post().url(Constant.BASEURL+Constant.REGISTER)
                                    .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                                    .addParams("account", phone)
                                    .addParams("password",pwd1)
                                    .addParams("code",code)
                                    .addParams("preRememberId",extentsionCode+"")
                                    .addParams("channel",Constant.channel_id)
                                    .addParams("appType",Constant.platform_id)
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {

                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            Log.d("hcy",response);
                                            RegisterBean messageCodeBean = JSON.parseObject(response, RegisterBean.class);
                                            if (messageCodeBean.getCode()==200){
                                                 ToastUtils.showToast(RegisterActivity.this, messageCodeBean.getMessage());
                                                finish();
                                            }else {
                                                ToastUtils.showToast(RegisterActivity.this,messageCodeBean.getMessage());
                                            }
                                        }
                                    });
                        }


                      }
                break;
        }
    }


    /**
     * 验证登录的信息
     * @return
     */
    private boolean verifyRegister() {
        //先进行判断 是否为空
        if(!etPhone.getText().toString().equals("") &&
                !password.getText().toString().equals("") &&
                        !etPwd.getText().toString().equals("")){
            //都不为空
            if(!etPhone.getText().toString().matches(ConstantTips.PHONE_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.PHONE_REG_FORMATE_ERROR);
                return false;
            }

            //验证 输入密码格式的正确性
            if(!password.getText().toString().matches(ConstantTips.LOGIN_PWD_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.PWD_FORMATE_ERROR);
                return false;
            }

            //验证 输入密码格式的正确性
            if(!etPwd.getText().toString().matches(ConstantTips.VERIFY_CODE_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.VERIFY_CDOE_ERROR);
                return false;
            }

            if(!ed_extendition_code.getText().toString().equals("")){
                //如果注册时候的推广码 不为空 需要判断 格式
                if (!ed_extendition_code.getText().toString()
                        .matches(ConstantTips.EXTENDITION_CODE_REGEX)) {
                    ToastUtils.showToast(this,"推广码必须是6位数字");
                    return false;
                }
            }
            return true;
        }else{
            //输入的信息不完整
            if(etPhone.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,etPhone.getHint().toString());
                return false;
            }
            //输入的信息不完整
            if(password.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,password.getHint().toString());
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


    /**
     * 验证 输入的手机号
     */
    public boolean  verifyCode(){
        //先进行判断 是否为空
        if(!etPhone.getText().toString().equals("")){
            //都不为空
            if(!etPhone.getText().toString().matches(ConstantTips.PHONE_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.PHONE_REG_FORMATE_ERROR);
                return false;
            }
            return true;
        }else{
            //输入的信息不完整
            if(etPhone.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,etPhone.getHint().toString());
                return false;
            }
        }
        return true;
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
