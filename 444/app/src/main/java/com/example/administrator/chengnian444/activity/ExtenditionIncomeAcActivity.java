package com.example.administrator.chengnian444.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.base.BaseActivity;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;


/**
 *    推广收益的核算
     * @Title:
     * @ProjectName
     * @Description: TODO
     * @author zhangyang
     * @date
     */
public class ExtenditionIncomeAcActivity extends BaseActivity {

    @Bind(R.id.back)
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extendition_income_ac);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.black));
    }

    @OnClick({R.id.back})
    public void onClickView(View view) {

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
