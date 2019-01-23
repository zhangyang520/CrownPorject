package com.jh.lottery.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jh.lottery.AppConfig;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.view.XLoadingDialog;
import com.jh.lottery.view.XToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/8/7.
 */

public class ChangePasswordActivity extends BaseActivity implements TextWatcher {
    private EditText oldPwd;
    private EditText newPwd;
    private EditText entPwd;
    private Button button;
    @Override
    public int getLayoutId() {
        return R.layout.activity_password;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

        showBackButton();
        setTitle("修改密码");

        oldPwd = find(R.id.oldPwd);
        newPwd = find(R.id.newPwd);
        entPwd = find(R.id.enterPwd);
        button = find(R.id.submitBtn);
        button.setClickable(false);
        oldPwd.addTextChangedListener(this);
        newPwd.addTextChangedListener(this);
        entPwd.addTextChangedListener(this);
    }
    public void submitAction(View v){

        String old = oldPwd.getText().toString();
        String newP = newPwd.getText().toString();
        String ent = entPwd.getText().toString();
        if (newP.length() < 6){
            XToast.warning("新密码不能少于6位");
            return;
        }
        if(!newP.equals(ent)){
            XToast.warning("两次密码不一样!");
            return;
        }


        Map<String,String> map = new HashMap<>();
        map.put("newPassword",newP);
        map.put("oldPassword",old);
        map.put("accessToken",UserModel.getUserInfo().getToken());
        XLoadingDialog.with(this).setMessage("修改中...").show();
        HttpTool.POST(AppConfig.updatePasswordUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                XLoadingDialog.with(ChangePasswordActivity.this).dismiss();
                XToast.success("修改成功");
                finish();

            }

            @Override
            public void onError(String msg) {
                XLoadingDialog.with(ChangePasswordActivity.this).dismiss();
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String old = oldPwd.getText().toString();
        String newP = newPwd.getText().toString();
        String ent = entPwd.getText().toString();
        if (!old.isEmpty() && !newP.isEmpty() && !ent.isEmpty()){
            button.setClickable(true);
            button.setBackground(getResources().getDrawable(R.drawable.login_button_bg));
        }else {
            button.setClickable(false);
            button.setBackground(getResources().getDrawable(R.drawable.disable_button_bg));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
