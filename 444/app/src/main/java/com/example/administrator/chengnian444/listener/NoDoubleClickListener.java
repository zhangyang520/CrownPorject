package com.example.administrator.chengnian444.listener;

import android.view.View;

import java.util.Calendar;

/**
 *  是否双击的点击事件
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2019/1/519:56
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 3000;
    private long lastClickTime = 0;
    boolean isCanClick=false;
    @Override
    public void onClick(View view) {

        long currentTime=Calendar.getInstance().getTimeInMillis();
        System.out.println("NoDoubleClickListener ....zhoangodg flag:"+(currentTime - lastClickTime > MIN_CLICK_DELAY_TIME)+"..isCanClick:"+isCanClick);
        if ((currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) || isCanClick) {
            lastClickTime = currentTime;
            isCanClick=false;
            //双击的点击事件
            onNoDoubleClick(view);
        }else{
            lastClickTime = currentTime;
        }
    }

    public boolean isCanClick() {
        return isCanClick;
    }

    public void setCanClick(boolean canClick) {
        isCanClick = canClick;
    }

    /**
     * 缺省的点击事件
     */
    public  abstract void onNoDoubleClick(View v);
}
