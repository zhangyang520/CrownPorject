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

public class ChangeNicknameActivity extends BaseActivity implements TextWatcher {

    private EditText editText;
    private UserModel user;
    private Button button;
    @Override
    public int getLayoutId() {
        return R.layout.activity_change_nickname;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        user = UserModel.getUserInfo();
    }

    @Override
    public void initView() {
        showBackButton();
        setTitle("修改昵称");
        button = find(R.id.submitBtn);
        button.setClickable(false);
        editText = find(R.id.editText);
        editText.setText(user.getNickName());
        editText.addTextChangedListener(this);
    }

    public void submitAction(View v){

        final String name = editText.getText().toString();

        Map<String,String> map = new HashMap<>();
        map.put("accessToken",user.getToken());
        map.put("acctBaseId",user.getAcctBaseId());
        map.put("nickName",name);
        XLoadingDialog.with(this).setMessage("修改中...").show();
        HttpTool.GET(AppConfig.updateNickNameUrl, map, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                XLoadingDialog.with(ChangeNicknameActivity.this).dismiss();
                XToast.success("修改成功");
                user.setNickName(name);
                UserModel.saveUserInfo(user);
                finish();

            }

            @Override
            public void onError(String msg) {
                XLoadingDialog.with(ChangeNicknameActivity.this).dismiss();
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().isEmpty() && !s.toString().equals(user.getNickName())){
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
