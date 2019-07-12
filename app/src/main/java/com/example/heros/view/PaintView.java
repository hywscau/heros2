package com.example.heros.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.heros.R;

public class PaintView extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private RadialGradient mRadiaGradient1;
    private RadialGradient mRadiaGradient2;
    private RadialGradient mRadiaGradient3;
    private SweepGradient mSweepGradient;
    private LinearGradient mShader1;
    private LinearGradient mShader2;
    private LinearGradient mShader3;

    private BitmapShader mBitmapShader1;
    private BitmapShader mBitmapShader2;
    private BitmapShader mBitmapShader3;

    public PaintView(Context context) {
        this(context,null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(1080,3780);
    }

    private void initViews() {
        mPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(30);
        mTextPaint.setColor(Color.BLACK);

        //x0 y0 x1 y1：渐变的两个端点的位置  color0 color1 是端点的颜色
        //
        mRadiaGradient1 = new RadialGradient(200, 200, 100, Color.parseColor("#E91E63"),
                Color.YELLOW, Shader.TileMode.CLAMP);
        mRadiaGradient2 = new RadialGradient(200, 620, 100, Color.parseColor("#E91E63"),
                Color.YELLOW, Shader.TileMode.REPEAT);
        mRadiaGradient3 = new RadialGradient(200, 1040, 100, Color.parseColor("#E91E63"),
                Color.YELLOW, Shader.TileMode.MIRROR);

        mSweepGradient = new SweepGradient(700, 1040, Color.parseColor("#E91E63"),
                Color.YELLOW);



//        tileX：横向的 TileMode
//        tileY：纵向的 TileMode。

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawGradient(canvas);
        getComposeShader(canvas);
        drawLines(canvas);
    }

    private void drawGradient(Canvas canvas) {
        mPaint.setShader(mRadiaGradient1);
        canvas.drawCircle(200, 200, 200, mPaint);

        mPaint.setShader(mRadiaGradient2);
        canvas.drawCircle(200, 620, 200, mPaint);

        mPaint.setShader(mRadiaGradient3);
        canvas.drawCircle(200, 1040, 200, mPaint);

        mPaint.setShader(mSweepGradient);
        canvas.drawCircle(700, 1040, 200, mPaint);

        mShader1 = new LinearGradient(500, 0, 900, 200, Color.parseColor("#E91E63"),
                Color.YELLOW, Shader.TileMode.CLAMP);
        mShader2 = new LinearGradient(0, 50, 100, 50, Color.parseColor("#E91E63"),
                Color.YELLOW, Shader.TileMode.REPEAT);
        mShader3 = new LinearGradient(0, 50, 100, 50, Color.parseColor("#E91E63"),
                Color.YELLOW, Shader.TileMode.MIRROR);


        mPaint.setShader(mShader1);
        canvas.drawRect(500, 0, 900, 200,mPaint);
        canvas.drawLine(450, 100, 950, 100,mPaint);


        mPaint.setShader(mShader2);
        canvas.drawRect(500, 220, 900, 420,mPaint);
        canvas.drawLine(450, 320, 950, 320,mPaint);


        mPaint.setShader(mShader3);
        canvas.drawRect(500, 440, 900, 640,mPaint);
        canvas.drawLine(450, 540, 950, 540,mPaint);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.in_icon);
        mBitmapShader1 = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapShader2 = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mBitmapShader3 = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        mPaint.setShader(mBitmapShader1);
        canvas.drawRect(0, 1300, 250,1600, mPaint);

        mPaint.setShader(mBitmapShader2);
        canvas.drawRect(300, 1300, 550,1600,mPaint);

        mPaint.setShader(mBitmapShader3);
        canvas.drawRect(600, 1300, 850,1600, mPaint);
    }

    private void getComposeShader(Canvas canvas) {
        drawShapeByMode(0,1620,canvas,PorterDuff.Mode.SRC,"SRC");
        drawShapeByMode(300,1620,canvas,PorterDuff.Mode.DST,"DST");
        drawShapeByMode(600,1620,canvas,PorterDuff.Mode.SRC_IN,"SRC_IN");

        drawShapeByMode(0,2000,canvas,PorterDuff.Mode.SRC_OUT,"SRC_OUT");
        drawShapeByMode(300,2000,canvas,PorterDuff.Mode.DST_IN,"DST_IN");
        drawShapeByMode(600,2000,canvas,PorterDuff.Mode.DST_OUT,"DST_OUT");

        drawShapeByMode(0,2380,canvas,PorterDuff.Mode.SRC_OVER,"SRC_OVER");
        drawShapeByMode(300,2380,canvas,PorterDuff.Mode.SRC_ATOP,"SRC_ATOP");
        drawShapeByMode(600,2380,canvas,PorterDuff.Mode.DST_OVER,"DST_OVER");

        drawShapeByMode(0,2760,canvas,PorterDuff.Mode.DST_ATOP,"DST_ATOP");
        drawShapeByMode(300,2760,canvas,PorterDuff.Mode.XOR,"XOR");

    }

    private void drawShapeByMode(float left, float top,Canvas canvas, PorterDuff.Mode mode,String text) {
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Shader shader = new ComposeShader(shader1, shader2, mode);
        mPaint.setShader(shader);
        canvas.drawRect(left, top, left + 250,top+280, mPaint);
        canvas.drawText(text,left+100,top+320,mTextPaint);
    }

    private void drawLines(Canvas canvas) {

        mTextPaint.setStrokeWidth(40);
        mTextPaint.setTextSize(30);
        canvas.drawText("setStrokeCap(Paint.Cap cap)",0,3140,mTextPaint);
        mTextPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(0,3170,500,3170,mTextPaint);
        canvas.drawText("BUTT",550,3170,mTextPaint);

        mTextPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawLine(0,3220,500,3220,mTextPaint);
        canvas.drawText("ROUND",550,3220,mTextPaint);

        mTextPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(0,3270,500,3270,mTextPaint);
        canvas.drawText("SQUARE",550,3270,mTextPaint);


        canvas.drawText(" setStrokeJoin(Paint.Join join)",0,3350,mTextPaint);

        Paint paintLine = new Paint();
        paintLine.setStrokeWidth(40);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeJoin(Paint.Join.BEVEL);
        Path path1 = new Path();
        path1.moveTo(0,3390);
        path1.lineTo(250,3390);
        path1.lineTo(100,3590);
        canvas.drawPath(path1,paintLine);
        canvas.drawText("BEVEL",75,3640,mTextPaint);

        paintLine.setStrokeJoin(Paint.Join.MITER);
        Path path2 = new Path();
        path2.moveTo(300,3390);
        path2.lineTo(550,3390);
        path2.lineTo(400,3590);
        canvas.drawPath(path2,paintLine);
        canvas.drawText("MITER",375,3640,mTextPaint);

        paintLine.setStrokeJoin(Paint.Join.ROUND);
        Path path3 = new Path();
        path3.moveTo(600,3390);
        path3.lineTo(850,3390);
        path3.lineTo(700,3590);
        canvas.drawPath(path3,paintLine);
        canvas.drawText("ROUND",675,3640,mTextPaint);

    }
}
