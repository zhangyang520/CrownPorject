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
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.utils.ToastUtils;


/**
  *    安全密码界面
     * @Title:
     * @ProjectName
     * @Description: TODO
     * @author zhangyang
     * @date
     */
public class SafetyPwdActivity extends AppCompatActivity {


     @Bind(R.id.tv_choengzhi)
     TextView tv_choengzhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_pwd);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.tv_choengzhi,R.id.back,R.id.tv_edit_pwd})
     public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_choengzhi:
                //重置密码
                startActivity(new Intent(this,ResetPwdActivity.class));
                break;

            case R.id.back:
                finish();
                break;

            case R.id.tv_edit_pwd:
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
        }
     }
}
