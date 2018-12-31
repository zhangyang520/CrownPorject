package com.example.administrator.chengnian444.activity;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.bean.SettingSafetyPwdResponse;
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;


/**
  *    重置密码的界面
     * @Title:
     * @ProjectName 
     * @Description: TODO
     * @author zhangyang
     * @date   
     */
public class ResetPwdActivity extends BaseActivity {

     @Bind(R.id.et_phone)
     EditText et_phone;

     @Bind(R.id.et_new_safety_pwd)
     EditText et_new_safety_pwd;

     @Bind(R.id.et_verify_code)
     EditText et_verify_code;

     @Bind(R.id.get_code)
     Button get_code;

     @Bind(R.id.btn_ok_reset)
     Button btn_ok_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        ButterKnife.bind(this);

    }


     @OnClick({R.id.back})
     public void onClickView(View view) {
         switch (view.getId()) {
             case R.id.back:
                 finish();
                 break;

             case R.id.btn_ok_reset:
                //重置密码 点击事件
                if (verifyOk()){
                    //进行请求数据
                    //todo 增加请求过程
                        OkHttpUtils.post().url(Constant.resetSafetyPwd)
                                .addHeader("Content-Type","application/json")
                                .addHeader("Authorization",SPUtils.getInstance(this).getString("token"))
                                .addParams("loginToken",SPUtils.getInstance(this).getString("loginToken"))
                                .addParams("newSecurityPassword",et_new_safety_pwd.getText().toString())
                                .addParams("code",et_verify_code.getText().toString())
                                .addParams("account",et_phone.getText().toString()).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ToastUtils.showToast(ResetPwdActivity.this,"安全密码重置失败");
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                //成功的请求
                                SettingSafetyPwdResponse settingSafetyPwdResponse=JSON.parseObject(response,SettingSafetyPwdResponse.class);
                                if(settingSafetyPwdResponse.getCode() == 200 && settingSafetyPwdResponse.isData()){
                                    //如果设置成功
                                    try {
                                        UserBean userBean=UserDao.getLocalUser();
                                        userBean.isSafeLocked=true;
                                        userBean.safePwd=et_new_safety_pwd.getText().toString();
                                        UserDao.saveUpDate(userBean);
                                    } catch (ContentException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    //如果安全密码 设置 失败
                                    ToastUtils.showToast(ResetPwdActivity.this,settingSafetyPwdResponse.getMessage());
                                }
                            }
                        });
                }
                 break;

             case R.id.get_code:
                 //获取验证码
                 //获取短信验证码
                 if(verifyCode()){
                     String et_code = et_phone.getText().toString().trim();
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
                             ToastUtils.showToast(ResetPwdActivity.this,message);
                             //todo
                             int code = (int) jsonObject.get("code");
                             if (code==301){
                                 exitDialog();
                             }
                         }
                     });

                     //时间的定时器
                     TimeCount timeCount = new TimeCount(60000, 1000);
                     timeCount.start();
                 }
                 break;
         }
     }


    /**
     * 验证 输入的手机号
     */
    public boolean  verifyCode(){
        //先进行判断 是否为空
        if(!et_phone.getText().toString().equals("")){
            //都不为空
            if(!et_phone.getText().toString().matches(ConstantTips.PHONE_REGEX)){
                //如果手机号 不满足格式
                ToastUtils.showToast(this,ConstantTips.PHONE_REG_FORMATE_ERROR);
                return false;
            }
            return true;
        }else{
            //输入的信息不完整
            if(et_phone.getText().toString().trim().equals("")){
                ToastUtils.showToast(this,et_phone.getHint().toString());
                return false;
            }
        }
        return true;
    }

     /**
      * 设置 按钮
      * @return
      */
     private boolean verifyOk() {
         //先进行判断 是否为空
         if(!et_phone.getText().toString().equals("") &&
                 !et_new_safety_pwd.getText().toString().equals("") &&
                 !et_verify_code.getText().toString().equals("")){
             //都不为空
             if(!et_phone.getText().toString().matches(ConstantTips.PHONE_REGEX)){
                 //如果手机号 不满足格式
                 ToastUtils.showToast(this,ConstantTips.PHONE_REG_FORMATE_ERROR);
                 return false;
             }

             //验证 输入密码格式的正确性
             if(!et_new_safety_pwd.getText().toString().matches(ConstantTips.SAFE_PWD_VERIFY)){
                 //如果手机号 不满足格式
                 ToastUtils.showToast(this,ConstantTips.PWD_FORMATE_ERROR);
                 return false;
             }

             //验证 输入密码格式的正确性
             if(!et_verify_code.getText().toString().matches(ConstantTips.VERIFY_CODE_REGEX)){
                 //如果手机号 不满足格式
                 ToastUtils.showToast(this,ConstantTips.VERIFY_CDOE_ERROR);
                 return false;
             }
             return true;
         }else{
             //输入的信息不完整
             if(et_phone.getText().toString().trim().equals("")){
                 ToastUtils.showToast(this,et_phone.getHint().toString());
                 return false;
             }
             //输入的信息不完整
             if(et_new_safety_pwd.getText().toString().trim().equals("")){
                 ToastUtils.showToast(this,et_new_safety_pwd.getHint().toString());
                 return false;
             }

             //输入的信息不完整
             if(et_verify_code.getText().toString().trim().equals("")){
                 ToastUtils.showToast(this,et_verify_code.getHint().toString());
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
            get_code.setTextColor(getResources().getColor(R.color.gray_text1));
            get_code.setClickable(false);
            get_code.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            get_code.setText("重获验证码");
            get_code.setTextColor(getResources().getColor(R.color.red_wine));
            get_code.setClickable(true);

        }
    }

 }
