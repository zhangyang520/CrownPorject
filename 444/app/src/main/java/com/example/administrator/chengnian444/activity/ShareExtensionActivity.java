package com.example.administrator.chengnian444.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.bean.PromotionInfoResponse;
import com.example.administrator.chengnian444.bean.UserBean;
import com.example.administrator.chengnian444.bean.UserInfoResponse;
import com.example.administrator.chengnian444.dao.UserDao;
import com.example.administrator.chengnian444.exception.ContentException;
import com.example.administrator.chengnian444.http.Constant;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import okhttp3.Call;

/**
  *  分享推广的界面
     * @Title:
     * @ProjectName
     * @Description: TODO
     * @author zhangyang
     * @date
     */
public class ShareExtensionActivity extends AppCompatActivity {


     @Bind(R.id.back)
     ImageView back;

     @Bind(R.id.iv_bg)
     ImageView iv_bg;

     //我的推广码
    @Bind(R.id.tv_my_code)
    TextView tv_my_code;

    //二维码 对应的图片
    @Bind(R.id.iv_zcode)
    AppCompatImageView iv_zcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_share_extention);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.black));
        ButterKnife.bind(this);
        Glide.with(this).load(R.mipmap.iv_mine_share_bg).into(iv_bg);

        //初始化数据
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        try {
            UserBean userBean=UserDao.getLocalUser();
            //二维码图片的地址
            Glide.with(this).load(userBean.zcodeImgUrl).into(iv_zcode);
            if(userBean.extendistinCode.equals("")){
                //如果为空字符串
                tv_my_code.setVisibility(View.GONE);
                //进行请求数据
                getUserExtenditionCode();
            }else{
                tv_my_code.setVisibility(View.VISIBLE);
                tv_my_code.setText(userBean.extendistinCode);
            }
        } catch (ContentException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取用户 推广码的信息
     */
    private void getUserExtenditionCode() {
        try {
            OkHttpUtils.post()
                    .url(Constant.BASEURL+Constant.PROMOTION_INFO)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", SPUtils.getInstance(this).getString("token"))
                    .addParams("loginToken", SPUtils.getInstance(this).getString("loginToken"))
                    .addParams("account", UserDao.getLocalUser().userName)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            PromotionInfoResponse oneBannerBean = JSON.parseObject(response, PromotionInfoResponse.class);
                            if (oneBannerBean.getCode() == 200) {
                                //为 200的响应码
                                String promoteCode =oneBannerBean.getData().getPromoteCode();
                                String downloadUrl =oneBannerBean.getData().getUrl();

                                try {
                                    UserBean userBean=UserDao.getLocalUser();
                                    userBean.extendistinCode=promoteCode;
                                    userBean.url=downloadUrl;
                                    //用户 的信息的保存
                                    UserDao.saveUpDate(userBean);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //重新初始化数据
                                initData();
                            }
                        }
                    });
        } catch (Exception e) {

        }
    }


    @OnClick({R.id.back,R.id.tv_shouyi_suanfa,R.id.ib_share})
     public void onClickView(View view) {

         switch (view.getId()) {
             case R.id.back:
                 finish();
                 break;

             case R.id.tv_shouyi_suanfa:
                 startActivity(new Intent(this,ExtenditionIncomeAcActivity.class));
                 break;


             case R.id.ib_share:
                 if(tv_my_code.getVisibility()==View.VISIBLE &&
                         !tv_my_code.getText().toString().equals("")){
                     //如果推广码 有数据
                     copyFromText();
                 }else{
                     //如果没有对应的数据
                     ToastUtils.showToast(this,"暂无推广码");
                 }
                 break;
         }
     }


     private ClipboardManager clipboardManager;
     /**
      * 将内容 粘贴到剪贴板中
      */
     public void copyFromText(){
         if (clipboardManager == null) {
             clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
         }
         ClipData clipData = ClipData.newPlainText("simple Text",
                 "好多小姐姐在劈叉，快来快来睹一睹！我的推广码：123456，输入推广码即可获得现金奖励哦！http://779.sjxw365.com");
         clipboardManager.setPrimaryClip(clipData);
         ToastUtils.showToast(this,"复制成功！将链接粘贴发送给好友下载APP，好友注册后输入您的推广码可获得邀请现金");
     }
}
