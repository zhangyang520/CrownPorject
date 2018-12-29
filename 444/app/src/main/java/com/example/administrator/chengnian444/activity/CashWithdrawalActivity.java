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
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
import com.example.administrator.chengnian444.utils.PopupUtils;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.ToastUtils;

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

    @Bind(R.id.tv_cash_type)
    TextView tv_cash_type;

    //对应的名称
    @Bind(R.id.tv_cash_user_name)
    TextView tv_cash_user_name;

    //账号的输入框
    @Bind(R.id.ed_alipay_name)
    EditText ed_alipay_name;

    //提现金额输入框
    @Bind(R.id.ed_cash_withdraw)
    EditText ed_cash_withdraw;

    //安全密码的输入框
    @Bind(R.id.ed_safety_pass)
    EditText ed_safety_pass;

    @Bind(R.id.tv_balance)
    TextView tv_balance;

    @Bind(R.id.tv_first_benfit)
    TextView tv_first_benfit;

    @Bind(R.id.tv_second_benfit)
    TextView tv_second_benfit;

    @Bind(R.id.tv_third_benfit)
    TextView tv_third_benfit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdrawal);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.black));
        ButterKnife.bind(this);

        //初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        try {
            //初始化用户
            UserBean userBean=UserDao.getLocalUser();
            tv_balance.setText("当前余额："+userBean.totalBalance+"");
            tv_first_benfit.setText(userBean.firstPromotionBenfits + "");
            tv_second_benfit.setText(userBean.secondPormotionBenfits + "");
            tv_third_benfit.setText(userBean.thirdPromotionBenfits+"");
        } catch (ContentException e) {
            e.printStackTrace();
            //初始化用户
            ToastUtils.showToast(this,ConstantTips.USER_NO_LOGIN);
        }

    }

    @OnClick({R.id.back,R.id.rl_pay_type,R.id.tv_balance_detail,R.id.btn_submit})
     public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.rl_pay_type:
                int type=0;
                if(tv_cash_type.getText().equals("支付宝")){
                    //为支付宝的提现方式
                    type=0;
                }else{
                    //否则为微信 的提现方式
                    type=1;
                }
                PopupUtils.showPayType(this, new PopupUtils.ClickOnListener() {
                    @Override
                    public void onOk(int currentCashType) {
                        //如果为支付宝
                        if(currentCashType==0){
                            tv_cash_type.setText("支付宝");
                            tv_cash_user_name.setText("支付宝账号");
                            ed_alipay_name.setHint("请输入支付宝账号");
                        }else{
                            tv_cash_type.setText("微信");
                            tv_cash_user_name.setText("微信账号");
                            ed_alipay_name.setHint("请输入微信宝账号");
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                },constraintLayout,type);
                break;

            case R.id.tv_balance_detail:
                startActivity(new Intent(this,DetailofBalanceActivity.class));
                break;

            case R.id.btn_submit:
                //点击提交
                if(verifySubmit()){
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
                }
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

    /**
     * 校验其中的内容
     */
    public boolean  verifySubmit(){

        try {
            UserBean userBean=UserDao.getLocalUser();

            //对应的数据的 是否为空判断
            //先进行判断 是否为空
            if(!ed_alipay_name.getText().toString().equals("") &&
                    !ed_cash_withdraw.getText().toString().equals("") &&
                                !ed_safety_pass.getText().toString().equals("")){
                //不为空的请求 ok
                //以下判断 数据的有效性:
                if (!ed_cash_withdraw.getText().toString().matches("\\d")) {
                    ToastUtils.showToast(this,"提现金额必须是数字");
                    return false;
                }
                //判断 用户的余额是否大于100
                if (userBean.totalBalance < 100) {
                    ToastUtils.showToast(this,"用户余额必须大于100");
                    return false;
                }
                // 提现金额 不能大于 用户的余额
                if(Double.parseDouble(ed_cash_withdraw.getText().toString())<userBean.totalBalance){
                    ToastUtils.showToast(this,"提现金额不能大于用户的余额");
                    return false;
                }
                //提现的金额必须是100的倍数
                if(Double.parseDouble(ed_cash_withdraw.getText().toString())%100==0){
                    ToastUtils.showToast(this,"提现金额必须是100的整数倍");
                    return false;
                }

                //安全密码
                if(!ed_safety_pass.getText().toString().matches("\\d{6}")){
                    ToastUtils.showToast(this,"安全密码必须是6位数字");
                    return false;
                }
                return true;
            }else{
                //输入的信息不完整
                if(ed_alipay_name.getText().toString().trim().equals("")){
                    ToastUtils.showToast(this,ed_alipay_name.getHint().toString());
                    return false;
                }
                //输入的信息不完整
                if(ed_cash_withdraw.getText().toString().trim().equals("")){
                    ToastUtils.showToast(this,ed_cash_withdraw.getHint().toString());
                    return false;
                }

                //输入的信息不完整
                if(ed_safety_pass.getText().toString().trim().equals("")){
                    ToastUtils.showToast(this,ed_safety_pass.getHint().toString());
                    return false;
                }
            }
            return true;

        } catch (ContentException e) {
            e.printStackTrace();
            ToastUtils.showToast(this,ConstantTips.USER_NO_LOGIN);
            return false;
        }
    }
}
