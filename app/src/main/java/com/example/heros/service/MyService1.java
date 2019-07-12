package com.example.heros.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService1 extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("hyw","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("hyw","onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }


    private IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        public MyService1 getService() {
            return MyService1.this;
        }

        public int getInt() {
            return 3;
        }
    }

    public int getNumber() {
        return 3;
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e("hyw", "MyService1 onBind");
        return mBinder;  //返回null则不会掉onServiceConnected
    }

    @Override
    public void onDestroy() {
        Log.e("hyw","onStartCommand");
        super.onDestroy();
    }

}
