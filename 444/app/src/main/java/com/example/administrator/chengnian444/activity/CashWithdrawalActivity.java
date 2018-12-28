package com.example.administrator.chengnian444.activity;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
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
import com.example.administrator.chengnian444.utils.PopupUtils;

/**
  *    提现申请的界面
     * @Title:
     * @ProjectName 
     * @Description: TODO
     * @author zhangyang
     * @date   
     */
public class CashWithdrawalActivity extends AppCompatActivity {

    @Bind(R.id.cl_root)
    ConstraintLayout constraintLayout;

    @Bind(R.id.tv_balance_detail)
    TextView tv_balance_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdrawal);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.back,R.id.rl_pay_type,R.id.tv_balance_detail,R.id.btn_submit})
     public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.rl_pay_type:
                PopupUtils.showPayType(this, new PopupUtils.ClickOnListener() {
                    @Override
                    public void onOk() {

                    }

                    @Override
                    public void onCancel() {

                    }
                },constraintLayout);
                break;

            case R.id.tv_balance_detail:
                startActivity(new Intent(this,DetailofBalanceActivity.class));
                break;

            case R.id.btn_submit:
                //点击提交
                //修改密码 弹出对应的对话框
                View contentView=View.inflate(this,R.layout.dialog_application_submission,null);
                final AlertDialog alertDialog=new AlertDialog.Builder(this).setView(contentView).create();
                Button btn_cancel = contentView.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                final TextView tv_content=contentView.findViewById(R.id.tv_content);
                //todo  tv_content 设置账号
                Button btn_ok=contentView.findViewById(R.id.btn_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                        showTipsDialog();
                        //
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
                break;
        }
     }

    /**
     * 弹出 提示框
     */
    public void showTipsDialog(){
         //修改密码 弹出对应的对话框
         View contentView=View.inflate(this,R.layout.dialog_application_submission_tips,null);
         final AlertDialog alertDialog=new AlertDialog.Builder(this).setView(contentView).create();
//         alertDialog.getWindow().getAttributes().width=(int) (getResources().getDisplayMetrics().widthPixels*0.5);
         Button btn_ok=contentView.findViewById(R.id.btn_ok);
         btn_ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 alertDialog.dismiss();
                 //
             }
         });
         alertDialog.setCancelable(false);
         alertDialog.show();
     }
}
