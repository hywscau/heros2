package com.example.heros.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.heros.R;

public class MyCircleView extends View {

    private Paint mPaint;
    private float mWidth;

    public MyCircleView(Context context) {
        this(context,null);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
        mWidth = getWidth();
    }

    private void initViews() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(50);
        mPaint.setTextSize(50);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("hyw","onDraw:"+canvas.getWidth());
        mWidth = canvas.getWidth();


        canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2); //将位置移动画纸的坐标点:150,150
        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0,0,canvas.getWidth()/2,mPaint);
        mPaint.setColor(Color.WHITE);
        Log.e("hyw","canvas.getWidth():"+canvas.getWidth());
        canvas.drawCircle(0,0,canvas.getWidth()/2 - 50,mPaint);

        Bitmap bitmapGolden = BitmapFactory.decodeResource(getResources(), R.drawable.golden);


        for (int i = 0; i <8 ; i++) {
            canvas.save();
            canvas.rotate(360/8 * i,0f,0f); //旋转画纸
            mPaint.setColor(Color.YELLOW);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(30);
            canvas.drawLine(0,0,0,-mWidth/2,mPaint);
            canvas.restore();
        }

        for (int i = 0; i <8 ; i++) {
            canvas.save();
            canvas.rotate(360/8 * i + 360/16 ,0f,0f); //旋转画纸
            canvas.drawBitmap(bitmapGolden, -bitmapGolden.getWidth()/2, -200-bitmapGolden.getHeight(), mPaint);
            canvas.restore();
        }

        Bitmap bitmapChoujiang = BitmapFactory.decodeResource(getResources(), R.drawable.choujiang);
        canvas.drawBitmap(bitmapChoujiang, -bitmapChoujiang.getWidth()/2, -bitmapChoujiang.getHeight()/2, mPaint);


    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.e("hyw","dispatchDraw");
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
