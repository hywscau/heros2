package com.example.heros.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.heros.R;
import com.example.heros.image.imageshape.XfermodeView;
import com.example.heros.view.pull.PullViewActivity;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Stack;

public class ViewActivity extends Activity implements View.OnClickListener {

    private TextToSpeech mSpeech;
    private Button btn_basic;
    private TextView tv_data;
     TickerView tickerView;

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

        tv_data = findViewById(R.id.tv_data);
        curTranslationY = tv_data.getTranslationY();
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(tv_data, "alpha", 1f, 0f);
        animator = ObjectAnimator.ofFloat(tv_data, "translationY", curTranslationY, curTranslationY-150);
        set = new AnimatorSet();
        set.play(animator).with(fadeInOut);
        set.setDuration(200);
        set.start();
//        animator.setDuration(2000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
//                tv_data.setAlpha(1);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e("hyw","onAnimationEnd");
                if(data++<20) {
                    tv_data.setAlpha(0);
                    tv_data.setText(data+"");
                    set.start();;
                } else {
                    tv_data.setAlpha(1);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(tv_data, "translationY", curTranslationY, curTranslationY);
                    animator.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();;

       tickerView = findViewById(R.id.tickerView);
        tickerView.setCharacterList(TickerUtils.getDefaultNumberList());
        tickerView.setText("50" );
        tickerView.setAnimationDuration(2000);
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
        }
//        startActivity(intent);
        tickerView.setText("200" );
    }
    int i ;

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
                view = (View)m.pop();
            }
        }
    }
}
