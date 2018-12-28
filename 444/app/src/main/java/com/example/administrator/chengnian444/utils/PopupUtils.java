package com.example.administrator.chengnian444.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.administrator.chengnian444.R;

/**
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/2421:06
 */
public class PopupUtils {

    public interface ClickOnListener {
        /**
         * ok的点击事件
         */
        public void onOk(int currentCashType);

        /**
         * 取消的点击事件
         */
        public void onCancel();
    }

    static PopupWindow popupWindow;
    static int currentType=0;
    /**
     * 展示 提现方式的对话框
     * @param context
     * @param listener
     * @param rootView
     * @param type  0: 支付宝   1:微信
     */
    public static void showPayType(Context context, final ClickOnListener listener, View rootView,int type){
        if(popupWindow!=null && popupWindow.isShowing()){
            return;
        }
        popupWindow=new PopupWindow(context,null,R.style.Implicit_Dialog);
        View contentView = View.inflate(context, R.layout.popup_pay_way, null);
        popupWindow.setWidth(context.getResources().getDisplayMetrics().widthPixels);
        popupWindow.setHeight((int)(context.getResources().getDisplayMetrics().heightPixels));
        popupWindow.setContentView(contentView);
        popupWindow.setOutsideTouchable(false);
        popupWindow.showAtLocation(rootView,Gravity.BOTTOM,0,0);
        currentType=type;

        //iv_mine_rect_gray_cash 灰色的背景
        //iv_mine_rect_white_cash 白色的背景

        final Button btn_alipay=contentView.findViewById(R.id.btn_alipay);
        final Button btn_wechat=contentView.findViewById(R.id.btn_wechat);

         btn_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentType=0;
                if(currentType==0){
                    //支付宝
                    btn_alipay.setBackgroundResource(R.mipmap.iv_mine_rect_gray_cash);
                    btn_wechat.setBackgroundResource(R.mipmap.iv_mine_rect_white_cash);
                }else{
                    //微信支付方式
                    btn_alipay.setBackgroundResource(R.mipmap.iv_mine_rect_white_cash);
                    btn_wechat.setBackgroundResource(R.mipmap.iv_mine_rect_gray_cash);
                }
            }
        });



        btn_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentType=1;
                if(currentType==0){
                    //支付宝
                    btn_alipay.setBackgroundResource(R.mipmap.iv_mine_rect_gray_cash);
                    btn_wechat.setBackgroundResource(R.mipmap.iv_mine_rect_white_cash);
                }else{
                    //微信支付方式
                    btn_alipay.setBackgroundResource(R.mipmap.iv_mine_rect_white_cash);
                    btn_wechat.setBackgroundResource(R.mipmap.iv_mine_rect_gray_cash);
                }
            }
        });

        if(type==0){
            //支付宝
            btn_alipay.setBackgroundResource(R.mipmap.iv_mine_rect_gray_cash);
            btn_wechat.setBackgroundResource(R.mipmap.iv_mine_rect_white_cash);
        }else{
            //微信支付方式
            btn_alipay.setBackgroundResource(R.mipmap.iv_mine_rect_white_cash);
            btn_wechat.setBackgroundResource(R.mipmap.iv_mine_rect_gray_cash);
        }

        View blankView=contentView.findViewById(R.id.view_blank);
        blankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(popupWindow!=null && popupWindow.isShowing()){
                     popupWindow.dismiss();
                }
            }
        });
        TextView tv_cacel=contentView.findViewById(R.id.tv_cacel);
        tv_cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    popupWindow.dismiss();
                    listener.onCancel();
                }
            }
        });

        TextView tv_ok=contentView.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    popupWindow.dismiss();
                    listener.onOk(currentType);
                }
            }
        });
    }
}
