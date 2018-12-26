package com.example.administrator.chengnian444.activity;

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

     @OnClick({R.id.back,R.id.tv_shouyi_suanfa})
     public void onClickView(View view) {

         switch (view.getId()) {
             case R.id.back:
                 finish();
                 break;

             case R.id.tv_shouyi_suanfa:
                 startActivity(new Intent(this,ExtenditionIncomeAcActivity.class));
                 break;
         }
     }
}
