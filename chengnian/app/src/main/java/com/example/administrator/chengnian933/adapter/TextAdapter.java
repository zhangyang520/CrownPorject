package com.example.administrator.chengnian933.adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chengnian933.R;

import java.util.List;

import com.example.administrator.chengnian933.activity.MediaActivity;
import com.example.administrator.chengnian933.bean.HomeBean;

public class TextAdapter extends BaseQuickAdapter<HomeBean.DataBean.LiveMoviesBean,BaseViewHolder> {
    private Context context;

    public TextAdapter(int layoutResId, @Nullable List<HomeBean.DataBean.LiveMoviesBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeBean.DataBean.LiveMoviesBean item) {
        ImageView view = helper.getView(R.id.iv_it);
        String bigimage = item.getBigimage();
        Glide.with(context).load(bigimage).placeholder(R.mipmap.photo_zhanwei1).centerCrop().into(view);
        helper.setText(R.id.tv,item.getName());
        helper.setText(R.id.tv_time,item.getMoveTime());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MediaActivity.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("moveUrl",item.getUrl());
                intent.putExtra("image",item.getBigimage());
                intent.putExtra("typenum",item.getTypenum()+"");
                intent.putExtra("name",item.getName());
                context.startActivity(intent);

            }
        });

    }
}
