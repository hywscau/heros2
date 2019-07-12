package com.example.heros.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.hyw.aidlserver.IMyAidlInterface;

public class MyService2 extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("hyw","MyService2 onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("hyw","MyService2 onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }


    private IBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        public MyService2 getService() {
            return MyService2.this;
        }

        public int getInt() {
            return 4;
        }
    }

    public int getNumber() {
        return 3;
    }



//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        Log.e("hyw", "MyService1 onBind");
//        return mBinder;  //返回null则不会掉onServiceConnected
//    }

    @Override
    public void onDestroy() {
        Log.e("hyw","MyService2 onDestroy");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //将接口暴露给客户端
        return iBinder;
    }

    private IBinder iBinder = new IMyAidlInterface.Stub(){
        //实现aidl中定义的方法
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.e("hyw","num1 + num2:"+num1 + num2);
            return num1 + num2;
        }
    };

}
