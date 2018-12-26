package com.jh.lottery.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import allen.com.rntestproject.R;

import java.util.HashMap;
import java.util.Map;

public class PopWindowUtils {

    public static void showSheetPop(final Activity activity, View view, String[] strs, final SheetCallback callback){
        if(null == activity || null == view || null == callback){
            return;
        }
        final PopupWindow mPopupWindow;
        View popView = activity.getLayoutInflater().inflate(R.layout.layout_default_popupwindow, null);
        mPopupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        setBackgroundAlpha(activity,0.5f);//设置屏幕透明度
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), (Bitmap) null));
        mPopupWindow.setFocusable(true);
        //出现动画
        mPopupWindow.setAnimationStyle(R.style.popupWindowSheetAnimation);
        TextView default_pop_tv_1 = (TextView) popView.findViewById(R.id.default_pop_tv_1);
        default_pop_tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                callback.callBack("",1,"");
            }
        });
        TextView default_pop_tv_2 = (TextView) popView.findViewById(R.id.default_pop_tv_2);
        default_pop_tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                callback.callBack("",2,"");
            }
        });
        TextView default_pop_tv_3 = (TextView) popView.findViewById(R.id.default_pop_tv_3);
        default_pop_tv_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(activity,1.0f);
            }
        });
        if(null != strs && strs.length == 2){
            default_pop_tv_1.setText(strs[0]);
            default_pop_tv_2.setText(strs[1]);
        }
        mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }



    @SuppressLint("WrongViewCast")
    public static void showInputView(final Activity activity, View view, final InputCallback callback){


        final View popView = activity.getLayoutInflater().inflate(R.layout.layout_inputview,null);
        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final EditText name = popView.findViewById(R.id.name);
        final EditText phone = popView.findViewById(R.id.phone);
        final EditText address = popView.findViewById(R.id.address);

        setBackgroundAlpha(activity,0.5f);//设置屏幕透明度
        popupWindow.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), (Bitmap) null));

        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setAnimationStyle(R.style.popupWindowScaleAnimation);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                setBackgroundAlpha(activity,1.0f);
            }
        });
        popView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(activity,1.0f);
            }
        });
        popView.findViewById(R.id.enter_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundAlpha(activity,1.0f);
                popupWindow.dismiss();

                String nameStr = name.getText().toString();
                String phoneStr = phone.getText().toString();
                String addressStr = address.getText().toString();
                if (nameStr.isEmpty()){
                    XToast.warning("姓名不能为空!");
                    return;
                }
                if (phoneStr.isEmpty()){
                    XToast.warning("手机号不能为空!");
                    return;
                }
                if (addressStr.isEmpty()){
                    XToast.warning("收货地址不能为空!");
                    return;
                }
                Map<String,String> map = new HashMap<>();
                map.put("contactsTel",phoneStr);
                map.put("contactsAdress",addressStr);
                map.put("contactsName",nameStr);
                callback.callback(map);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    public interface SheetCallback{
        void callBack(String json, int code, String msg);
    }

    public interface InputCallback{
        void callback(Map<String, String> map);
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     *            屏幕透明度0.0-1.0 1表示完全不透明
     */
    public static void setBackgroundAlpha(Activity activity,float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        //下面是为了兼容华为手机
        if (bgAlpha == 1) {
            //不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            //此行代码主要是解决在华为手机上半透明效果无效的bug
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

}
