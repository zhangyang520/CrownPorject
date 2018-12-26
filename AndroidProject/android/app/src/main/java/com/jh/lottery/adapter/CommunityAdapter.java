package com.jh.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import allen.com.rntestproject.R;
import com.jh.lottery.model.BlockModel;

import java.util.List;

/**
 * Created by sangcixiang on 2018/8/4.
 */

public class CommunityAdapter extends BaseAdapter {

    private List<Object> models;
    private Context context;
    private LayoutInflater mInflater;
    public CommunityAdapter(Context context,List<Object> models){
        this.context = context;
        this.models = models;
        this.mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public boolean isEnabled(int position) {
        if (!models.get(position).getClass().equals(BlockModel.class)){
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder headerView = null;
        ViewHolder viewHolder = null;
        if (null == convertView){
            if (!models.get(position).getClass().equals(BlockModel.class)){
                headerView = new HeaderViewHolder();
                convertView = mInflater.inflate(R.layout.layout_community_header_item,null);
                headerView.title = convertView.findViewById(R.id.title);
                convertView.setTag(headerView);
            }else {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.layout_community_item,null);
                viewHolder.title = convertView.findViewById(R.id.title);
                viewHolder.content = convertView.findViewById(R.id.content);
                convertView.setTag(viewHolder);
            }
        }else {
            if (!models.get(position).getClass().equals(BlockModel.class)){
                headerView = (HeaderViewHolder) convertView.getTag();
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
        }

        if (!models.get(position).getClass().equals(BlockModel.class)){

            headerView.title.setText((String ) models.get(position));

        }else {
            BlockModel model = (BlockModel) models.get(position);
            viewHolder.title.setText(model.getPlateName());
            viewHolder.content.setText(model.getPlateDescription());
        }

        return convertView;

    }

    private class HeaderViewHolder{
        public TextView title;
    }

    private  class ViewHolder{
        public TextView title;
        public TextView content;
    }

}
