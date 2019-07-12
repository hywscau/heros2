package com.example.heros.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.heros.R;

public class Pain2View extends View {

    private Paint mPaint;

    public Pain2View(Context context) {
        this(context,null);
    }

    public Pain2View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Pain2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setTextSize(40);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("setPathEffect(PathEffect effect)",0,30,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        PathEffect pathEffect = new DashPathEffect(new float[]{20, 5}, 10);
        mPaint.setPathEffect(pathEffect);

        canvas.drawCircle(250, 250, 200, mPaint);
        canvas.drawLine(500, 150, 1000,150, mPaint); //需要关闭硬件加速
        canvas.drawRect(500,200,1000,400,mPaint);
//        注意： PathEffect 在有些情况下不支持硬件加速，需要关闭硬件加速才能正常使用：
//        Canvas.drawLine() 和 Canvas.drawLines() 方法画直线时，setPathEffect()是不支持硬件加速的；
//        PathDashPathEffect 对硬件加速的支持也有问题，所以当使用 PathDashPathEffect 的时候，最好也把硬件加速关了。


        mPaint.setTextSize(80);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShadowLayer(10, 0, 0, Color.RED);
        canvas.drawText("setShadowLayer", 0, 530, mPaint);
        //BlurMaskFilter
        mPaint.setTextSize(40);
        mPaint.setShadowLayer(0, 0, 0, Color.RED);
        canvas.drawText("BlurMaskFilter", 0, 580, mPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        canvas.drawBitmap(bitmap, 0, 620, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawText("原图", 100, 820, mPaint);
//        NORMAL: 内外都模糊绘制
//        SOLID: 内部正常绘制，外部模糊
//        INNER: 内部模糊，外部不绘制
//        OUTER: 内部不绘制，外部模糊（什么鬼？）
        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        canvas.drawBitmap(bitmap, 500, 620, mPaint);
        mPaint.setMaskFilter(null);
        canvas.drawText("NORMAL", 600, 820, mPaint);

        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
        canvas.drawBitmap(bitmap, 0, 1020, mPaint);
        canvas.drawText("SOLID", 100, 1220, mPaint);

        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));
        canvas.drawBitmap(bitmap, 500, 1020, mPaint);
        mPaint.setMaskFilter(null);
        canvas.drawText("INNER", 600, 1220, mPaint);

//        4.1 reset()
//        重置 Paint 的所有属性为默认值。相当于重新 new 一个，不过性能当然高一些啦。


    }
}
