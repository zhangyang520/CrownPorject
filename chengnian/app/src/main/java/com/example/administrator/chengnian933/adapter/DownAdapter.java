package com.example.administrator.chengnian933.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.activity.WebActivity;
import com.example.administrator.chengnian933.bean.YingyongBean;

import java.util.List;

public class DownAdapter extends BaseQuickAdapter<YingyongBean.DataBean,BaseViewHolder> {

        private Context context;
        public DownAdapter(int layoutResId, @Nullable List<YingyongBean.DataBean> data,Context context) {
        super(layoutResId, data);
       this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final YingyongBean.DataBean item) {
        RelativeLayout view = helper.getView(R.id.goto_caip);
        helper.setText(R.id.tv_caipiao,item.getName());
        ImageView imageView = helper.getView(R.id.image_caipiao);
        Glide.with(context).load(item.getImg()).into(imageView);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", item.getApplicationUrl());
                context.startActivity(intent);
            }
        });
    }
}
