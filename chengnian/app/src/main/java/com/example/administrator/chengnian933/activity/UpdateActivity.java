package com.example.administrator.chengnian933.activity;

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
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.base.BaseActivity;
import com.example.administrator.chengnian933.http.Constant;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.example.administrator.chengnian933.utils.StatusBarCompat.StatusBarCompat;
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
                String phone = etPhone.getText().toString().trim();
                String oldPwd = etPwd.getText().toString().trim();
                String newPwd = etPwdNew.getText().toString().trim();
                updateHttp(phone,oldPwd,newPwd);
                break;
        }
    }

    private void updateHttp(String phone, String oldPwd, String newPwd) {
        Log.d("hcy","2222");
        OkHttpUtils.post()
                .url(Constant.CHANGEPASSWORD)
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
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
                        JSONObject jsonObject = JSON.parseObject(response);
                        int code = (int) jsonObject.get("code");
                        String message = (String) jsonObject.get("message");
                        if (code == 200){
                            Toast.makeText(UpdateActivity.this,message,Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateActivity.this,LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(UpdateActivity.this,message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
