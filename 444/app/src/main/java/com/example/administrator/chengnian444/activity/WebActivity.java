package com.example.administrator.chengnian444.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.utils.MyDownLoadListener;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WebActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;
    private WebView webView;
    private ProgressBar pd;
    private WebSettings webSettings;
    private final int SDK_PERMISSION_REQUEST = 11;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web1);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.black));
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        //加载网页，并且设置一些属性
        initWebView();
        //对于6.0手机，获取动态授权
        // getPermissions();
    }

    private void initWebView() {
        webView = (WebView) findViewById(R.id.webView); //初始化控件
        pd = ((ProgressBar) findViewById(R.id.progressbar));
        // String url = "http://www.baidu.com"; //初始化网页


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

        });


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pd.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    pd.setProgress(newProgress);
                }
            }
        });


        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        webView.loadUrl(url);
        webView.setDownloadListener(new MyDownLoadListener(this));
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }


   /* private void getPermissions() {
        //如果权限>=5.0,就需要动态授权
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //准备一个集合
            ArrayList<String> permissions = new ArrayList<>();
            //如果读取SD卡的权限不存在，就创建一个保存在集合里面
            if(checkSelfPermission(READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                permissions.add(READ_EXTERNAL_STORAGE);
            }

            if(permissions.size()>0){
                requestPermissions(permissions.toArray(new String[permissions.size()]),SDK_PERMISSION_REQUEST);
            }else {
                initWebView();
            }
        }else {
            initWebView();
        }
    }*/



   /* @Override //6.0动态授权
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case SDK_PERMISSION_REQUEST:
                if(grantResults.length>0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){
                    //允许
                    Toast.makeText(this, "已授权", Toast.LENGTH_SHORT).show();
                }else {
                    //不允许
                    Toast.makeText(this, "拒绝授权", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override //当点击了返回键并且能够返回的时候
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){ //如果点击了返回键并且webView能够返回
            webView.goBack();
            return true;
        }

//        //如果能返回并且能够一次性返回两步的操作
//        if(keyCode ==  KeyEvent.KEYCODE_BACK && webView.canGoBackOrForward(-2)){
//            webView.goBackOrForward(-2);
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }*/

}
