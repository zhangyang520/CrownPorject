package com.example.administrator.chengnian444.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;


/**
 * Created by Administrator on 2016/12/1.
 */
public class CommonUtils {

    private static InputMethodManager inputMethodManager;

    /**
     * 弹吐司
     *
     * @param ctx
     * @param str
     */
    public static void showToast(Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * @param @param  bitmap
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: bitmapToBase64
     * @Description:
     */

    public static String bitmapToBase64(Bitmap bitmap) {

        // 要返回的字符串
        String reslut = null;

        ByteArrayOutputStream baos = null;

        try {

            if (bitmap != null) {

                baos = new ByteArrayOutputStream();
                /**
                 * 压缩只对保存有效果bitmap还是原来的大小
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);

                baos.flush();
                baos.close();
                // 转换为字节数组
                byte[] byteArray = baos.toByteArray();

                // 转换为字符串
                reslut = Base64.encodeToString(byteArray, Base64.DEFAULT);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return reslut;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }

    /**
     * 切换软键盘的状态
     * 如当前为收起变为弹出,若当前为弹出变为收起
     */
    public static void toggleInput(Context context) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void showInput(Context context, View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    public static void call(Context cont, String phone) {
        String[] split = phone.split("-");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + split[0] + split[1]));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        cont.startActivity(intent);
    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }



    /**
     * @param s 银行名
     * @return
     */
    public static boolean isSupporBank(String s) {
        if (s.contains("建设银行")) {
            return true;
        }
        if (s.contains("工商银行")) {
            return true;
        }
        if (s.contains("中国银行")) {
            return true;
        }
        if (s.contains("农业银行")) {
            return true;
        }
        if (s.contains("交通银行")) {
            return true;
        }
        if (s.contains("招商银行")) {
            return true;
        }
        if (s.contains("中信银行")) {
            return true;
        }
        if (s.contains("民生银行")) {
            return true;
        }
        if (s.contains("兴业银行")) {
            return true;
        }
        if (s.contains("浦发银行")) {
            return true;
        }
        if (s.contains("邮政储蓄银行")) {
            return true;
        }
        if (s.contains("光大银行")) {
            return true;
        }
        if (s.contains("平安银行")) {
            return true;
        }
        if (s.contains("华夏银行")) {
            return true;
        }
        if (s.contains("北京银行")) {
            return true;
        }
        if (s.contains("广发银行")) {
            return true;
        }
        if (s.contains("上海银行")) {
            return true;
        }
        if (s.contains("江苏银行")) {
            return true;
        }
        if (s.contains("恒丰银行")) {
            return true;
        }
        if (s.contains("浙商银行")) {
            return true;
        }
        if (s.contains("南京银行")) {
            return true;
        }
        return false;
    }


    /**
     * 清除adapter 的parentview
     */
    public static void removeEmptyView(BaseQuickAdapter adapter) {
        if (adapter != null) {
            if (adapter.getEmptyView() != null) {
                if ((adapter.getEmptyView().getParent())!=null){
                    ((ViewGroup) adapter.getEmptyView().getParent()).removeAllViews();
                }
            }
        }
    }
}
