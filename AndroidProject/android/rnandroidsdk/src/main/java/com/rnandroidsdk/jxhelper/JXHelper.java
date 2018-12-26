package com.rnandroidsdk.jxhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.rnandroidsdk.R;
import com.rnandroidsdk.util.RootUtil;
import com.rnandroidsdk.util.SPUtils;
import com.rnandroidsdk.util.UpdateManager;
import com.rnandroidsdk.webview.JXGameWebView;
import com.rnandroidsdk.webview.JXWebView;

import java.util.UUID;

/**
 * Created by Allen on 2017/2/20.
 */

public class JXHelper extends ReactContextBaseJavaModule {
    Context context;

    public JXHelper(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return "JXHelper";
    }

    @ReactMethod
    public void getCFUUID(Callback resultCallback) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String szImei = TelephonyMgr.getDeviceId();
        String res = null;
        try {
            res = UUID.nameUUIDFromBytes(szImei.getBytes("utf8")).toString();
        } catch (Exception e) {
        }
        if (res == null) {
            res = UUID.randomUUID().toString();
        }

        resultCallback.invoke("deviceId", res);
    }

    @ReactMethod
    public void getVersionCode(Callback resultCallback) {
        String versionName = getPackageInfo(context).versionName;
        if (versionName != null && versionName != "") {
            resultCallback.invoke(versionName);
        }
    }


    @ReactMethod
    public void getApplicationId(Callback callback) {
        try {
            String applicationId = getReactApplicationContext().getPackageName();
            callback.invoke(applicationId);
        } catch (Exception e) {

        }
    }

    @ReactMethod
    public void getAffCode(Callback resultCallback) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Object obj = applicationInfo.metaData.get("TD_CHANNEL_AFFCODE");

            if (obj != null && !TextUtils.isEmpty(obj.toString())) {
                resultCallback.invoke(obj.toString());
            }

        } catch (Exception e) {

        }

    }

    @ReactMethod
    public void isAndroidRootDevice(Callback resultCallback) {
        try {
            boolean result = RootUtil.isDeviceRooted();
            resultCallback.invoke(result);
        } catch (Exception e) {

        }
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    @ReactMethod
    public void updateApp(String url) {
        UpdateManager updateManager = new UpdateManager(getCurrentActivity());
        updateManager.setUrl(url);
        updateManager.update();
    }

    @ReactMethod
    public void openWebViewFromJs(String url) {
        try {
            Activity currentActivity = getCurrentActivity();
            Intent intent = new Intent(currentActivity, JXWebView.class);
            intent.putExtra("url", url);
            currentActivity.startActivity(intent);
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "不能打开Activity : " + e.getMessage());
        }
    }

    @ReactMethod
    public void openGamePageFromJs(String url, String title, String platform) {
        Log.d("JXHelper", "openGamePageFromJs() platform="+platform);
        try {
            Activity currentActivity = getCurrentActivity();
            Intent intent = new Intent(currentActivity, JXGameWebView.class);
            intent.putExtra("url", url);
            intent.putExtra("title", title);
            intent.putExtra("platform", platform);
            currentActivity.startActivity(intent);
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "不能打开Activity : " + e.getMessage());
        }
    }

    @ReactMethod
    public void getCanShowIntelligenceBet(Callback resultCallback) {
        try {
            resultCallback.invoke(true);
        } catch (Exception e) {

        }
    }

    @ReactMethod
    public void getCitys(Callback callback) {
        try {
            String citys = context.getResources().getString(R.string.shield_city);
            callback.invoke(citys);
        } catch (Exception e) {

        }
    }

    @ReactMethod
    public void saveServerUrlAndKey(String url, String key) {
        SPUtils.put(context, SPUtils.SERVER_URL, url);
        SPUtils.put(context, SPUtils.SERVER_KEY, key);
        Log.d("==============URL", url);
        Log.d("==============URL", key);
    }

}
