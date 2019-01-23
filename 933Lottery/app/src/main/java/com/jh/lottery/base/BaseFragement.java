package com.jh.lottery.base;

import android.content.Intent;
import android.view.View;

/**
 * function
 * Created by mhdt on 2016/12/3.12:38
 * update by:
 */
public abstract class BaseFragement extends XFragment {
//    protected View mView;
    public Boolean isResume = false;
    protected <T extends View> T find(int resId) {
        return (T) getActivity().findViewById(resId);
    }

    public void resume(){

    }


    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
    }

    public void JumpToActivity(Class<?> cls) {
        JumpToActivity(cls, null);
    }

    public void JumpToActivity(Class<?> cls, Intent data) {
        Intent intent = new Intent(getActivity(), cls);
        if (data != null)
            intent.putExtras(data);
        startActivity(intent);
    }

    public void JumpToActivityForResult(Class<?> cls, int requestCode) {
        JumpToActivityForResult(cls, null, requestCode);
    }

    public void JumpToActivityForResult(Class<?> cls, Intent data, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        if (data != null)
            intent.putExtras(data);
        startActivityForResult(intent, requestCode);
        setOverridePendingTransition();
    }



    public void setOverridePendingTransition() {
        getActivity().overridePendingTransition(0, 0);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
