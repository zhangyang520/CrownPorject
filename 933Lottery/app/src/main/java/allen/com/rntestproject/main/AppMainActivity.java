package allen.com.rntestproject.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.jh.lottery.adapter.ViewPagerAdapter;
import com.jh.lottery.base.BaseActivity;
import com.jh.lottery.fragment.HomeFragment;
import com.jh.lottery.fragment.NewsFragment;
import com.jh.lottery.fragment.RecommendFragment;
import com.jh.lottery.fragment.VipFragment;
import com.jh.lottery.view.CustomViewPager;

import java.lang.reflect.Field;

import allen.com.rntestproject.R;
//
//import allen.com.rntestproject.R;
//

public class AppMainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;

    private CustomViewPager viewPager;
    private ViewPagerAdapter adapter;
    private VipFragment vipFragment;
    private RecommendFragment recommendFragment;
    private NewsFragment newsFragment;
    private HomeFragment homeFragment;

    ProgressBar progressBar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

        @Override
    public void initView() {

        setTitle(getResources().getString(R.string.app_name));
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(bottomNavigationView);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        adapter.addFragment(homeFragment);

        newsFragment = new NewsFragment();
        adapter.addFragment(newsFragment);

        recommendFragment = new RecommendFragment();
        adapter.addFragment(recommendFragment);

        vipFragment = new VipFragment();
        adapter.addFragment(vipFragment);

        viewPager = findViewById(R.id.main_viewpager);
        viewPager.setScanScroll(false);
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            isExit.setTitle("系统提示");
            isExit.setMessage("确定要退出吗");
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            isExit.show();

        }
        return false;
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    finish();
                    System.exit(0);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
                default:
                    break;
            }
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.tabBar_home:
                    setTitle(getResources().getString(R.string.app_name));
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.tabBar_news:
                    setTitle("新闻资讯");
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.tabbar_recommend:
                    setTitle("大神推荐");
                    viewPager.setCurrentItem(2);
                    recommendFragment.resume();
                    return true;
                case R.id.tabbar_vip:
                    setTitle("个人中心");
                    viewPager.setCurrentItem(3);
                    vipFragment.resume();
                    return true;
            }
            return false;
        }
    };


    @SuppressLint("RestrictedApi")
    public void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

}




