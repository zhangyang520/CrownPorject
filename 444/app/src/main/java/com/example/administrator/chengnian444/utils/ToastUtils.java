package com.example.administrator.chengnian444.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.Toast;

/**
 *  弹出吐司的工具类
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/2713:42
 */
public class ToastUtils {

    private  static  Toast toast;
    /**
     * todo 以后需要修改 对应的消失的动画
     * 弹出 吐司
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg) {
        toast= Toast.makeText(context,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void dismissToast(){
        if(toast!=null){
            toast.cancel();
            toast=null;
        }
    }
}
