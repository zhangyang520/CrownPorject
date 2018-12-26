package com.rnandroidsdk.jxcodepush;

import android.content.Context;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rnandroidsdk.util.SPUtils;

/**
 * author: allen-jx
 * created on: 2018/5/24 9:54
 * description:
 */
public class JXCodepush extends ReactContextBaseJavaModule {

    Context context;

    public JXCodepush(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return "JXCodepush";
    }

    @ReactMethod
    public void resetLoadModleForJS(boolean flag) {
        SPUtils.put(context, SPUtils.UPADTE_FLAG, flag);
    }
}
