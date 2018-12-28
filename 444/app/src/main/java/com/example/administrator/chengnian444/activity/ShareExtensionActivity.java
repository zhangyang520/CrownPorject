package com.example.administrator.chengnian444.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.utils.ToastUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_share_extention);
        ButterKnife.bind(this);
        Glide.with(this).load(R.mipmap.iv_mine_share_bg).into(iv_bg);
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
                 copyFromText();
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
