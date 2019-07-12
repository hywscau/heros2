package com.example.heros.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.heros.R;
import com.example.hyw.aidlserver.IMyAidlInterface;

import java.lang.ref.WeakReference;

public class ServiceActivity extends Activity implements View.OnClickListener {

    private MyService1 myService1;
    private MyService2 myService2;
    private TextView textView;
    private IMyAidlInterface iMyAidlInterface2;
    private IMyAidlInterface iMyAidlInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        findViewById(R.id.btn_start_service1).setOnClickListener(this);
        findViewById(R.id.btn_stop_service1).setOnClickListener(this);
        findViewById(R.id.btn_start_service2).setOnClickListener(this);
        findViewById(R.id.btn_stop_service2).setOnClickListener(this);
        findViewById(R.id.btn_get_data1).setOnClickListener(this);
        findViewById(R.id.btn_get_data2).setOnClickListener(this);
        findViewById(R.id.btn_aidl).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service1:
                bindService1();
                break;
            case R.id.btn_stop_service1:
                unbindService1();
                break;
            case R.id.btn_start_service2:
                bindService2();
                break;
            case R.id.btn_stop_service2:
                unbindService2();
                break;
            case R.id.btn_get_data1:
                Log.e("hyw","geyint:"+ myService1.getNumber());
                break;
            case R.id.btn_get_data2:
//                Log.e("hyw","geyint:"+ myService2.getNumber());
                bindAidlService2();
                break;
            case R.id.btn_aidl:
                try {
                    bindAidlService();
                } catch (Exception e) {
                    Log.e("hyw","bindAidlService Exception:"+e.getMessage());
                }

                break;
        }
    }

    private ServiceConnection conn1 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("hyw","onServiceConnected");
            myService1 = ((MyService1.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("hyw","onServiceDisconnected");
            myService1 = null;
        }
    };

    private ServiceConnection conn2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("hyw","onServiceConnected");
            myService2 = ((MyService2.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("hyw","onServiceDisconnected");
            myService2 = null;
        }
    };

    private void bindService1(){
        Intent intent = new Intent(this,MyService1.class);
        bindService(intent,conn1, Context.BIND_AUTO_CREATE);
    }

    private void unbindService1(){
        unbindService(conn1);
    }

    private void bindService2(){
        Intent intent = new Intent(this,MyService2.class);
//        bindService(intent,conn2, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    private void unbindService2(){
        unbindService(conn2);
    }

    private static class MyHandler extends Handler {

        WeakReference<ServiceActivity> weakReference;

        MyHandler(ServiceActivity testActivity) {
            this.weakReference = new WeakReference<ServiceActivity>(testActivity);

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            weakReference.get().textView.setText("do someThing");
        }
    }


    //绑定服务回调
    private ServiceConnection connAdil = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务绑定成功后调用，参数中的service即是在服务端onBind方法中返回的iBinder，即已实现的接口
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            Log.e("hyw", "onServiceConnected: iMyAidlInterface" + iMyAidlInterface);
            try {
                Log.e("hyw", "iMyAidlInterface:"+iMyAidlInterface.add(1,2));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //解除绑定时调用， 清空接口，防止内容溢出
            iMyAidlInterface = null;
        }
    };

    //绑定服务回调
    private ServiceConnection connAdil2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //服务绑定成功后调用，参数中的service即是在服务端onBind方法中返回的iBinder，即已实现的接口
            iMyAidlInterface2 = IMyAidlInterface.Stub.asInterface(service);
            Log.e("hyw", "onServiceConnected: iMyAidlInterface2" + iMyAidlInterface2);
            try {
                Log.e("hyw", "iMyAidlInterface:"+iMyAidlInterface2.add(1,2));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //解除绑定时调用， 清空接口，防止内容溢出
            iMyAidlInterface2 = null;
        }
    };


    private void bindAidlService(){
        Intent intent = new Intent();
//        intent.setAction("com.example.hyw.aidlserver.IRemoteService2");
        intent.setComponent(new ComponentName("com.example.hyw.aidlserver"
                , "com.example.hyw.aidlserver.IRemoteService2"));
        boolean isBind = bindService(intent, connAdil, Context.BIND_AUTO_CREATE);
        Log.e("hyw", "bindService: isBind->"+isBind);

    }

    private void bindAidlService2(){
        Intent intent = new Intent();
//        intent.setAction("com.example.hyw.aidlserver.IRemoteService2");
        intent.setComponent(new ComponentName("com.example.heros"
                , "com.example.heros.service.MyService2"));
        boolean isBind = bindService(intent, connAdil2, Context.BIND_AUTO_CREATE);
        Log.e("hyw", "bindService: isBind->"+isBind);

    }





}
