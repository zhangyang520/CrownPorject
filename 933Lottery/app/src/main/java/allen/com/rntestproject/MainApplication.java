package allen.com.rntestproject;

import android.app.Application;

public class MainApplication extends Application {

    private static MainApplication mInstance = null;

    public static MainApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        mInstance = this;
        super.onCreate();
    }
}
