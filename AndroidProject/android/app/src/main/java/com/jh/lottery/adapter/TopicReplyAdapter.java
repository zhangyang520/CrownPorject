package com.jh.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import allen.com.rntestproject.R;

import com.jh.lottery.model.TopicReplyModel;
import com.jh.lottery.utils.GlideUtils;
import com.jh.lottery.utils.XDateUtils;

import java.util.List;

/**
 * Created by sangcixiang on 2018/8/4.
 */

public class TopicReplyAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<TopicReplyModel> models;

    public TopicReplyAdapter(Context context, List<TopicReplyModel> models){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.models = models;
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
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (null == convertView){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_topic_reply_item,null);
            holder.content = convertView.findViewById(R.id.content);
            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.name = convertView.findViewById(R.id.name);
            holder.timer = convertView.findViewById(R.id.timer);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        TopicReplyModel model = models.get(position);
        if (null != model.getMemberHeadImagePath() && model.getMemberHeadImagePath().length() > 10){
            GlideUtils.loadAvatar(context,model.getMemberHeadImagePath(),holder.avatar);
        }
        holder.name.setText(model.getNickName());
        holder.timer.setText(XDateUtils.millis2String(model.getCreateTime(),"yyyy-MM-dd"));
        holder.content.setText(model.getContent());


        return convertView;
    }

    public class ViewHolder{

        public TextView content;
        public TextView timer;
        public TextView name;
        public ImageView avatar;
    }

}
