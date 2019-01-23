package com.jh.lottery.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jh.lottery.AppConfig;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.utils.XRegexUtils;
import com.jh.lottery.view.XLoadingDialog;
import com.jh.lottery.view.XToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import allen.com.rntestproject.R;


public class RegisterActivity extends BaseActivity {
    private EditText mobileEdit;
    private EditText codeEdit;
    private EditText passwordEdit;
    private EditText endPwdEdit;
    private Button getCodeBtn;
    private TimeCount time = null;
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        showBackButton();
        setTitle("账号注册");
        mobileEdit = find(R.id.mobile);
        codeEdit = find(R.id.code);
        passwordEdit = find(R.id.password);
        endPwdEdit = find(R.id.endPassword);
        getCodeBtn = find(R.id.getCodeBtn);
    }


    public void registerAction(View v){

        String mobile = mobileEdit.getText().toString();
        String code = codeEdit.getText().toString();
        String password =  passwordEdit.getText().toString();
        String entPwd = endPwdEdit.getText().toString();


        if (mobile.isEmpty()){
            XToast.warning("手机号不能为空！");
            return;
        }
        if (!XRegexUtils.checkMobile(mobile)){
            XToast.warning("手机号格式不对！");
            return;
        }

        if (code.isEmpty()){
            XToast.warning("验证码不能为空！");
            return;
        }
        if (password.isEmpty()){
            XToast.warning("密码不能为空！");
            return;
        }
        if (password.length() < 6){
            XToast.warning("密码不能少于6位");
            return;
        }
        if (!password.equals(entPwd)){
            XToast.warning("两次密码不一样！");
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("mobilePhone",mobile);
        map.put("password",password);
        map.put("veryCode",code);
        map.put("userComeFrom","android");
        map.put("flag","F");
        XLoadingDialog.with(this).setMessage("注册中...").show();
        HttpTool.POST(AppConfig.registerUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                XLoadingDialog.with(RegisterActivity.this).dismiss();
                XToast.success("注册成功");
                finish();
            }

            @Override
            public void onError(String msg) {
                XLoadingDialog.with(RegisterActivity.this).dismiss();
            }
        });

    }

    public void getCodeAction(View v){
        String mobile = mobileEdit.getText().toString();
        if (mobile.isEmpty()){
            XToast.warning("手机号不能为空！");
            return;
        }
        if (!XRegexUtils.checkMobile(mobile)){
            XToast.warning("手机号格式不对");
            return;
        }
       time = new TimeCount(60000,1000);
       time.start();
       getCodeBtn.setTextColor(getResources().getColor(R.color.XFrame_text_gray));
       getCodeBtn.setClickable(false);
        Map<String,String> map = new HashMap<>();
        map.put("mobilePhone",mobile);
        map.put("sign","0");
        HttpTool.GET(AppConfig.getValidateCodeUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                XToast.success("短信发送成功!");
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            getCodeBtn.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            getCodeBtn.setText("获取难码");
            getCodeBtn.setClickable(true);
            getCodeBtn.setTextColor(getResources().getColor(R.color.color444));
            time = null;
        }
    }
    public void loginAction(View v){
        if (time != null){
            time.onFinish();
            time = null;
        }
        finish();
    }



    @Override
    protected void onDestroy() {
        if (time != null){
            time.onFinish();
            time = null;
        }
        super.onDestroy();
    }
}
