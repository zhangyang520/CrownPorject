package com.example.administrator.chengnian444.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.constant.ConstantTips;
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


public class UpdateActivity extends BaseActivity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_pwd)
    EditText etPwd;
    @Bind(R.id.et_pwd_new)
    EditText etPwdNew;
    @Bind(R.id.btn_update)
    Button btnUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.black));
    }

    @OnClick({R.id.back, R.id.btn_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;


            case R.id.btn_update:
                if(verifyForgetPwd()){
                    String phone = etPhone.getText().toString().trim();
                    String oldPwd = etPwd.getText().toString().trim();
                    String newPwd = etPwdNew.getText().toString().trim();
                    updateHttp(phone,oldPwd,newPwd);
                }
                break;
        }
    }

    private void updateHttp(String phone, String oldPwd, String newPwd) {
        Log.d("hcy","2222");
        OkHttpUtils.post()
                .url(Constant.BASEURL+Constant.CHANGEPASSWORD)
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("loginToken", SPUtils.getInstance(this).getString("loginToken"))
                .addParams("account",phone)
                .addParams("oldPassword",oldPwd)
                .addParams("newPassword",newPwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    Log.d("hcy",e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("hcy",response.toString());
                        try {
                            JSONObject jsonObject = JSON.parseObject(response);
                            int code = (int) jsonObject.get("code");
                            String message = (String) jsonObject.get("message");
                            if (code == 200){
                                ToastUtils.showToast(UpdateActivity.this,message);
                                startActivity(new Intent(UpdateActivity.this,LoginActivity.class));
                                finish();
                            }else if (code==301){
                                exitDialog();
                            }else {
                                ToastUtils.showToast(UpdateActivity.this,message);
                            }
                        } catch (Exception e) {
                            ToastUtils.showToast(UpdateActivity.this,"未知错误");
                        }
                    }
                });
    }


    /**
     * 验证忘记密码 的信息
     * @return
     */
    private boolean verifyForgetPwd() {
        //先进行判断 是否为空
        if(!etPhone.getText().toString().equals("") &&
                !etPwd.getText().toString().equals("") &&
                !etPwdNew.getText().toString().equals("")){
            //都不为空
            if(!etPhone.getText().toString().matches(ConstantTips.PHONE_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.PHONE_REG_FORMATE_ERROR);
                return false;
            }

            //验证 输入密码格式的正确性
            if(!etPwd.getText().toString().matches(ConstantTips.LOGIN_PWD_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.OLD_PWD_FORMATE_ERROR);
                return false;
            }

            //验证 输入密码格式的正确性
            if(!etPwdNew.getText().toString().matches(ConstantTips.LOGIN_PWD_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.NEW_PWD_FORMATE_ERROR);
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

            //输入的信息不完整
            if(etPwdNew.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,etPwdNew.getHint().toString());
                return false;
            }
        }
        return true;
    }

}
