package com.example.administrator.chengnian933;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

import com.example.administrator.chengnian933.base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.administrator.chengnian933.fragment.HomeFragment;
import com.example.administrator.chengnian933.fragment.LoanFragment;
import com.example.administrator.chengnian933.fragment.MineFragment;
import com.example.administrator.chengnian933.fragment.MoveFragment;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @Bind(R.id.main_bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationbar;
    private ArrayList<Fragment> mFragmentList;
    private ArrayList<Fragment> hasAddedFragment;
    private FragmentTransaction mTransaction;
    private Fragment mCurrFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
    }


    private void initFragment() {

        mBottomNavigationbar
                .addItem(new BottomNavigationItem(R.mipmap.home2, "首页").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.home1)))
                .addItem(new BottomNavigationItem(R.mipmap.player2, "影库").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.player)))
                .addItem(new BottomNavigationItem(R.mipmap.move2, "精品").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.move1)))
                .addItem(new BottomNavigationItem(R.mipmap.me2, "我的").setInactiveIcon(ContextCompat.getDrawable(this, R.mipmap.me1)))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationbar.setTabSelectedListener(this);

        mFragmentList = new ArrayList<>();
        hasAddedFragment = new ArrayList<>();
        mFragmentList.add(new HomeFragment());
        mFragmentList.add(new LoanFragment());
        mFragmentList.add(new MoveFragment());
        mFragmentList.add(new MineFragment());


        //默认fragment
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.add(R.id.main_fragment_container,  mFragmentList.get(0));
        mTransaction.commit();
        mCurrFragment =  mFragmentList.get(0);
        hasAddedFragment.add(mFragmentList.get(0));
    }

    @Override
    public void onTabSelected(int position) {
        addShowHideFragment( mFragmentList.get(position));
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void addShowHideFragment(Fragment fragment) {
        FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
        if (mCurrFragment != null) {
            mTransaction.hide(mCurrFragment);
        }
        if (hasAddedFragment.contains(fragment)) {
            mTransaction.show(fragment);
        } else {
            mTransaction.add(R.id.main_fragment_container, fragment);
            hasAddedFragment.add(fragment);
        }
        mTransaction.commit();
        mCurrFragment = fragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}