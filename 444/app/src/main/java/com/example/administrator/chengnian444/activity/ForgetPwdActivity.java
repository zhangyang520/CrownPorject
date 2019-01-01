package com.example.administrator.chengnian444.activity;

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
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.http.Constant;
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

   /**
     * 忘记密码界面
     * @Title:
     * @ProjectName
     * @Description: TODO
     * @author zhangyang
     * @date
     */
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
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.main_bg_color));
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.get_code, R.id.btn_register})
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
                                .addHeader("Content-Type","application/json")
                                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                                .addParams("loginToken", SPUtils.getInstance(this).getString("loginToken"))
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
                                ToastUtils.showToast(ForgetPwdActivity.this,message);
                                int code = (int) jsonObject.get("code");
                                if (code==301){
                                    exitDialog();
                                }
                            }
                        });
                    TimeCount timeCount = new TimeCount(60000, 1000);
                    timeCount.start();
                }
                break;


            case R.id.btn_register:
                if(verifyForgetPwd()){
                    String phone = etPhone.getText().toString().trim();
                    final String code = etPwd.getText().toString().trim();
                    String pwd1 = password.getText().toString().trim();
                    OkHttpUtils.post().url(Constant.BASEURL+Constant.UPDATAPASSWORD)
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
                                        ToastUtils.showToast(ForgetPwdActivity.this,message);
                                    }else {
                                        ToastUtils.showToast(ForgetPwdActivity.this,message);
                                    }
                                }
                            });
                }
                break;
        }
    }

       /**
        * 验证忘记密码 的信息
        * @return
        */
       private boolean verifyForgetPwd() {
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
