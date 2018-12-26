package com.example.administrator.chengnian933.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.DownloadListener;

public class MyDownLoadListener implements DownloadListener {
    private Context context;

    public MyDownLoadListener(Context context) {
        this.context = context;
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        Log.i("hcy", "url="+url);

        Log.i("hcy", "userAgent="+userAgent);

        Log.i("hcy", "contentDisposition="+contentDisposition);

        Log.i("hcy", "mimetype="+mimetype);

        Log.i("hcy", "contentLength="+contentLength);

        Uri uri = Uri.parse(url);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        context.startActivity(intent);
    }


}
