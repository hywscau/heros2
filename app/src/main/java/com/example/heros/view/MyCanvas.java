package com.example.heros.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.heros.R;

public class MyCanvas extends View {

    private Paint mPaint;

    public MyCanvas(Context context) {
        this(context,null);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
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
        Log.e("hyw","onDraw");
        setBackgroundColor(Color.YELLOW);
        setPadding(10,10,10,10);

        canvas.drawText("画布裁剪：clipRect",0,50,mPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        canvas.drawBitmap(bitmap, 0, 70, mPaint);

        canvas.save();
        canvas.translate(500,70);
        //裁剪画布
        canvas.clipRect(0, 0, 200, 100);
        canvas.drawColor(Color.RED);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(750,70);
        canvas.drawBitmap(bitmap,new Rect(100,0,300,100),new Rect(0,0,200,100),mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(500,200);
        Path path1 = new Path();
        path1.addOval(new RectF(0,0,200,100),Path.Direction.CCW);
        canvas.clipPath(path1);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();

        canvas.drawText("画布平移：translate",0,70 + bitmap.getHeight() + 50,mPaint);
        canvas.save();
        canvas.translate(0, 130 + bitmap.getHeight());
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();

        canvas.drawText("画布旋转：rotate",0,200 + bitmap.getHeight()*2,mPaint);
        canvas.save();
        canvas.rotate(45, 500 + bitmap.getWidth()/2, 130 + bitmap.getHeight()*1.5f);
        canvas.drawBitmap(bitmap, 500, 130 + bitmap.getHeight(), mPaint);
        canvas.restore();

        canvas.drawText("画布缩放：scale",0,300 + bitmap.getHeight()*2,mPaint);
        canvas.save();
        canvas.translate(0, 300 + bitmap.getHeight()*2);
        canvas.scale(0.5f,0.5f);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();

        canvas.drawText("画布错切：skew",500,300 + bitmap.getHeight()*2,mPaint);
        canvas.save();
        canvas.translate(500, 300 + bitmap.getHeight()*2);
        canvas.scale(0.5f,0.5f);
        canvas.skew(0,0.5f);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);
        canvas.restore();

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
