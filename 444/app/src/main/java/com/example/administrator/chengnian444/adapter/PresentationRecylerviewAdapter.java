package com.example.administrator.chengnian444.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.bean.PresentationBalanceBean;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 收益明细的 adpter
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date   2018/12/25 16:47
 */
public class PresentationRecylerviewAdapter extends RecyclerView.Adapter <PresentationRecylerviewAdapter.IncomeViewHolder>{


    private Context context;
    private List<PresentationBalanceBean> incomeBeans;
    private DecimalFormat decimalFormat=new DecimalFormat("0.00");
    public PresentationRecylerviewAdapter(Context context, List<PresentationBalanceBean> inComeBeans) {
        super();
        this.context=context;
        this.incomeBeans=inComeBeans;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IncomeViewHolder(View.inflate(context,R.layout.item_recylerview_presentation_details,null));
    }

    @Override
    public int getItemCount() {
        if (incomeBeans != null) {
            return incomeBeans.size();
        }
        return 0;
    }

    public List<PresentationBalanceBean> getIncomeBeans() {
        return incomeBeans;
    }

    public void setIncomeBeans(List<PresentationBalanceBean> incomeBeans) {
        this.incomeBeans = incomeBeans;
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder incomeViewHolder, int i) {
        incomeViewHolder.tv_balance.setText("余额："+decimalFormat.format(incomeBeans.get(i).getBalance()));
        if(incomeBeans.get(i).getIncome()>0){
            incomeViewHolder.tv_income.setText("-"+decimalFormat.format(incomeBeans.get(i).getIncome()));
        }else{
            incomeViewHolder.tv_income.setText("-"+decimalFormat.format(incomeBeans.get(i).getIncome()));
        }
        incomeViewHolder.tv_income_date.setText(incomeBeans.get(i).getDate());
        incomeViewHolder.tv_income_name.setText(incomeBeans.get(i).getName());

        if(incomeBeans.get(i).getStatus()==0){
             //成功
            Glide.with(context).load(R.mipmap.iv_draw_success).into(incomeViewHolder.iv_status);
        }else if(incomeBeans.get(i).getStatus()==1){
            //1失败
            Glide.with(context).load(R.mipmap.iv_draw_failure).into(incomeViewHolder.iv_status);
        }else{
            //拒绝
            Glide.with(context).load(R.mipmap.iv_draw_reject).into(incomeViewHolder.iv_status);
        }
    }

    class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_income_name;
        TextView tv_balance;
        TextView tv_income_date;
        TextView tv_income;
        ImageView iv_status;

        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_income_name=itemView.findViewById(R.id.tv_income_name);
            tv_balance=itemView.findViewById(R.id.tv_balance);
            tv_income_date=itemView.findViewById(R.id.tv_income_date);
            tv_income=itemView.findViewById(R.id.tv_income);
            iv_status=itemView.findViewById(R.id.iv_status);
        }
    }
}
