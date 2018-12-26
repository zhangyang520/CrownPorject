package com.example.administrator.chengnian444.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.activity.MediaActivity;
import com.example.administrator.chengnian444.bean.MoveListBean;

import java.util.List;

public class MoveAdapter extends BaseQuickAdapter<MoveListBean.DataBean.DataListBean,BaseViewHolder> {
    private Context context;
    public MoveAdapter(int layoutResId, @Nullable List<MoveListBean.DataBean.DataListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    public void convert(BaseViewHolder helper, final MoveListBean.DataBean.DataListBean item) {
        ImageView view = helper.getView(R.id.iv_it);
        Glide.with(context).load(item.getBigimage()).placeholder(R.mipmap.zhanwei1).centerCrop().into(view);
        TextView view1 = helper.getView(R.id.tv);
        helper.setText(R.id.tv_time,item.getMoveTime());
        view1.setText(item.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MediaActivity.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("moveUrl",item.getUrl());
                intent.putExtra("image",item.getBigimage());
                intent.putExtra("typenum",item.getTypenum()+"");
                intent.putExtra("duration",item.getDuration());
                intent.putExtra("name",item.getName());
                context.startActivity(intent);
            }
        });
    }
}


