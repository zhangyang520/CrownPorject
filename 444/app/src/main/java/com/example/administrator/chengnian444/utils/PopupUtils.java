package com.example.administrator.chengnian444.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
        public void onOk();

        /**
         * 取消的点击事件
         */
        public void onCancel();
    }

    static PopupWindow popupWindow;

    public static void showPayType(Context context, final ClickOnListener listener, View rootView){
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
                    listener.onOk();
                }
            }
        });
    }
}
