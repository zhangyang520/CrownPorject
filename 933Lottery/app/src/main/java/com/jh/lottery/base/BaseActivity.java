package com.jh.lottery.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import allen.com.rntestproject.R;

public abstract class BaseActivity extends XActivity implements View.OnClickListener {

    protected ImageButton backButton;
    protected TextView title;
    protected TextView right;
    protected ImageButton right_button;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        backButton = find(R.id.back_buton);
        title = find(R.id.navigation_title);
        right = find(R.id.navigation_right);
        right_button = find(R.id.navigation_right_icon);
//        XStatusBar.setColor(this,getResources().getColor(R.color.mainColor));
        fullScreen(this);
    }

    protected void setTitle(String str){
        if(this.title != null){
            title.setText(str);
        }
    }

    protected void setRight_icon(Drawable drawable){
        if (right_button != null){
            right_button.setVisibility(View.VISIBLE);
            right_button.setImageDrawable(drawable);
        }
    }


    protected void showBackButton(){
        if (this.backButton != null){
            this.backButton.setVisibility(View.VISIBLE);
            backButton.setOnClickListener(this);
        }
    }

    protected void setRightTitle(String str){
        if (null != right){
            right.setText(str);
            right.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_buton:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    protected <T extends View> T find(int resId) {
        return (T) findViewById(resId);
    }

    public void JumpToActivity(Class<?> cls) {
        JumpToActivity(cls, null);
    }

    public void JumpToActivity(Class<?> cls, Intent data) {
        Intent intent = new Intent(this, cls);
        // 设置跳转标志为如此Activity存在则把其从任务堆栈中取出放到最上方
        if (data != null)
            intent.putExtras(data);
        startActivity(intent);
//        setOverridePendingTransition();
    }

    public void startActivity(Class<?> cls, Intent data){
        Intent intent = new Intent(this, cls);
        if (data != null)
            intent.putExtras(data);
        startActivity(intent);
    }

    public void setOverridePendingTransition() {
        overridePendingTransition(0, 0);
    }


    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

}
