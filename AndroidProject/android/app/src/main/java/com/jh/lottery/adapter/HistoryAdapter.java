package com.jh.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import allen.com.rntestproject.R;

import com.jh.lottery.model.HistoryModel;
import com.jh.lottery.utils.XDateUtils;

import java.util.List;

/**
 * Created by sangcixiang on 2018/8/6.
 */

public class HistoryAdapter extends BaseAdapter {

    private List<HistoryModel> models;
    private LayoutInflater inflater;
    public HistoryAdapter(Context context,List<HistoryModel> models){
        this.models = models;
        inflater = LayoutInflater.from(context);
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
        ViewHolder viewHolder = null;
        if (null == convertView){
            convertView = inflater.inflate(R.layout.layout_history_item,null);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.timer = convertView.findViewById(R.id.timer);
            viewHolder.score = convertView.findViewById(R.id.score);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HistoryModel model = models.get(position);
        viewHolder.title.setText(model.getCodeCn());
        viewHolder.timer.setText(XDateUtils.millis2String(model.getCreateDate(),"yyyy-MM-dd HH:mm"));
        viewHolder.score.setText(model.getConsumptionScore());
        return convertView;
    }

    private class ViewHolder{
        public TextView title;
        public TextView timer;
        public TextView score;

    }
}
