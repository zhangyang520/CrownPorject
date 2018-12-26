package com.example.administrator.chengnian933.activity;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.adapter.HomeChangerAdapter;
import com.example.administrator.chengnian933.base.BaseActivity;
import com.example.administrator.chengnian933.bean.HomeChangeBean;
import com.example.administrator.chengnian933.bean.OneBannerBean;
import com.example.administrator.chengnian933.bean.ShareBean;
import com.example.administrator.chengnian933.http.Constant;
import com.example.administrator.chengnian933.utils.SPUtils;
import com.example.administrator.chengnian933.utils.StatusBarCompat.StatusBarCompat;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import okhttp3.Call;

public class MediaActivity extends BaseActivity {
    @Bind(R.id.player)
    JzvdStd player;
    @Bind(R.id.play_recycler)
    RecyclerView playRecycler;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.change9)
    ImageView change9;
    @Bind(R.id.move_title)
    TextView moveTitle;
   /* @Bind(R.id.move_cishu)
    TextView moveCishu;*/
    @Bind(R.id.home_iv)
    ImageView homeIv;
    @Bind(R.id.media_image)
    ImageView mediaImage;
    @Bind(R.id.share)
    ImageView share;

    private String typenum;
    private MyThread thread;
    private boolean isLogin;
    private String url;
    private String url1;
    private String id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.black));
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        String moveUrl = intent.getStringExtra("moveUrl");
        String image = intent.getStringExtra("image");
        typenum = intent.getStringExtra("typenum");
        String name = intent.getStringExtra("name");
       // getLookCount();
        getOneBannerHttp();
        moveTitle.setText(name);
        isLogin = SPUtils.getInstance(this).getBoolean("isLogin");
        Log.d("hcy", isLogin + "");
        player.setUp(moveUrl, "", JzvdStd.SCREEN_WINDOW_NORMAL);
        Glide.with(this).load(image).into(player.thumbImageView);
        //如果不是登录状态，禁止seekBar的触摸事件,并且限制播放时间
        if (!isLogin) {
            player.progressBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            thread = new MyThread();
            thread.start();
        }

        playRecycler.setHasFixedSize(true);
        playRecycler.setNestedScrollingEnabled(false);
        playRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        getMoveData(typenum);
        //playRecycler.setAdapter(adapter);
    }

    /*private void getLookCount() {
       OkHttpUtils.post()
               .url(Constant.WATCHCOUNT)
               .addHeader("Content-Type", "application/json")
               .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
               .addParams("movieId",id)
               .build()
               .execute(new StringCallback() {
                   @Override
                   public void onError(Call call, Exception e, int id) {

                   }

                   @Override
                   public void onResponse(String response, int id) {
                    Log.d("hcy",response.toString());
                   }
               });
    }
*/
    private void getOneBannerHttp() {
        OkHttpUtils.post()
                .url(Constant.GETONEBANNER)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("type", typenum)
                .addParams("appType", "933")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        OneBannerBean oneBannerBean = JSON.parseObject(response, OneBannerBean.class);
                        if (oneBannerBean.getCode() == 200) {
                            String image = oneBannerBean.getData().getImage();
                            url1 = oneBannerBean.getData().getUrl();
                            Glide.with(MediaActivity.this).load(image).into(mediaImage);
                        }
                    }
                });
    }


    private void getMoveData(String typenum) {
        dialogShow();
        OkHttpUtils.post()
                .url(Constant.HOMECHANGE)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("typeNum", typenum)
                .addParams("count", "12")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dialogDismiss();
                        homeIv.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dialogDismiss();
                        HomeChangeBean homeBean = JSON.parseObject(response, HomeChangeBean.class);
                        if (homeBean.getCode() == 200) {
                            List<HomeChangeBean.DataBean> data = homeBean.getData();
                            HomeChangerAdapter adapter = new HomeChangerAdapter(R.layout.test, data, MediaActivity.this, true);

                            playRecycler.setAdapter(adapter);
                        } else {
                            Toast.makeText(MediaActivity.this, homeBean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshUI();
        }
    };

    private void refreshUI() {
        String s = player.currentTimeTextView.getText().toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));

        long time = 0;
        try {
            time = simpleDateFormat.parse(s).getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("hcy", time + "");
        if (time > 30000) {
            Jzvd.releaseAllVideos();
            Toast.makeText(MediaActivity.this, "登录后观看更多", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isLogin) {
            if (mHandler != null) {
                mHandler.removeCallbacksAndMessages(null);
                thread.close();
            }
        }
    }


    @OnClick({R.id.back, R.id.change9, R.id.media_image, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.change9:
                getMoveData(typenum);
                break;
            case R.id.media_image:
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", url1);
                startActivity(intent);
                break;
            case R.id.share:
                getShareContent();
                break;
        }
    }

    private void getShareContent() {
        OkHttpUtils.post()
                .url(Constant.SHARE)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                .addParams("type", "933")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        ShareBean shareBean = JSON.parseObject(response, ShareBean.class);
                        if (shareBean.getCode()==200){
                            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            // 将文本内容放到系统剪贴板里。
                            cm.setText(shareBean.getData().getShareContent());
                            Toast.makeText(MediaActivity.this, "链接复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
                        }else {

                        }

                    }
                });

    }

    private class MyThread extends Thread {

        private boolean stop = false;

        @Override
        public void run() {
            while (!stop) {
                try {
                    Thread.sleep(10000);
                    mHandler.sendMessage(mHandler.obtainMessage());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void close() {
            stop = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isLogin){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("温馨提示");
            builder.setMessage("游客试看5分钟，会员免费观看");
            builder.setCancelable(true);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }
    }
}
