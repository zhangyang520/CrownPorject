package com.jh.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jh.lottery.model.DaShengModel;
import com.jh.lottery.utils.GlideUtils;

import java.util.List;

import allen.com.rntestproject.R;

/**
 * Created by sangcixiang on 2018/8/3.
 */

public class DaShengHaderViewAdapter extends RecyclerView.Adapter<DaShengHaderViewAdapter.ItemViewHolder> implements View.OnClickListener {

    private Context context;
    private List<DaShengModel> models;
    private OnItemClickListener mOnItemClickListener = null;
    public DaShengHaderViewAdapter(Context context, List<DaShengModel> models){
        this.context = context;
        this.models = models;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ds_header_item, parent, false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        DaShengModel model = models.get(position);
        holder.name.setText(model.getGodName());
        GlideUtils.loadAvatar(context,model.getHeadImageUrl(),holder.avatar);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 25);

        layoutParams.setMargins(0,0,5,0);
        if (model.isDashen()){
            holder.starts.setVisibility(View.VISIBLE);
            for(int i=0;i<model.getGodStar();i++){
                ImageView start = new ImageView(context);
                start.setImageDrawable(context.getResources().getDrawable(R.mipmap.star_big3));
                start.setLayoutParams(layoutParams);
                holder.starts.addView(start);
            }
            for (int i=model.getGodStar();i<5;i++){
                ImageView start = new ImageView(context);
                start.setImageDrawable(context.getResources().getDrawable(R.mipmap.star_big1));
                start.setLayoutParams(layoutParams);
                holder.starts.addView(start);
            }
        }else {
            holder.starts.setVisibility(View.GONE);
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
    @Override
    public void onClick(View v) {

        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(models.get((int) v.getTag()));
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(DaShengModel model);
    }

    public class ItemViewHolder extends  RecyclerView.ViewHolder{

        ImageView avatar;
        TextView name;
        LinearLayout starts;
        public ItemViewHolder(View itemView){
            super(itemView);
            avatar= (ImageView) itemView.findViewById(R.id.avatar);
            name= (TextView) itemView.findViewById(R.id.name);
            starts = (LinearLayout)itemView.findViewById(R.id.starts);
        }
    }
}
