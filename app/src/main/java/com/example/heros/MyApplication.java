package com.example.heros;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.heros.service.MyService2;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private int appCount = 0;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.e("hyw", "attachBaseContext");
        Intent intent = new Intent(this,MyService2.class);
        startService(intent);

    }

    public void registerLifecycle() {
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("hyw", "hero MyApplication onCreate");
        initLeakCanary();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            Log.e("hyw","isInAnalyzerProcess");
            return;
        }
        Log.e("hyw"," LeakCanary.install");
        LeakCanary.install(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.e("hyw", "onActivityCreated:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("hyw", "onActivityStarted:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.e("hyw", "onActivityResumed:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.e("hyw", "onActivityPaused:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.e("hyw", "onActivityStopped:" + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.e("hyw", "onActivitySaveInstanceState:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.e("hyw", "onActivityDestroyed:" + activity.getLocalClassName());
    }


    public int getAppCount() {
        return appCount;
    }

    public void setAppCount(int appCount) {
        this.appCount = appCount;
    }
}
