package com.jh.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jh.lottery.model.TopicModel;
import com.jh.lottery.utils.XDateUtils;

import java.util.List;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/8/4.
 */

public class CommunityTopicAdapter extends BaseAdapter {

    private Context context;
    private List<TopicModel> models;
    private LayoutInflater inflater;
    public CommunityTopicAdapter(Context context, List<TopicModel> models){
        this.context = context;
        this.models = models;
        this.inflater = LayoutInflater.from(context);
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
        ViewHolder view = null;
        if (null == convertView){
            view = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_topic_item,null);
            view.content = convertView.findViewById(R.id.content);
            view.name = convertView.findViewById(R.id.name);
            view.title = convertView.findViewById(R.id.title);
            view.timer = convertView.findViewById(R.id.timer);
            convertView.setTag(view);
        }else {
            view = (ViewHolder) convertView.getTag();
        }

        TopicModel model = models.get(position);
        view.title.setText(model.getTitle());
        view.content.setText(model.getContent());
        view.timer.setText(XDateUtils.millis2String(model.getCreateTime(),"yyyy-MM-dd HH:mm"));
        view.name.setText(model.getAdminName());
        return convertView;
    }

    public class ViewHolder{

        public TextView title;
        public TextView content;
        public TextView timer;
        public TextView name;


    }
}
