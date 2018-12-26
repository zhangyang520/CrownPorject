package allen.com.rntestproject;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.jadsonlourenco.RNShakeEvent.RNShakeEventPackage;
import com.microsoft.codepush.react.CodePush;
import com.remobile.toast.RCTToastPackage;
import com.rnandroidsdk.datastatistics.DataStatisticsPackage;
import com.rnandroidsdk.jxcodepush.JXCodepushPackage;
import com.rnandroidsdk.jxhelper.JXHelperPackage;
import com.rnandroidsdk.marqueeLabel.RCTMarqueeLabelPackage;
import com.rnandroidsdk.openapp.OpenAppPackage;
import com.umeng.analytics.MobclickAgent;
import com.zmxv.RNSound.RNSoundPackage;

import java.util.Arrays;
import java.util.List;

import allen.com.rntestproject.crash.CrashHandler;
import allen.com.rntestproject.openapp.OpenNativeModulePackage;
import cn.jpush.android.api.JPushInterface;
import fr.greweb.reactnativeviewshot.RNViewShotPackage;

public class MainApplication extends Application implements ReactApplication {

    private static MainApplication mInstance = null;

    public static MainApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        mInstance = this;
        super.onCreate();
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        CrashHandler.getInstance().init(this);
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        MobclickAgent.setCatchUncaughtExceptions(false);
//        TalkingDataAppCpa. setVerboseLogDisable();
//        TalkingDataAppCpa.init(this.getApplicationContext(), getResources().getString(R.string.talkingdata_appid), getResources().getString(R.string.channelId_today_top));
    }


    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {

        @Override
        protected String getJSBundleFile() {
            return CodePush.getJSBundleFile();
        }


        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }


        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage(),
                    new RNSoundPackage(),
                    new CodePush(getResources().getString(R.string.deploymentKey), getApplicationContext(), BuildConfig.DEBUG, getResources().getString(R.string.deploymentServer)),
                    new RNViewShotPackage(),
                    new RNShakeEventPackage(),
                    new RCTToastPackage(),
                    new OpenAppPackage(),
                    new RCTMarqueeLabelPackage(),
                    new JXHelperPackage(),
                    new DataStatisticsPackage(),
                    new JXCodepushPackage(),
                    new OpenNativeModulePackage()
            );
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }

}
