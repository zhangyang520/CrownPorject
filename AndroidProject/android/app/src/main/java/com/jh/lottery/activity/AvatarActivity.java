package com.jh.lottery.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jh.lottery.AppConfig;
import allen.com.rntestproject.R;

import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.model.UserModel;
import com.jh.lottery.utils.HttpTool;
import com.jh.lottery.utils.PhotoUtils;
import com.jh.lottery.utils.Tools;
import com.jh.lottery.utils.UIHelper;
import com.jh.lottery.view.PopWindowUtils;
import com.jh.lottery.view.XLoadingDialog;
import com.jh.lottery.view.XToast;
import com.zhy.m.permission.MPermissions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sangcixiang on 2018/8/7.
 */

public class AvatarActivity extends BaseActivity {
    private ImageView avatar;

    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;

    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    @Override
    public int getLayoutId() {
        return R.layout.activity_avatar;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void initView() {

        showBackButton();
        setTitle("头像");
        setRight_icon(getResources().getDrawable(R.mipmap.barbuttonicon_more));
        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetAction();
            }
        });
        avatar = find(R.id.avatar);
        WindowManager wm = getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) avatar.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = width;
        avatar.setLayoutParams(linearParams);

    }

    private void sheetAction(){
        PopWindowUtils.showSheetPop(this,getWindow().getDecorView(), new String[]{"拍照", "相册"}, new PopWindowUtils.SheetCallback() {
            @Override
            public void callBack(String json, int code, String msg) {

                if(code == 1){
                    autoObtainCameraPermission();
                }else{
                    autoObtainStoragePermission();
                }
            }
        });
    }

    private void uploadImage(){
        Map<String,String> map = new HashMap<>();
        map.put("acctBaseId", UserModel.getUserInfo().getAcctBaseId());
        List<File> list = new ArrayList<>();
        list.add(fileCropUri);
        XLoadingDialog.with(this).setMessage("上传中...").show();
        HttpTool.getHttpFile(AppConfig.uploadImageUrl, map, list, new HttpTool.HttpCallback() {
            @Override
            public void onSuccess(JSONObject json) {
                Log.i("uploadImage",json.toString());
                XLoadingDialog.with(AvatarActivity.this).dismiss();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                Log.i("onError",msg);
                XLoadingDialog.with(AvatarActivity.this).dismiss();
            }
        });
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                XToast.warning("您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            if (hasSdcard()) {
                imageUri = Uri.fromFile(fileUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    imageUri = FileProvider.getUriForFile(AvatarActivity.this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
            } else {
                XToast.warning("设备没有SD卡！");
            }
        }
    }
    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }

    }
    private int output_X = 480;
    private int output_Y = 480;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CODE_CAMERA_REQUEST://拍照完成回调
                    cropImageUri = Uri.fromFile(fileCropUri);
                    PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    break;
                case CODE_GALLERY_REQUEST://访问相册完成回调
                    if (hasSdcard()) {
                        cropImageUri = Uri.fromFile(fileCropUri);
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                    } else {
                        XToast.warning("设备没有SD卡！");
                    }
                    break;
                case CODE_RESULT_REQUEST:
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                    if (bitmap != null) {
                        uploadImage();
                        avatar.setImageBitmap(bitmap);

                    }
                    break;
            }
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST_CODE: {//调用系统相机申请拍照权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            imageUri = FileProvider.getUriForFile(this, "com.zz.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        XToast.warning("设备没有SD卡！");
                    }
                } else {
                    XToast.warning("请允许打开相机！！");
                }
                break;
            }
            case STORAGE_PERMISSIONS_REQUEST_CODE://调用系统相册申请Sdcard权限回调
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    XToast.warning("请允许打操作SDCard！！");
                }
                break;
        }
    }

    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }
    private void deleteExistsPic(){
        File pic = Tools.getLocalPic();
        if (pic.exists()) {
            pic.delete();
        }
    }
}
