package com.jh.lottery.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import allen.com.rntestproject.R;
import com.jh.lottery.model.MessageModel;
import com.jh.lottery.model.NewsModel;
import com.jh.lottery.utils.GlideUtils;

import java.util.List;

/**
 * Created by sangcixiang on 2018/8/6.
 */

public class MessageAdapter extends BaseAdapter {

    private List<MessageModel> models;
    private LayoutInflater inflater;
    private Context context;

    public MessageAdapter(Context context,List<MessageModel> models){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder= new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_news_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.content = convertView.findViewById(R.id.content);
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        MessageModel model = models.get(position);
        if (null != model.getNewsImgurl() && model.getNewsImgurl().length() > 0){
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.imageView.setVisibility(View.GONE);
        }
        GlideUtils.loadAvatar(context,model.getNewsImgurl(),holder.imageView);
        if (null != model.getTitle()){
            holder.title.setText(model.getTitle());
        }
        if (null != model.getContent()){
            holder.content.setText(model.getContent());
        }
        return convertView;
    }

    class ViewHolder{
        TextView title;
        TextView content;
        ImageView imageView;
    }

}
