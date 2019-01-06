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
import com.alibaba.fastjson.JSON;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.base.MyApplication;
import com.example.administrator.chengnian444.bean.IsLockSafetyPwdResponse;
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.bean.UserInfoResponse;
import com.example.administrator.chengnian444.constant.ConstantTips;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.listener.NoDoubleClickListener;
import com.example.administrator.chengnian444.utils.PopupUtils;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;

/**
  *    提现申请的界面
     * @Title:
     * @ProjectName 
     * @Description: TODO
     * @author zhangyang
     * @date   
     */
public class CashWithdrawalActivity extends BaseActivity {

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

    @Bind(R.id.tv_rate)
    TextView tv_rate;

    @Bind(R.id.btn_submit)
    Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdrawal);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.black));
        ButterKnife.bind(this);

        //初始化数据
        initData();

        btn_submit.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                ondoClick(v);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        try {
            //初始化用户
            UserBean userBean=UserDao.getLocalUser();
            tv_balance.setText("当前余额："+userBean.totalBalance+"");
            if(userBean.firstPromotionBenfits.equals("-1.00")){
                tv_first_benfit.setText("暂不开放");
            }else{
               tv_first_benfit.setText(userBean.firstPromotionBenfits + "");
            }
            if(userBean.secondPormotionBenfits.equals("-1.00")){
                tv_second_benfit.setText("暂不开放");
            }else{
                tv_second_benfit.setText(userBean.secondPormotionBenfits + "");
            }
            if(userBean.thirdPromotionBenfits.equals("-1.00")){
                tv_third_benfit.setText("暂不开放");
            }else{
                tv_third_benfit.setText(userBean.thirdPromotionBenfits + "");
            }
        } catch (ContentException e) {
            e.printStackTrace();
            //初始化用户
            ToastUtils.showToast(this,ConstantTips.USER_NO_LOGIN);
        }
    }

    //支付类型 0:微信提现  1:支付宝提现
    int type=5;

    @OnClick({R.id.back,R.id.rl_pay_type,R.id.tv_balance_detail})
     public void ondoClick(View view) {

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.rl_pay_type:
                 type=5;
                if(tv_cash_type.getText().equals("支付宝")){
                    //为支付宝的提现方式
                    type=5;
                }else{
                    //否则为微信 的提现方式
                    type=6;
                }
                PopupUtils.showPayType(this, new PopupUtils.ClickOnListener() {
                    @Override
                    public void onOk(int currentCashType) {
                        //如果为支付宝
                        if(currentCashType==5){
                            tv_cash_type.setText("支付宝");
                            tv_cash_user_name.setText("支付宝账号");
                            ed_alipay_name.setHint("请输入支付宝账号");
                        }else{
                            tv_cash_type.setText("微信");
                            tv_cash_user_name.setText("微信账号");
                            ed_alipay_name.setHint("请输入微信账号");
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
                        //验证接口 validateCashWithdraw
                    validateCashWithdraw();
                }
                break;
        }
     }


    /**
     * 是否确定提现到对应的账户上
     */
    private  void showOkSubmit(){
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
            if(type==5){
                //支付宝
                tv_content.setText("支付宝账号:"+ed_alipay_name.getText().toString());
            }else{
                //微信
                tv_content.setText("微信账号:"+ed_alipay_name.getText().toString());
            }
            Button btn_ok=contentView.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    //提交接口
                    cashWithdraw(alertDialog);
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.show();
    }

    /**
     * 提交 申请接口
     * @param alertDialog
     */
    private void cashWithdraw(final AlertDialog alertDialog){
        try {
            OkHttpUtils.post().url(Constant.BASEURL+Constant.cashWithdraw)
                    .addHeader("Authorization",SPUtils.getInstance(this).getString("token"))
                    .addParams("loginToken",SPUtils.getInstance(this).getString("loginToken"))
                    .addParams("drawlWay",type+"")
                    .addParams("drawlAccount",ed_alipay_name.getText().toString())
                    .addParams("amount",ed_cash_withdraw.getText().toString())
                    .addParams("securityPassword",ed_safety_pass.getText().toString())
                    .addParams("rate",0.05+"")
                    .addParams("appType",Constant.platform_id)
                    .addParams("account",UserDao.getLocalUser().userName).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    if(e instanceof ConnectException){
                        ToastUtils.showToast(MyApplication.context, "请检查网络!");
                    }else if(e instanceof SocketTimeoutException){
                        ToastUtils.showToast(MyApplication.context, "请求超时!");
                    }else{
                        ToastUtils.showToast(CashWithdrawalActivity.this, "提现请求失败!");
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        IsLockSafetyPwdResponse isLockSafetyPwdResponse=
                                JSON.parseObject(response,IsLockSafetyPwdResponse.class);
                        if(isLockSafetyPwdResponse.getCode()==200){
                            if(isLockSafetyPwdResponse.isData()){
                                //安全密码 验证成功 提交成功
                                alertDialog.dismiss();
                                getPromoteInfo();//进行调用接口
                                showTipsDialog();
                            }else{
                                //安全密码 验证失败
                                ToastUtils.showToast(CashWithdrawalActivity.this,isLockSafetyPwdResponse.getMessage());
                            }
                        }else{
                            ToastUtils.showToast(CashWithdrawalActivity.this,isLockSafetyPwdResponse.getMessage());
                        }
                    } catch (Exception e) {
                        ToastUtils.showToast(CashWithdrawalActivity.this,"提交申请提现失败!");
                    }
                }
            });
        } catch (ContentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 提现的 验证接口
     */
    private void validateCashWithdraw() {
        try {
            OkHttpUtils.post().url(Constant.BASEURL+Constant.validateCashWithdraw)
                    .addHeader("Authorization",SPUtils.getInstance(this).getString("token"))
                    .addParams("loginToken",SPUtils.getInstance(this).getString("loginToken"))
                    .addParams("drawlWay",type+"")
                    .addParams("appType",Constant.platform_id)
                    .addParams("drawlAccount",ed_alipay_name.getText().toString())
                    .addParams("amount",ed_cash_withdraw.getText().toString())
                    .addParams("securityPassword",ed_safety_pass.getText().toString())
                    .addParams("rate",0.05+"")
                    .addParams("account",UserDao.getLocalUser().userName).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    if(e instanceof ConnectException){
                        ToastUtils.showToast(MyApplication.context, "请检查网络!");
                    }else if(e instanceof SocketTimeoutException){
                        ToastUtils.showToast(MyApplication.context, "请求超时!");
                    }else{
                        ToastUtils.showToast(CashWithdrawalActivity.this,"申请提现提交失败!");
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        IsLockSafetyPwdResponse isLockSafetyPwdResponse=
                                JSON.parseObject(response,IsLockSafetyPwdResponse.class);
                        if(isLockSafetyPwdResponse.getCode()==200){
                            if(isLockSafetyPwdResponse.isData()){
                                //安全密码 验证成功
                                showOkSubmit();
                            }else{
                               //安全密码 验证失败
                                ToastUtils.showToast(CashWithdrawalActivity.this,isLockSafetyPwdResponse.getMessage());
                            }
                        }else{
                            ToastUtils.showToast(CashWithdrawalActivity.this,isLockSafetyPwdResponse.getMessage());
                        }
                    } catch (Exception e) {
                        ToastUtils.showToast(CashWithdrawalActivity.this,"提交申请提现失败!");
                    }
                }
            });
        } catch (ContentException e) {
            e.printStackTrace();
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

     //提现的临界金额
     private final int withdrawCash=50;
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
                if (!ed_cash_withdraw.getText().toString().matches("\\d*.*\\d*")) {
                    ToastUtils.showToast(this,"提现金额必须是数字");
                    return false;
                }
                //判断 用户的余额是否大于100
                if (Float.parseFloat(userBean.totalBalance) < withdrawCash) {
                    ToastUtils.showToast(this,"用户余额必须大于"+withdrawCash);
                    return false;
                }
                // 提现金额 不能大于 用户的余额
                if(Double.parseDouble(ed_cash_withdraw.getText().toString())>Float.parseFloat(userBean.totalBalance)){
                    ToastUtils.showToast(this,"提现金额不能大于用户的余额");
                    return false;
                }


                //提现的金额必须是100的倍数
                if(Double.parseDouble(ed_cash_withdraw.getText().toString())<withdrawCash){
                    ToastUtils.showToast(this,"提现金额必须大于"+withdrawCash);
                    return false;
                }

                //提现的金额必须是100的倍数
                if(Double.parseDouble(ed_cash_withdraw.getText().toString())%withdrawCash!=0){
                    ToastUtils.showToast(this,"提现金额必须是"+withdrawCash+"的整数倍");
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

    /**
     * 获取推广信息接口
     */
    private void getPromoteInfo() {
        try {
            OkHttpUtils.post()
                    .url(Constant.BASEURL+Constant.ACCOUNT_INFO)
                    .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                    .addParams("loginToken", SPUtils.getInstance(this).getString("loginToken"))
                    .addParams("account", UserDao.getLocalUser().userName)
                    .addParams("appType",Constant.platform_id)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            UserInfoResponse oneBannerBean = JSON.parseObject(response, UserInfoResponse.class);
                            if (oneBannerBean.getCode() == 200 && oneBannerBean.getData()!=null) {
                                //为 200的响应码
                                float balance=oneBannerBean.getData().getBalance();
                                DecimalFormat decimalFormat=new DecimalFormat("0.00");
                                String promoteNum=oneBannerBean.getData().getPromoteNum();
                                String eqCodeUrl=oneBannerBean.getData().getEqCodeUrl();
                                boolean lockStatus=oneBannerBean.getData().getLockStatus().equals("1")?true:false;
                                try {
                                    UserBean userBean=UserDao.getLocalUser();
                                    userBean.totalBalance=decimalFormat.format(balance);
                                    userBean.zcodeImgUrl=eqCodeUrl;
                                    userBean.isExtendistionState=lockStatus;
                                    userBean.extensitionCount=Integer.parseInt(promoteNum);

                                    //获取一级 和 二级推广收益
                                    userBean.firstPromotionBenfits=decimalFormat.format(oneBannerBean.getData().getFirstIncome());
                                    userBean.secondPormotionBenfits=decimalFormat.format(oneBannerBean.getData().getSecondIncome());;
                                    userBean.thirdPromotionBenfits=decimalFormat.format(oneBannerBean.getData().getThirdIncome());;

                                    UserDao.saveUpDate(userBean);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //重新初始化数据
                                initData();
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }
}
