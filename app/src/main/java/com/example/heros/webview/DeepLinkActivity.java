package com.example.heros.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.heros.R;

public class DeepLinkActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("hyw","DeepLinkActivity oncreate:");
        setContentView(R.layout.activity_deep_link);
        textView = findViewById(R.id.tv_deeplink);

        Uri uri = getIntent().getData();
        Log.e("hyw","uri:"+uri.toString());
        //Uri 规则: scheme://host+path?query
        //这里以“https://yaoyi.ypzdw.com/article/413?type=url&from=web”为例
        String scheme = uri.getScheme(); //https , "://" 这个前面的就表示scheme
        String host = uri.getHost(); //yaoyi.ypzdw.com
        String path = uri.getPath(); //article/413
        String query = uri.getQuery(); //type=mobile&from=web
        String type = uri.getQueryParameter("key"); //url
        String log = "scheme = " + scheme + ", host = " + host + ", path = " + path + ", query = " + query+", type = " + type;
        Log.e("hyw", log);

//        textView.setText(log);
        finish();
    }

    /**
     * 第三方应用调用
     */
    private void awakeApp() {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("myScheme://blog/appthird?key=1&name=veidy&sex=男");
        intent2.setData(uri);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("hyw","DeepLinkActivity onDestroy");
    }
}
