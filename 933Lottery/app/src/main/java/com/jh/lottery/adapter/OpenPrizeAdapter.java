package com.jh.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jh.lottery.model.LotteryModel;
import com.jh.lottery.utils.XDateUtils;

import java.util.Date;
import java.util.List;

import allen.com.rntestproject.R;

import static android.view.Gravity.CENTER;


public class OpenPrizeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<LotteryModel> models;
    private Context context;
    private String type = "";
    public OpenPrizeAdapter(Context context, List<LotteryModel> models, String type){
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.models = models;
        this.type = type;
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
            convertView = mInflater.inflate(R.layout.layout_open_prize_item, null);
            holder.name = convertView.findViewById(R.id.name);
            holder.time1 = convertView.findViewById(R.id.time1);
            holder.time2 = convertView.findViewById(R.id.time2);
            holder.numbersView = convertView.findViewById(R.id.numbersView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        LotteryModel model = models.get(position);

        holder.name.setText(model.getName());
        holder.time1.setText("期号："+model.getLotteryPeriods());
        Date date = XDateUtils.string2Date(model.getLotteryDate(),"yyyy-MM-dd HH:mm:ss");
        holder.time2.setText(XDateUtils.date2String(date,"yyyy-MM-dd"));

        if (!type.equals("all")){
            holder.name.setTextSize(17);
            holder.name.setText("期号："+model.getLotteryPeriods());
            holder.time1.setVisibility(View.GONE);
            holder.time2.setVisibility(View.GONE);
        }

        holder.numbersView.removeAllViews();
        String[] numbers = model.getLotteryOpenNumber().split(",");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);

        layoutParams.setMargins(0,0,10,0);
        for (int i=0;i<numbers.length;i++){
            String num = numbers[i];
            TextView n = new TextView(context);
            n.setGravity(CENTER);

            n.setTextColor(context.getResources().getColor(R.color.colorWhite));
            n.setText(num);
            n.setLayoutParams(layoutParams);
            if (model.getName().equals("双色球")){
                if (i == numbers.length - 1){
                    n.setBackground(context.getResources().getDrawable(R.drawable.lettory_blue_round));
                }else {
                    n.setBackground(context.getResources().getDrawable(R.drawable.lettory_red_round));
                }
            }else if (model.getName().equals("大乐透")){
                if (i < numbers.length - 2){
                    n.setBackground(context.getResources().getDrawable(R.drawable.lettory_red_round));
                }else {
                    n.setBackground(context.getResources().getDrawable(R.drawable.lettory_blue_round));
                }
            }
            else {
                n.setBackground(context.getResources().getDrawable(R.drawable.lettory_red_round));
            }
            holder.numbersView.addView(n);
        }
        return convertView;
    }

    private final class ViewHolder{
        public TextView name;
        public TextView time1;
        public TextView time2;
        public LinearLayout numbersView;
    }
}
