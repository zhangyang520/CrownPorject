package com.example.administrator.chengnian444.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.fragment.DetailofPresentationFragment;
import com.example.administrator.chengnian444.fragment.DetailofIncomesFragment;
import com.example.administrator.chengnian444.utils.TabUtils;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;


/**
  *    余额明细的界面
     * @Title:
     * @ProjectName
     * @Description: TODO
     * @author zhangyang
     * @date
     */
public class DetailofBalanceActivity extends AppCompatActivity {

      //tab的集合
     private List<String> tabList=new ArrayList<String>();
     //fragment的集合
     private List<Fragment> fragmentlist=new ArrayList<Fragment>();

     @Bind(R.id.back)
     ImageView back;

    @Bind({R.id.magic_indicator})
    MagicIndicator magicIndicator;

    @Bind(R.id.vp)
    ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailof_balance);
        ButterKnife.bind(this);

        //初始化标题
        tabList.add("收益明细");
        tabList.add("提现明细");

        fragmentlist.add(new DetailofIncomesFragment());
        fragmentlist.add(new DetailofPresentationFragment());


        DetailCashPagerAdapter myPagerAdapter = new DetailCashPagerAdapter(getSupportFragmentManager(),fragmentlist,tabList);
        vp.setAdapter(myPagerAdapter);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return tabList == null ? 0 : tabList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#bebebe"));
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setWidth(getResources().getDisplayMetrics().widthPixels/2);
                colorTransitionPagerTitleView.setText(tabList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(context.getResources().getColor(R.color.yellow));
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                //设置indicator的宽度
                indicator.setLineWidth(TabUtils.Dp2Px(context,29));
                indicator.setLineHeight(TabUtils.Dp2Px(context,2));
                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator,vp);

    }


     @OnClick({R.id.back})
     public void onClick(View view) {

         switch (view.getId()) {
             case R.id.back:
                 finish();
                 break;
         }
     }


    static  class DetailCashPagerAdapter extends FragmentPagerAdapter{

        private List<Fragment> datas;
        private List<String> tabList;
        public DetailCashPagerAdapter(FragmentManager fm,List<Fragment> datas,List<String> tabList) {
            super(fm);
            this.datas=datas;
            this.tabList=tabList;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Fragment getItem(int i) {
            System.out.println("DetailCashPagerAdapter getItem i:"+i+"...datas.get(i):"+datas.get(i));
            return datas.get(i);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position);
        }
    }
}

