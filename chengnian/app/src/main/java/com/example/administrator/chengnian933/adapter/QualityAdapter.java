package com.example.administrator.chengnian933.adapter;

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

import com.example.administrator.chengnian933.activity.MoveListActivity;
import com.example.administrator.chengnian933.activity.MoveSeriesActivity;
import com.example.administrator.chengnian933.bean.MoveQuality;
import com.example.administrator.chengnian933.http.Constant;

public class QualityAdapter extends BaseQuickAdapter<MoveQuality.DataBean.DataListBean,BaseViewHolder> {
    private Context context;
    public QualityAdapter(int layoutResId, @Nullable List<MoveQuality.DataBean.DataListBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MoveQuality.DataBean.DataListBean item) {
        ImageView view = helper.getView(R.id.iv_quality);
        helper.setText(R.id.title,item.getSeriesName());
        Glide.with(context).load(item.getImg()).placeholder(R.mipmap.photo_zhanwei2).centerCrop().into(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MoveSeriesActivity.class);
                intent.putExtra("title",item.getSeriesName());
                intent.putExtra("seriesCode",item.getSeriesCode()+"");
                context.startActivity(intent);
            }
        });
    }
}
