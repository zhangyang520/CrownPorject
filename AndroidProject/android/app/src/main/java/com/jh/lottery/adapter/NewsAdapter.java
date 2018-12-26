package com.jh.lottery.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import allen.com.rntestproject.R;
import com.jh.lottery.model.NewsModel;
import com.jh.lottery.utils.XDateUtils;

import java.util.List;

/**
 * Created by sangcixiang on 2018/7/27.
 */

public class NewsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<NewsModel> models;
    private Context context;
    public NewsAdapter(Context context, List<NewsModel> models){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.layout_news_item, null);
            holder.title = convertView.findViewById(R.id.title);
            holder.content = convertView.findViewById(R.id.content);
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.time = convertView.findViewById(R.id.time);
            holder.author = convertView.findViewById(R.id.author);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        NewsModel model = models.get(position);
        if (null != model.getNewsTitle()){
            holder.title.setText(model.getNewsTitle());
        }
        if (null != model.getNewsContent()){
            holder.content.setText(model.getNewsContent());
        }
        if (null != model.getNewsImgurl() && !model.getNewsImgurl().isEmpty()){
            Glide.with(context).load(model.getNewsImgurl()).into(holder.imageView);
            holder.imageView.setVisibility(View.VISIBLE);
        }else {
            holder.imageView.setVisibility(View.GONE);
        }
        holder.author.setVisibility(View.GONE);

        if (model.getNewsCreateDate() > 0){
            holder.time.setText(XDateUtils.millis2String(model.getNewsCreateDate(),"yyyy-MM-dd HH:mm:ss"));
            holder.time.setVisibility(View.VISIBLE);
        }else {
            holder.author.setVisibility(View.GONE);
        }

        return convertView;
    }

    private final class ViewHolder{
        public TextView title;
        public ImageView imageView;
        public TextView content;
        public TextView time;
        public TextView author;
    }

}
