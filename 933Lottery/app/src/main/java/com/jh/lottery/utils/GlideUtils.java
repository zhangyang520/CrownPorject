package com.jh.lottery.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import allen.com.rntestproject.MainApplication;
import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/7/30.
 */

public class GlideUtils {

    public static void loadAvatar(Context context, String url, ImageView imageView){

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.placeholder(MainApplication.getInstance().getResources().getDrawable(R.mipmap.def_header));
        options.priority(Priority.NORMAL);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

}
