package com.example.administrator.chengnian444.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.activity.MediaActivity;
import com.example.administrator.chengnian444.bean.HomeChangeBean;

import java.util.List;

public class HomeChangerAdapter extends BaseQuickAdapter<HomeChangeBean.DataBean,BaseViewHolder> {
    private Context context;
    private Boolean flag;
    public HomeChangerAdapter(int layoutResId, @Nullable List<HomeChangeBean.DataBean> data, Context context, Boolean flag) {
        super(layoutResId, data);
        this.context = context;
        this.flag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeChangeBean.DataBean item) {
        ImageView view = helper.getView(R.id.iv_it);
        String bigimage = item.getBigimage();
        Glide.with(context).load(bigimage).placeholder(R.mipmap.zhanwei1).centerCrop().into(view);
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
                if (flag){
                    Activity activity = (Activity) context;
                    activity.finish();
                }

            }
        });
    }
}
