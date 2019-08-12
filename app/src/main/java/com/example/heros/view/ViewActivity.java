package com.example.heros.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heros.R;
import com.example.heros.image.imageshape.XfermodeView;
import com.example.heros.view.pull.PullViewActivity;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class ViewActivity extends Activity implements View.OnClickListener {

    private TextToSpeech mSpeech;
    private Button btn_basic;
    private TextView tv_data;
    private TextView tv_html;
    private ImageView iv_pic;
    private FrameLayout fl_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        btn_basic = findViewById(R.id.btn_basic);
        findViewById(R.id.btn_basic).setOnClickListener(this);
        findViewById(R.id.btn_paint).setOnClickListener(this);
        findViewById(R.id.btn_xfermodes).setOnClickListener(this);
        findViewById(R.id.btn_paint2).setOnClickListener(this);
        findViewById(R.id.btn_text).setOnClickListener(this);
        findViewById(R.id.btn_canvas).setOnClickListener(this);
        findViewById(R.id.btn_region_click).setOnClickListener(this);
        findViewById(R.id.btn_calender).setOnClickListener(this);
        findViewById(R.id.btn_pull).setOnClickListener(this);
        findViewById(R.id.btn_circle).setOnClickListener(this);
        findViewById(R.id.btn_toast).setOnClickListener(this);
        tv_data = findViewById(R.id.tv_data);
        tv_data.setText(getClickableSpan());

        //设置超链接
        tv_data.setMovementMethod(LinkMovementMethod.getInstance());

        tv_html = findViewById(R.id.tv_html);
        String html = "连续签到<font color=red>7</font>天可获得1次兑换1元的机会，兑换后可重新获得提现机会（还需连续签到<font color=red>7</font>天）";
        tv_html.setText(Html.fromHtml(html));

        mSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = mSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("hyw", "not use");
                    } else {
                        mSpeech.speak("", TextToSpeech.QUEUE_FLUSH,
                                null);
                    }
                }
            }
        });

        mSpeech.speak("i am chinese",
                TextToSpeech.QUEUE_FLUSH, null);

        measure();
        measure2();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                autoClickPos(ViewActivity.this, 936,400);
            }
        }, 5000);

        tv_data.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        tv_data.getViewTreeObserver().removeOnPreDrawListener(this);
                        tv_data.getWidth(); // 获取宽度
                        tv_data.getHeight(); // 获取高度
                        Log.e("hyw","width:"+tv_data.getWidth());
                        Log.e("hyw","getHeight:"+tv_data.getHeight());
                        return true;
                    }
                });

        iv_pic = findViewById(R.id.iv_pic);
        fl_bg = findViewById(R.id.fl_bg);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               setMargins(fl_bg,0,200,0,0);
            }
        },2000);

    }

    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    private SpannableString getClickableSpan() {
        //监听器
        View.OnClickListener listener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("hyw", "listener1");
            }
        };
        String text = "登陆注册代表您已阅读\n并同意“用户协议”与“隐私条款”";
        SpannableString spanableInfo = new SpannableString(text);
        Log.e("hyw", "用户协议:" + text.indexOf("用户协议"));
        //可以为多部分设置超链接
        spanableInfo.setSpan(new Clickable(listener1), text.indexOf("用户协议"), text.indexOf("用户协议") + 4, Spanned.SPAN_MARK_MARK);
        spanableInfo.setSpan(new Clickable(listener1), text.indexOf("隐私条款"), text.indexOf("隐私条款") + 4, Spanned.SPAN_MARK_MARK);
       spanableInfo.setSpan(new ForegroundColorSpan(Color.RED),  text.indexOf("用户协议"), text.indexOf("用户协议") + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置前景色为洋红色
        return spanableInfo;
    }

    class Clickable extends ClickableSpan implements View.OnClickListener {
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener listener) {
            mListener = listener;
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view);
        }
    }


    int data = 10;
    ObjectAnimator animator;
    AnimatorSet set;
    float curTranslationY;

    private void measure() {
        ViewTreeObserver vto = btn_basic.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int height = btn_basic.getMeasuredHeight();
                int width = btn_basic.getMeasuredWidth();
//                Log.e("hyw", "width: " + width);
//                Log.e("hyw", "height: " + height);
//
//                Log.e("hyw", "width22: " + btn_basic.getWidth());
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("hyw","x:"+event.getRawX() + "y:"+event.getRawY());
        }
        return super.onTouchEvent(event);
    }

    private void measure2() {
        ViewTreeObserver vto = btn_basic.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                btn_basic.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                Log.e("hyw", "width: " + btn_basic.getWidth());
                Log.e("hyw", "height: " + btn_basic.getHeight());

                Log.e("hyw", "width33: " + btn_basic.getMeasuredWidth());
            }
        });

    }

    public void autoClickPos(Activity act, final double x, final double y) {
        Log.e("hyw","x:"+x+"    y:"+y);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 线程睡眠0.3s
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 利用ProcessBuilder执行shell命令
                String[] order = { "input", "tap", "" + x, "" + y };
                try {
                    new ProcessBuilder(order).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //x:936.0y:560.0

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_basic:
                intent.setClass(ViewActivity.this, BasicViewActivity.class);
                break;
            case R.id.btn_paint:
                intent.setClass(ViewActivity.this, PaintActivity.class);
                break;
            case R.id.btn_paint2:
                intent.setClass(ViewActivity.this, Paint2Activity.class);
                break;
            case R.id.btn_text:
                intent.setClass(ViewActivity.this, TextActivity.class);
                break;
            case R.id.btn_canvas:
                intent.setClass(ViewActivity.this, CanvasActivity.class);
                break;
            case R.id.btn_xfermodes:
                intent.setClass(ViewActivity.this, XfermodesActivity.class);
                break;
            case R.id.btn_region_click:
                intent.setClass(ViewActivity.this, RegionClickActivity.class);
                break;
            case R.id.btn_calender:
                intent.setClass(ViewActivity.this, CalenderActivity.class);
                break;
            case R.id.btn_pull:
                intent.setClass(ViewActivity.this, PullViewActivity.class);
                break;
            case R.id.btn_circle:
                intent.setClass(ViewActivity.this, CircleActivity.class);
                break;
            case R.id.btn_toast:
//                new ToastUtils(ViewActivity.this).showWithTime(10000);
//                ToastCustom.getInstance(ViewActivity.this).show("123",5000);
                new ToastUtils(ViewActivity.this).show(10000);
                return;
        }
        startActivity(intent);
    }

    int i;

    private void changeAllBtnBGColor(View view, int color) {
        if (view == null || !(view instanceof ViewGroup)) {
            return;
        }
        Stack m = new Stack<>();
        while (view != null) {
            ViewGroup tmpGroup = (ViewGroup) view;
            int count = tmpGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = tmpGroup.getChildAt(i);
                if (child instanceof ViewGroup) {
                    m.add(child);
                } else if (child instanceof Button) {
                    child.setBackgroundColor(color);
                }
            }
            if (m.isEmpty()) {
                break;
            } else {
                view = (View) m.pop();
            }
        }
    }
}
