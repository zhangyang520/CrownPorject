package com.example.administrator.chengnian444.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.bean.SettingSafetyPwdResponse;
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;


/**
 *  安全密码界面
 * @Title:
 * @ProjectName
 * @Description: TODO
 * @author zhangyang
 * @date
 */
public class SafetyPwdActivity extends AppCompatActivity {


     @Bind(R.id.tv_choengzhi)
     TextView tv_choengzhi;

    @Bind(R.id.btn_ok_safe_pwd)
    Button btn_ok_safe_pwd;

    @Bind({R.id.et_safety_pwd})
    EditText et_safety_pwd;

    //安全密码是否已经绑定
    boolean isSafetyPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_pwd);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.black));
        ButterKnife.bind(this);

        //这边传递一个安全密码 是否绑定类型
        isSafetyPwd=getIntent().getBooleanExtra("isSafetyPwd",false);

        //初始化数据
        initData();
    }


    /**
     * 初始化数据
     */
    private void initData(){
        if(!isSafetyPwd){
            //如果安全密码已经绑定
            btn_ok_safe_pwd.setVisibility(View.VISIBLE);
            et_safety_pwd.setHint("请输入6位安全密码");
            et_safety_pwd.setHintTextColor(getResources().getColor(R.color.mine_cash_name));
        }else{
            //如果已经展现
            btn_ok_safe_pwd.setVisibility(View.GONE);
            et_safety_pwd.setHint("已设置安全密码");
            et_safety_pwd.setHintTextColor(getResources().getColor(R.color.black));
        }
    }


    @OnClick({R.id.tv_choengzhi,R.id.back,R.id.tv_edit_pwd,R.id.btn_ok_safe_pwd})
     public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_choengzhi:
                //重置密码
                if(isSafetyPwd){
                    startActivity(new Intent(this,ResetPwdActivity.class));
                }else{
                    ToastUtils.showToast(this,"请先设置安全密码");
                }
                break;

            case R.id.back:
                finish();
                break;


            case R.id.tv_edit_pwd:  //暂时 消失
                //修改密码 弹出对应的对话框
                View contentView=View.inflate(this,R.layout.dialog_original_security_pwd,null);
                final AlertDialog alertDialog=new AlertDialog.Builder(this).setView(contentView).create();

                AppCompatImageView iv_mine_cancel=contentView.findViewById(R.id.iv_mine_cancel);
                iv_mine_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                Button btn_cancel = contentView.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                final EditText ed_original_safe_pwd=contentView.findViewById(R.id.ed_original_safe_pwd);

                Button btn_ok=contentView.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!ed_original_safe_pwd.getText().toString().trim().equals("")){
                            //如果不为空
                            ToastUtils.showToast(SafetyPwdActivity.this,"输入安全吗");
                        }else{
                            ToastUtils.showToast(SafetyPwdActivity.this,"请输入安全吗");
                        }
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
                //弹出
                break;

            case R.id.btn_ok_safe_pwd:
                //确认 修改的安全密码
                if(verifyOK()){
                     //进行业务的处理
                    //todo 增加请求过程
                    try {
                        OkHttpUtils.post().url(Constant.BASEURL+Constant.setSecurityPassword)
                                  .addHeader("Content-Type","application/json")
                                  .addHeader("Authorization",SPUtils.getInstance(this).getString("token"))
                                  .addParams("loginToken",SPUtils.getInstance(this).getString("loginToken"))
                                  .addParams("securityPassword",et_safety_pwd.getText().toString())
                                  .addParams("account",UserDao.getLocalUser().userName).build().execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ToastUtils.showToast(SafetyPwdActivity.this,"安全密码设置失败");
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
                                        userBean.safePwd=et_safety_pwd.getText().toString();
                                        UserDao.saveUpDate(userBean);
                                        isSafetyPwd=true;
                                        initData();
                                    } catch (ContentException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    //如果安全密码 设置 失败
                                    ToastUtils.showToast(SafetyPwdActivity.this,settingSafetyPwdResponse.getMessage());
                                }
                            }
                        });
                    } catch (ContentException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
     }

    /**
     * 是否 能够点击 确认
     * @return
     */
    private boolean verifyOK() {
        if(!et_safety_pwd.getText().toString().equals("")){
            if(!et_safety_pwd.getText().toString().matches(ConstantTips.SAFE_PWD_VERIFY)){
                  ToastUtils.showToast(this,"安全密码必须是4位数字");
                  return false;
            }
            return true;
        }else{
           ToastUtils.showToast(this,et_safety_pwd.getHint().toString());
        }
        return true;
    }
}
