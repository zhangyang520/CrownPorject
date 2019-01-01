package com.example.administrator.chengnian444.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.bean.InComeBean;

import java.util.List;

/**
 * 收益明细的adpter
 * @author Administrator
 * @Title ${name}
 * @ProjectName 444
 * @Description: TODO
 * @date 2018/12/2516:47
 */
public class IncomeRecylerviewAdapter extends RecyclerView.Adapter <IncomeRecylerviewAdapter.IncomeViewHolder>{


    private Context context;
    private List<InComeBean> incomeBeans;
    public IncomeRecylerviewAdapter(Context context, List<InComeBean> inComeBeans) {
        super();
        this.context=context;
        this.incomeBeans=inComeBeans;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IncomeViewHolder(View.inflate(context,R.layout.item_recylerview_income_balance,null));
    }

    @Override
    public int getItemCount() {
        if (incomeBeans != null) {
            return incomeBeans.size();
        }
        return 0;
    }

    public List<InComeBean> getIncomeBeans() {
        return incomeBeans;
    }

    public void setIncomeBeans(List<InComeBean> incomeBeans) {
        this.incomeBeans = incomeBeans;
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder incomeViewHolder, int i) {
        incomeViewHolder.tv_balance.setText("余额："+incomeBeans.get(i).getBalance().toString());

        if(incomeBeans.get(i).getIncome()>0){
            incomeViewHolder.tv_income.setText("+"+incomeBeans.get(i).getIncome().toString());
        }else{
            incomeViewHolder.tv_income.setText("-"+incomeBeans.get(i).getIncome().toString());
        }
        incomeViewHolder.tv_income_date.setText(incomeBeans.get(i).getDate());
        incomeViewHolder.tv_income_name.setText(incomeBeans.get(i).getName());
    }

    class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView tv_income_name;
        TextView tv_balance;
        TextView tv_income_date;
        TextView tv_income;


        public IncomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_income_name=itemView.findViewById(R.id.tv_income_name);
            tv_balance=itemView.findViewById(R.id.tv_balance);
            tv_income_date=itemView.findViewById(R.id.tv_income_date);
            tv_income=itemView.findViewById(R.id.tv_income);
        }
    }
}
