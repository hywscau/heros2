package com.example.heros.optimization;

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.util.Log;

import com.example.heros.R;

public class OptimizationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimization);
        String path =  Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/";
        Log.e("hyw","path:"+path);
        Debug.startMethodTracing("testTrace");
        treadTest();
        runTest();
        Debug.stopMethodTracing();
    }

    private void treadTest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("hyw","sleep before");
                try {
                    Thread.sleep(5*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("hyw","sleep after");
            }
        }).start();
    }

    private void runTest() {
        Log.e("hyw","runTest before");
        for (int i = 0;i<1000;i++){
            new Object();
            i++;
        }
        Log.e("hyw","runTest after");
    }
}
