package com.jh.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jh.lottery.model.GiftModel;

import java.util.List;

import allen.com.rntestproject.R;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ItemViewHolder> implements View.OnClickListener {

    private OnItemClickListener mOnItemClickListener = null;
    private List<GiftModel> models;
    private Context context;
    public ShoppingAdapter(Context context, List<GiftModel> models){
        this.models = models;
        this.context = context;
    }
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_shopping_item, viewGroup, false);
        view.setOnClickListener(this);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return this.models.size();
    }



    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {

        GiftModel model = models.get(position);
        holder.name.setText(model.getGiftName());
        Glide.with(context).load(model.getGiftImgurl()).into(holder.imageView);
        holder.score.setText(model.getGiftIntegral()+"积分");
        holder.itemView.setTag(position);
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
        void onItemClick(GiftModel model);
    }

    public class ItemViewHolder extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView name;
        TextView score;
        public ItemViewHolder(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.imageView);
            name= (TextView) itemView.findViewById(R.id.name);
            score = (TextView)itemView.findViewById(R.id.score);
        }
    }
}
