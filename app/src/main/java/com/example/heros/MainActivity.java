package com.example.heros;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.example.heros.anim.AnimActivity;
import com.example.heros.annotation.AnnotationActivity;
import com.example.heros.customview.DragViewActivity;
import com.example.heros.customview.drawpath.DrawPathActivity;
import com.example.heros.fancyBehavior.FancyBehaviorActivity;
import com.example.heros.image.ImageActivity;
import com.example.heros.image.imageshape.ImageShapeActivity;
import com.example.heros.java.Num;
import com.example.heros.java.PrintEven;
import com.example.heros.java.PrintOdd;
import com.example.heros.listview.ListViewActivity;
import com.example.heros.newFeatureOfL.PaletteActivity;
import com.example.heros.newFeatureOfL.ToolbarActivity;
import com.example.heros.newFeatureOfL.TransitionActivity;
import com.example.heros.optimization.OptimizationActivity;
import com.example.heros.process.FrontProcessActivity;
import com.example.heros.scaleRuler.ScaleRulerActivity;
import com.example.heros.service.ServiceActivity;
import com.example.heros.system.SystemActivity;
import com.example.heros.systemWidget.SystemWidgetActivity;
import com.example.heros.systemWidget.touchEvent.TouchEventActivity;
import com.example.heros.text.TextViewActivity;
import com.example.heros.tool.ToolActivity;
import com.example.heros.view.ViewActivity;
import com.example.heros.webview.WebActivity;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        Num num = new Num();

        PrintOdd printOdd = new PrintOdd(num);
        PrintEven printEven = new PrintEven(num);

        Thread thread1 = new Thread(printOdd);
        Thread thread2 = new Thread(printEven);

//        thread1.start();
//        thread2.start();
//        ArrayList<String> dd;
//        dd.size();
        MyAppInstallReceiver mMyAppInstallReceiver  = new MyAppInstallReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addAction("android.intent.action.PACKAGE_REMOVED");
        filter.addDataScheme("package");
        registerReceiver(mMyAppInstallReceiver, filter);

    }

    private class MyAppInstallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("hyw","intent22:"+intent.getAction());
        }
    }

    /**
     * 唤醒super sleep
     */
    private void awakeSuperSleep() {
        Log.e("hyw", "awakeSuperSleep:" );
        try {
            Uri uri = Uri.parse("content://com.example.hyw.myapplication/AppLockDataTable");
            Cursor cursor = getContentResolver().query(uri, null, "com.example.heros", null, null);
            if (cursor != null) {
                Log.e("hyw", " cursor != null");
                cursor.close();
            } else {
                Log.e("hyw", "cursor == null");
            }
        } catch (Exception e) {
            Log.e("hyw", "awakeSuperSecurity:" + e.getMessage());
        }
    }


    private void initViews() {
        findViewById(R.id.btn_system_widget).setOnClickListener(this);
        findViewById(R.id.btn_event).setOnClickListener(this);
        findViewById(R.id.btn_list_view).setOnClickListener(this);
        findViewById(R.id.btn_drag_view).setOnClickListener(this);
        findViewById(R.id.btn_image).setOnClickListener(this);
        findViewById(R.id.btn_image_shap).setOnClickListener(this);
        findViewById(R.id.btn_draw_path).setOnClickListener(this);
        findViewById(R.id.btn_anim).setOnClickListener(this);
        findViewById(R.id.btn_system).setOnClickListener(this);
        findViewById(R.id.btn_transition).setOnClickListener(this);
        findViewById(R.id.btn_palette).setOnClickListener(this);
        findViewById(R.id.btn_toolbar).setOnClickListener(this);
        findViewById(R.id.btn_ruler).setOnClickListener(this);
        findViewById(R.id.btn_fancy_behavior).setOnClickListener(this);
        findViewById(R.id.btn_view).setOnClickListener(this);
        findViewById(R.id.btn_text).setOnClickListener(this);
        findViewById(R.id.btn_annotation).setOnClickListener(this);
        findViewById(R.id.btn_optimization).setOnClickListener(this);
        findViewById(R.id.btn_service).setOnClickListener(this);
        findViewById(R.id.btn_tool).setOnClickListener(this);
        findViewById(R.id.btn_web).setOnClickListener(this);
        findViewById(R.id.btn_front_process).setOnClickListener(this);
        findViewById(R.id.btn_58).setOnClickListener(this);
        findViewById(R.id.btn_youxin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btn_system_widget:
                intent = new Intent(MainActivity.this, SystemWidgetActivity.class);
                break;
            case R.id.btn_event:
                intent = new Intent(MainActivity.this, TouchEventActivity.class);
                break;
            case R.id.btn_list_view:
                intent = new Intent(MainActivity.this, ListViewActivity.class);
                break;
            case R.id.btn_drag_view:
                intent = new Intent(MainActivity.this, DragViewActivity.class);
                break;
            case R.id.btn_image:
                intent = new Intent(MainActivity.this, ImageActivity.class);
                break;
            case R.id.btn_image_shap:
                intent = new Intent(MainActivity.this, ImageShapeActivity.class);
                break;
            case R.id.btn_draw_path:
                intent = new Intent(MainActivity.this, DrawPathActivity.class);
                break;
            case R.id.btn_anim:
                intent = new Intent(MainActivity.this, AnimActivity.class);
                break;
            case R.id.btn_system:
                intent = new Intent(MainActivity.this, SystemActivity.class);
                break;
            case R.id.btn_transition:
                intent = new Intent(MainActivity.this, TransitionActivity.class);
                break;
            case R.id.btn_palette:
                intent = new Intent(MainActivity.this, PaletteActivity.class);
                break;
            case R.id.btn_toolbar:
                intent = new Intent(MainActivity.this, ToolbarActivity.class);
                break;
            case R.id.btn_ruler:
                intent = new Intent(MainActivity.this, ScaleRulerActivity.class);
                break;
            case R.id.btn_fancy_behavior:
                intent = new Intent(MainActivity.this, FancyBehaviorActivity.class);
                break;
            case R.id.btn_view:
                intent = new Intent(MainActivity.this, ViewActivity.class);
                break;
            case R.id.btn_text:
                intent = new Intent(MainActivity.this, TextViewActivity.class);
                break;
            case R.id.btn_annotation:
                intent = new Intent(MainActivity.this, AnnotationActivity.class);
                break;
            case R.id.btn_optimization:
                intent = new Intent(MainActivity.this, OptimizationActivity.class);
                break;
            case R.id.btn_service:
                intent = new Intent(MainActivity.this, ServiceActivity.class);
                break;
            case R.id.btn_tool:
                intent = new Intent(MainActivity.this, ToolActivity.class);
                break;
            case R.id.btn_web:
                intent = new Intent(MainActivity.this, WebActivity.class);
                break;
            case R.id.btn_front_process:
                intent = new Intent(MainActivity.this, FrontProcessActivity.class);
//                getUserAgentNew(this);
                break;
            case R.id.btn_58:
                try {
                    taskLink2();
                } catch (Exception e) {
                    Log.e("hyw","exception:"+e.getMessage());
                }

                return;
            case R.id.btn_youxin:
                taskLink();
                return;

        }
        startActivity(intent);  //等驾驭 startActivityForResult(intent,RESULT_OK);
    }

    String link2 = "baiduboxapp://v1/browser/open?url=https%3A%2F%2Fsmartprogram.baidu.com%2Fstatic%2Fdistribution%2Findex.html%3FswanUrl%3D%252Fpages%252Fshangye%252Findex%252Findex%253F%26appKey%3DMzyRWoIRjWzq3pKItz5Dlt3Qhs7xSaKH%26webUrl%3Dhttps%253A%252F%252Fmbd.baidu.com%252Fma%252Ftips%253FappKey%253DMzyRWoIRjWzq3pKItz5Dlt3Qhs7xSaKH%26word%3D%25E4%25BA%258C%25E6%2589%258B%25E8%25BD%25A6%26swan_from%3D1081001400000000%26from%3D844b%26title%3D%26redirect_url%26gpc%3D%26m%3D%26nojc%3D%26pn%3D%26sa%3Doxi_tieba_1%26si%3D%26srlang%3D%26usm%3D%26keysearch%3Dautodq&needlog=1&logargs=%7B%22source%22%3A%221022189q%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%22%22%2C%22extlog%22%3A%7B%22appkey%22%3A%22MzyRWoIRjWzq3pKItz5Dlt3Qhs7xSaKH%22%7D%2C%22baiduId%22%3A%22B98E62712CA7E6B5950D8D72E501CFCB%3AFG%3D1%22%2C%22app_now%22%3A%22chrome_1562143209409_3154275934%22%2C%22yyb_pkg%22%3A%22com.baidu.searchbox%22%2C%22idmData%22%3A%7B%22firstOpen%22%3A%22main%22%2C%22secondOpen%22%3A%22lite%22%2C%22status%22%3A%22-1%22%7D%2C%22matrix%22%3A%22main%22%7D";
    private void taskLink() {
        String link = "baiduboxapp://v1/browser/open?url=https%3A%2F%2Fsmartprogram.baidu.com%2Fstatic%2Fdistribution%2Findex.html%3FswanUrl%3D%252Fpages%252Fhome%252Fhome%253F%26appKey%3DTZzPY8qVvX97uzIH4L2SnY0g%26webUrl%3Dhttps%253A%252F%252Fmbd.baidu.com%252Fma%252Ftips%253FappKey%253DTZzPY8qVvX97uzIH4L2SnY0g%26word%3D%25E4%25BA%258C%25E6%2589%258B%25E8%25BD%25A6%26swan_from%3D1081001400000000%26from%3D844b%26title%3D%26redirect_url%26gpc%3D%26m%3D%26nojc%3D%26pn%3D%26sa%3Doxi_tieba_1%26si%3D%26srlang%3D%26usm%3D%26keysearch%3Dautodq&needlog=1&logargs=%7B%22source%22%3A%221022189q%22%2C%22from%22%3A%22openbox%22%2C%22page%22%3A%22chrome%22%2C%22type%22%3A%22%22%2C%22value%22%3A%22%22%2C%22channel%22%3A%22%22%2C%22extlog%22%3A%7B%22appkey%22%3A%22TZzPY8qVvX97uzIH4L2SnY0g%22%7D%2C%22baiduId%22%3A%22B98E62712CA7E6B5950D8D72E501CFCB%3AFG%3D1%22%2C%22app_now%22%3A%22chrome_1562142848205_2246295215%22%2C%22yyb_pkg%22%3A%22com.baidu.searchbox%22%2C%22idmData%22%3A%7B%22firstOpen%22%3A%22main%22%2C%22secondOpen%22%3A%22lite%22%2C%22status%22%3A%22-1%22%7D%2C%22matrix%22%3A%22main%22%7D";
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(link);
        intent2.setData(uri);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);

    }

    private void taskLink2() {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(link2);
        intent2.setData(uri);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);

    }

    private static final String DEFAULT_UA = "Mozilla/5.0 (Linux; U; Android 1.1; en-gb; dream) " +
            "AppleWebKit/525.10+ (KHTML, like Gecko) Version/3.0.4 Mobile Safari/523.12.2 – G1 Phone";//ua默认值

    public static String getUserAgentNew(Context context) {
        Log.e("hyw","getDefaultUserAgent");
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {

                userAgent = WebSettings.getDefaultUserAgent(context);

            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
                if (TextUtils.isEmpty(userAgent))
                    userAgent = DEFAULT_UA;
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        //中文过滤
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        Log.e("hyw","getDefaultUserAgent end");
        return sb.toString();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("hyw", "onActivityResult");
    }
}
