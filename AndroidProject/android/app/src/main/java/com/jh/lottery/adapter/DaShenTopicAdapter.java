package com.jh.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import allen.com.rntestproject.R;

import com.jh.lottery.model.DashengTopicModel;
import com.jh.lottery.model.NewsModel;
import com.jh.lottery.utils.GlideUtils;
import com.jh.lottery.utils.XDateUtils;

import java.util.List;

/**
 * Created by sangcixiang on 2018/8/2.
 */

public class DaShenTopicAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<DashengTopicModel> models;
    private Context context;
    private Boolean isHideAvatar = false;
    public DaShenTopicAdapter(Context context, List<DashengTopicModel> models,Boolean isHideAvatar){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.models = models;
        this.isHideAvatar = isHideAvatar;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder= new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_dasheng_topic_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.content = convertView.findViewById(R.id.content);
            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.timer = convertView.findViewById(R.id.timer);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        DashengTopicModel model = models.get(position);
        holder.title.setText(model.getManitoArticleTitle());
        holder.content.setText(model.getManitoArticleContent());
        holder.timer.setText(XDateUtils.millis2String(model.getManitoArticleCreateDate(),"yyyyMMdd"));
        if (isHideAvatar){
            holder.avatar.setVisibility(View.GONE);
        }else {
            holder.avatar.setVisibility(View.VISIBLE);
            GlideUtils.loadAvatar(context,model.getHeadImageUrl(),holder.avatar);
        }

        return convertView;
    }

    private final class ViewHolder{
        public TextView title;
        public ImageView avatar;
        public TextView content;
        public TextView timer;
    }
}
