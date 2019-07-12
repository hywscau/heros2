package com.example.heros.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.heros.R;

/**
 * created by HYW on 2019/6/24 0024
 * Describe:
 */
public class GrdientView extends View {

    private Paint mPaint;
    Handler mHandler = new Handler();
    private int width = 200;
    private int height = 0;

    public GrdientView(Context context) {
        this(context,null);
    }

    public GrdientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GrdientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(50);
        mPaint.setTextSize(50);
        mHandler.postAtTime(new Runnable() {
            @Override
            public void run() {

                if (width>450) {
                    width = 200;
                    height= 0;
                } else {
                    width += 10;
                    height += 10;
                    if (width<320) {
                        setAlpha((width-200)/120.0f);
                    } else {
                        setAlpha(1-(width-320)/130.0f+0.2f);
                    }
                }
                invalidate();
                mHandler.postDelayed(this,30);
            }
        },50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("hyw", "onDraw");
        setBackgroundColor(Color.YELLOW);
        canvas.translate(canvas.getWidth()/2,canvas.getHeight()/2);
        Rect rect = new Rect(-width,-canvas.getHeight()/2 + 0 -height,width,canvas.getHeight()/2 - 0 + height);

        int colorStart = getResources().getColor(R.color.colorPrimary);
        colorStart = Color.parseColor("#ff6347");
//        int color1 = Color.GRAY;
        int colorEnd = getResources().getColor(R.color.colorAccent);
        colorEnd = Color.parseColor("#ffb6c1");

        LinearGradient backGradient = new LinearGradient(-width/2, 0, width/2, 0, new int[]{colorStart ,colorEnd}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(backGradient);
        canvas.drawRect(rect, mPaint);
//        setAlpha((600-width)/400.0f);

    }
}
