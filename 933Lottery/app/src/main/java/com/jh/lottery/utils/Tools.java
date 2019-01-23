package com.jh.lottery.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.jh.lottery.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

/**
 * Created by sangcixiang on 2017/9/1.
 */

public class Tools {

    /**
     * @return 当前UNIX时间戳
     */
    public static String getTime(){
        long time= System.currentTimeMillis()/1000;
        String str= String.valueOf(time);
        return str;
    }

    public static JSONObject stringToJson(String str){
        JSONObject jsonObject = null;
        try {
            jsonObject  = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = context.getApplicationInfo().packageName + ".fileprovider";
            uri = FileProvider.getUriForFile(context.getApplicationContext(), authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
    /**
     * 获取SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;
    }

    /** 获取本地头像
     * @return
     */
    public static File getLocalPic(){
        File file = new File(AppConfig.UPLOADPICPATH + "myPic.jpg");
        return file;
    }

    //相册裁剪
    public static void cutImage(Uri uri, Activity activity, int Code, String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 0.5);
        intent.putExtra("aspectY", 0.5);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, Code);
    }
    //相机裁剪
    public static void cutImage(Uri uri, Activity activity, int Code) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);

        }
        // 设置裁剪
        intent.putExtra("crop", "false");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 0.5);
        intent.putExtra("aspectY", 0.5);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", false);//去除默认的人脸识别，否则和剪裁匡重叠
        intent.putExtra("outputFormat", "JPEG");
        activity.startActivityForResult(intent, Code);
    }
    public static String transferFormat(String inTime){
        try{
            SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
            Date tempDate =null;
            String outTime = null;
            tempDate = s1.parse(inTime);
            outTime = s2.format(s2.parse(s1.format(tempDate)));
            return outTime;
        }catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
