package com.example.administrator.chengnian933.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.example.administrator.chengnian933.R;
import com.example.administrator.chengnian933.utils.StatusBarCompat.StatusBarCompat;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import com.zyao89.view.zloading.circle.SingleCircleBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
    public Activity mActivity;
    View rootView;
    @Bind(R.id.fragment_base_child_container)
    FrameLayout childContainer;
    private ZLoadingDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = new ZLoadingDialog(getActivity());
        dialog.setLoadingBuilder(Z_TYPE.SINGLE_CIRCLE)//设置类型
                .setLoadingColor(Color.GRAY)//颜色
                .setHintText("Loading...")
                .setHintTextSize(16) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setDialogBackgroundColor(Color.parseColor("#CC111111")); // 设置背景色，默认白色

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
            childContainer = (FrameLayout) rootView.findViewById(R.id.fragment_base_child_container);
            int childLayout = getContentLayoutRes();
            View childView = inflater.inflate(childLayout, childContainer);
            ButterKnife.bind(this, childView);
            initView(childView);
            StatusBarCompat.setStatusBarColor(getActivity(),getResources().getColor(R.color.black));
        }
        return rootView;
    }


    protected abstract int getContentLayoutRes();

    protected abstract void initView(View childView);

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
        public void dialogShow(){
        dialog.show();
        }
        public void dialogDismiss(){
        dialog.dismiss();
        }

}
