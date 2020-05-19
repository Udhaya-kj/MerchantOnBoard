package life.corals.onboarding.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.util.List;

import life.corals.onboarding.Activity.SplashActivity;
import life.corals.onboarding.Constants.Constants;


public class AppTimeOutManagerUtil {

    private Context mCtx;

    private static final String TAG = "AppTimeOutManagerUtil";

    private static final Handler HANDLER = new Handler();
    private Runnable runnable;
    private boolean isAppTimedOut = false;
    private boolean isAppMinimized = false;

    public AppTimeOutManagerUtil(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void onResumeApp() {
        try {
            Log.d(TAG, "onResumeApp: isAppTimedOut" + isAppTimedOut);
            Log.d(TAG, "onResumeApp: isAppMinimized" + isAppMinimized);
            if (isAppTimedOut && isAppMinimized) {
                timeOutIntent();
            } else if (!isAppTimedOut && isAppMinimized) {

                if (runnable != null) {
                    HANDLER.removeCallbacks(runnable);
                    Log.d(TAG, "onResumeApp: removeCallbacks");
                }
                isAppMinimized = false;
                isAppTimedOut = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            timeOutIntent();
        }
    }

    public void onPauseApp() {
        try {
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (!isAppOnForeground()) {
                        isAppTimedOut = true;
                        Log.d(TAG, "run: isAppTimedOut" + isAppTimedOut);
                    }
                }
            };
            Log.d(TAG, "onPauseApp");
            Log.d(TAG, "onPauseApp:isAppOnForeground "+isAppOnForeground());
            if (!isAppTimedOut && !isAppOnForeground()) {
                isAppMinimized = true;
                Log.d(TAG, "onPauseApp: handler called");
                HANDLER.postDelayed(runnable, Constants.APP_TIME_OUT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            timeOutIntent();
        }

    }

    private boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) mCtx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = mCtx.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    private void timeOutIntent() {
        mCtx.startActivity(new Intent(mCtx, SplashActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
