package com.example.heros.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heros.R;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

/**
 * created by HYW on 2019/7/12 0012
 * Describe:
 */
public class ToastUtils {

    private Toast mToast;
    private Activity mActivity;
    private TextView mTextTimeLeft;
    private TextView mTextGold;
    private int mTimeLeft;
    private long lastTime;
    private TimeCount time;
    private boolean canceled = true;
    private Handler handle = new Handler();

    public ToastUtils(Activity activity) {
        this.mActivity = activity;
        mToast = new Toast(activity);
        initView();
    }


    private void initView() {
        LayoutInflater inflater = mActivity.getLayoutInflater();//调用Activity的getLayoutInflater()
        View view = inflater.inflate(R.layout.toast_style, null); //加載layout下的布局
        ImageView iv = view.findViewById(R.id.iv_gold_icon);
        mTextTimeLeft = view.findViewById(R.id.tv_time_left);
        mTextTimeLeft.setText("剩余时间2s"); //toast的标题
        mTextGold = view.findViewById(R.id.tv_gold_num);
        mToast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
        mToast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
        mToast.setView(view); //添加视图文件
        setClickZoomEffect(iv);
        final float curTranslationX = mTextGold.getTranslationY();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectAnimator animator = ObjectAnimator.ofFloat(mTextGold, "translationY", curTranslationX+ 200, curTranslationX, curTranslationX);
                long time = System.currentTimeMillis() - lastTime;
                Log.e("hyw", "time:"+time);
                if (time > 500) {
                    time = 500;
                }
                animator.setDuration(time);
                animator.start();
                lastTime = System.currentTimeMillis();
            }
        });
        enableClicable();
    }

    public void show() {
        mToast.show();
    }

    int num = 0;
    float curTranslationX;

    public void showWithTime(final int time) {
        final Timer timer = new Timer();
        mTimeLeft = time/1000;
        curTranslationX = mTextGold.getTranslationY();
        num = 0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("hyw","mToast.show()");
                        mToast.show();
                        if (System.currentTimeMillis() - lastTime > 500) {
                            mTextTimeLeft.setText("剩余时间"+ mTimeLeft--+"s"); //toast的标题
                            ObjectAnimator animator = ObjectAnimator.ofFloat(mTextGold, "translationY", curTranslationX+ 200, curTranslationX, curTranslationX);
                            animator.setDuration(1000);
                            animator.start();
                        }
                    }
                });
            }
        }, 0, 500);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mToast.cancel();
                timer.cancel();
            }
        }, time);
    }

    private void enableClicable() {
        try {
            Object mTN;
            mTN = getField(mToast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    //Toast可点击
                    params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     */
    private Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }

    /**
     * 设置view点击缩小效果
     * @param view
     */
    public  void setClickZoomEffect(final View view) {
        if (view != null) {
            view.setOnTouchListener(new View.OnTouchListener() {
                boolean cancelled;
                Rect rect = new Rect();

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            scaleTo(v, 0.85f);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (rect.isEmpty()) {
                                v.getDrawingRect(rect);
                            }
                            if (!rect.contains((int) event.getX(), (int) event.getY())) {
                                scaleTo(v, 0.92f);
                                cancelled = true;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            scaleTo(v, 1);
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            if (!cancelled) {
                                scaleTo(v, 1);
                            } else {
                                cancelled = false;
                            }
                            break;
                    }
                    return false;
                }

            });
        }
    }


    public void scaleTo(View v, float scale) {
        if (Build.VERSION.SDK_INT >= 11) {
            v.setScaleX(scale);
            v.setScaleY(scale);
        } else {
            float oldScale = 1;
            if (v.getTag(Integer.MIN_VALUE) != null) {
                oldScale = (Float) v.getTag(Integer.MIN_VALUE);
            }
            final ViewGroup.LayoutParams params = v.getLayoutParams();
            params.width = (int) ((params.width / oldScale) * scale);
            params.height = (int) ((params.height / oldScale) * scale);
        }
    }

    /**
     * @param duration 显示的时间长
     *                 根据LENGTH_MAX进行判断
     *                 如果不匹配，进行系统显示
     *                 如果匹配，永久显示，直到调用hide()
     */
    public void show(int duration) {
        curTranslationX = mTextGold.getTranslationY();
        time = new TimeCount(duration, 1000);//1000是消失渐变时间
        if (canceled) {
            time.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏Toast
     */
    public void hide() {
        if (mToast != null) {
            mToast.cancel();
        }
        canceled = true;
    }

    private void showUntilCancel() {
        if (canceled) {
            return;
        }
        mToast.show();
        handle.postDelayed(new Runnable() {
            public void run() {
                showUntilCancel();
            }
        }, 3000);
    }

    /**
     * 计时器
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); // 总时长,计时的时间间隔
        }

        @Override
        public void onFinish() { // 计时完毕时触发
            hide();
        }

        @Override
        public void onTick(long millisUntilFinished) { // 计时过程显示
            if (System.currentTimeMillis() - lastTime > 500) {
                mTextTimeLeft.setText("剩余时间"+ mTimeLeft--+"s"); //toast的标题
                ObjectAnimator animator = ObjectAnimator.ofFloat(mTextGold, "translationY", curTranslationX+ 200, curTranslationX, curTranslationX);
                animator.setDuration(1000);
                animator.start();
            }
        }

    }



}
