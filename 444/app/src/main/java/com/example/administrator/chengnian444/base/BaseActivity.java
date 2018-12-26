package com.example.administrator.chengnian444.base;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.administrator.chengnian444.utils.SPUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


public class BaseActivity extends AppCompatActivity {

    private ZLoadingDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ZLoadingDialog(this);
        dialog.setLoadingBuilder(Z_TYPE.SINGLE_CIRCLE)//设置类型
                .setLoadingColor(Color.GRAY)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CC111111"));
    }

    public void dialogShow(){
        dialog.show();
    }
    public void dialogDismiss(){
        dialog.dismiss();
    }

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public void exitDialog(){
   /* AlertDialog.Builder alertDialog =new AlertDialog.Builder(this);
    alertDialog.setMessage("当前账号在其他设备登录");
    alertDialog.setCancelable(false);
    alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            SPUtils.getInstance(BaseActivity.this).put("isLogin",false);
            SPUtils.getInstance(BaseActivity.this).put("loginToken","");
            dialog.dismiss();
        }
    });
    AlertDialog dialog = alertDialog.create();
    dialog.show();*/
        SPUtils.getInstance(BaseActivity.this).put("isLogin",false);
        SPUtils.getInstance(BaseActivity.this).put("loginToken","");
        Toast.makeText(this,"当前账号在其他设备登录",Toast.LENGTH_LONG).show();

    }
}
