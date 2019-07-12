package com.example.heros.java;

import android.util.Log;

public class PrintOdd implements Runnable {
    Num num;

    public PrintOdd(Num num) {
        this.num = num;

    }

    public void run() {
        while (num.i <= 100) {
            synchronized (num) {
                if (num.flag) {
                    try {
                        num.wait();
                    } catch (Exception e) {
                        Log.e("hyw","奇数----" + e.getMessage());
                    }

                } else {
                    Log.e("hyw","奇数----" + num.i);
                    num.i++;
                    num.flag = true;
                    num.notify();
                    Log.e("hyw2","num.notify()");
                }

            }
        }
    }
}
