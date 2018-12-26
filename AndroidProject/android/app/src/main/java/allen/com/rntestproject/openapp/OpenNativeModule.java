package allen.com.rntestproject.openapp;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import allen.com.rntestproject.MainActivity;
import allen.com.rntestproject.main.AppMainActivity;

/**
 * author: allen-jx
 * created on: 2018/5/24 15:06
 * description:
 */
public class OpenNativeModule extends ReactContextBaseJavaModule {
    public OpenNativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "OpenNative";
    }

    @ReactMethod
    public void startActivity() {
        try {
            Activity currentActivity = getCurrentActivity();
            Intent intent = new Intent(currentActivity, AppMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
            currentActivity.startActivity(intent);
        } catch (Exception e) {
            throw new JSApplicationIllegalArgumentException(
                    "不能打开Activity : " + e.getMessage());
        }
    }
}
