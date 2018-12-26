package com.example.administrator.chengnian444.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.chengnian444.R;


/**
 *    推广收益的核算
     * @Title:
     * @ProjectName
     * @Description: TODO
     * @author zhangyang
     * @date
     */
public class ExtenditionIncomeAcActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extendition_income_ac);
        ButterKnife.bind(this);
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
