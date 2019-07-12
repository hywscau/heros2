package com.example.heros.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(Intent.ACTION_PACKAGE_ADDED)){
            Log.e("hyw","app installed:"+intent.getDataString());
        }else if(action.equals(Intent.ACTION_PACKAGE_REMOVED)){
            Log.e("hyw","app uninstalled:"+intent.getDataString());
        }
    }
}
