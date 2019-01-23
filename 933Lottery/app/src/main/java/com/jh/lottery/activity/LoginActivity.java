package com.jh.lottery.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jh.lottery.AppConfig;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.XLoadingDialog;
import com.jh.lottery.view.XToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import allen.com.rntestproject.R;

public class LoginActivity extends BaseActivity{


    private EditText accountEdit;
    private EditText passwordEdit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {
        showBackButton();
        setTitle("用户登录");

        accountEdit = find(R.id.mobile);
        passwordEdit = find(R.id.password);
    }

    public void loginAction(View v){

        String account = accountEdit.getText().toString();
        String password = passwordEdit.getText().toString();

        if (account.isEmpty()){
            XToast.error("账号不能为空！");
            return;
        }
        if (password.isEmpty()){
            XToast.error("密码不能为空");
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("mobilePhone",account);
        map.put("password",password);
        XLoadingDialog.with(this).setMessage("登录中...").show();
        HttpTool.POST(AppConfig.loginUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                XLoadingDialog.with(LoginActivity.this).dismiss();


                try {
                    JSONObject userJSON = json.getJSONObject("data");
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    UserModel user = gson.fromJson(userJSON.toString(),UserModel.class);
                    UserModel.saveUserInfo(user);
                    finish();
                } catch (JSONException e) {
                }


            }

            @Override
            public void onError(String msg) {
                XLoadingDialog.with(LoginActivity.this).dismiss();
            }
        });

    }

    public void registerAction(View v){
        JumpToActivity(RegisterActivity.class);
    }
}

