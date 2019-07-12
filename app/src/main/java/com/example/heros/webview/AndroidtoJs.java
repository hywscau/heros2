package com.example.heros.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

// 继承自Object类
public class AndroidtoJs extends Object {

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
       Log.e("hyw","JS调用了Android的hello方法");
    }
}