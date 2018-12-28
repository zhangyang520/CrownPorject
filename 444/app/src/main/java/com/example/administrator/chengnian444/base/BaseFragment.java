package com.example.administrator.chengnian444.base;

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
import android.widget.Toast;

import com.example.administrator.chengnian444.R;
import com.example.administrator.chengnian444.utils.SPUtils;
import com.example.administrator.chengnian444.utils.StatusBarCompat.StatusBarCompat;
import com.example.administrator.chengnian444.utils.ToastUtils;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

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
    public void exitDialog(){
       /* AlertDialog.Builder alertDialog =new AlertDialog.Builder(getActivity());
        alertDialog.setMessage("当前账号在tCancelable(false);
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void其他设备登录");
        alertDialog.se onClick(DialogInterface dialog, int which) {
                SPUtils.getInstance(getActivity()).put("isLogin",false);
                SPUtils.getInstance(getActivity()).put("loginToken","");
                dialog.dismiss();
            }
        });
        AlertDialog dialog = alertDialog.create();
        dialog.show();*/
        SPUtils.getInstance(getActivity()).put("isLogin",false);
        SPUtils.getInstance(getActivity()).put("loginToken","");
        ToastUtils.showToast(getActivity(),"当前账号在其他设备登录");
    }
}
