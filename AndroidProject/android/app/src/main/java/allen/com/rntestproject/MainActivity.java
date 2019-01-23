package allen.com.rntestproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.facebook.react.ReactActivity;
import com.umeng.analytics.MobclickAgent;

import allen.com.rntestproject.crash.ErrorManager;


/**
 * 入口Activity
 */
public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "TC168";
    }


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
//        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        ErrorManager.getInstance().checkAndUpdateErrorLogIfNeed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ErrorManager.getInstance().checkAndUpdateErrorLogIfNeed();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
