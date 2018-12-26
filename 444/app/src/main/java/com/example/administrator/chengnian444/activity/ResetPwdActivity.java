package com.example.administrator.chengnian444.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.chengnian444.R;


 /**
  *    重置密码的界面
     * @Title:
     * @ProjectName 
     * @Description: TODO
     * @author zhangyang
     * @date   
     */
public class ResetPwdActivity extends AppCompatActivity {

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

                 break;

             case R.id.get_code:
                 //获取验证码

                 break;
         }
     }


}
