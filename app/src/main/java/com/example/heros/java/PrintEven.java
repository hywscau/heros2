package com.example.heros.java;

import android.util.Log;

public class PrintEven implements Runnable {

    Num num;

    public PrintEven(Num num) {
        this.num = num;

    }

    public void run() {
        while (num.i <= 100) {
            Log.e("hyw2"," while (num.i <= 100)");
            synchronized (num) {// 必须要用同意吧锁对象，这个对象是num
                if (!num.flag) {
                    Log.e("hyw2"," if (!num.flag)");
                    try {
                        num.wait();// wait()函数必须和锁死同一个
                    } catch (Exception e) {
                        Log.e("hyw2","偶数----" + e.getMessage());
                    }
                    Log.e("hyw2"," num.wait()");
                } else {
                    Log.e("hyw2","偶数----" + num.i);
                    num.i++;
                    num.flag = false;
                    num.notify();
                }

            }
        }
    }

}
