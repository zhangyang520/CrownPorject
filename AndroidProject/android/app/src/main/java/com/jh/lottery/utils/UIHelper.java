package com.jh.lottery.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import allen.com.rntestproject.MainApplication;
import allen.com.rntestproject.R;

public class UIHelper {
    private static Toast toast;
    private static final int MIN_CLICK_DELAY_TIME = 1500;
    private static long lastClickTime;

    public static void showMessage(String message) {
        if (toast == null) {
            toast = Toast.makeText(MainApplication.getInstance(), message,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 20);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    public static void alertDialog(Context context, String message){
        new AlertDialog.Builder(context).
                setTitle("提示!").setMessage(message).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {}
                }).create().show();
    }
    public static class CreateLoadingDialog{

        private Dialog loadingDialog;
        private TextView tipTextView;

        public CreateLoadingDialog(Context context, String msg) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.layout_dialog_loading, null);// 得到加载view
            tipTextView = (TextView) v.findViewById(R.id.dialog_text_loading);// 提示文字
            if(!TextUtils.isEmpty(msg)) {
                tipTextView.setText(msg);// 设置加载信息
            }

            loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
            loadingDialog.setCancelable(true);// 不可以用“返回键”取消
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setContentView(v,new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));// 设置布局
        }

        public void setText(String text){
            if(!TextUtils.isEmpty(text)) {
                tipTextView.setText(text);
            }
        }

        public void showLoadingDialog(){
            if(null != loadingDialog && !loadingDialog.isShowing()){
                loadingDialog.show();
            }
        }
        public void dismissLoadingDialog(){
            if(null != loadingDialog && loadingDialog.isShowing()){
                loadingDialog.dismiss();
            }
        }
    }

    /** 防止过快点击
     * @return
     */
    public synchronized static boolean isFastClick() {
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) < MIN_CLICK_DELAY_TIME) {
            return true;
        }
        lastClickTime = curClickTime;
        return false;
    }
}
