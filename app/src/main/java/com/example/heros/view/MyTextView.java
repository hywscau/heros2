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
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.heros.R;

public class MyTextView extends View {

    private Paint mPaint;
    private TextPaint mTextPaint;

    public MyTextView(Context context) {
        this(context,null);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(1080,4000);
    }

    private void initViews() {
        mPaint = new Paint();
        mPaint.setTextSize(80);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(50);
        mTextPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("hyw","text ondraw");
        String text = "Hjjggff 一二三四五六日";
        canvas.drawText(text, 100, 100, mPaint);
        canvas.drawLine(0,100,1000,100,mPaint);
        canvas.drawLine(100,0,100,150,mPaint);

        Path path = new Path();
        path.moveTo(0,200);
        path.lineTo(300,300);
        path.lineTo(600,200);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,mPaint);
        canvas.drawTextOnPath("Hello HeCoder", path, 0, 0, mPaint);

        String textString = "Hjjggff 一二三四五六日 \n Hello HeCoder";
        canvas.drawText(textString, 0, 350, mTextPaint);

        StaticLayout staticLayout1 = new StaticLayout(textString, mTextPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        String text2 = "StaticLayout\n换行\n换行";
        StaticLayout staticLayout2 = new StaticLayout(text2, mTextPaint, 600,
                Layout.Alignment.ALIGN_NORMAL, 1, 0, true);

        canvas.save();
        canvas.translate(0, 400);
        staticLayout1.draw(canvas);
        canvas.translate(0, 100);
        staticLayout2.draw(canvas);
        canvas.restore();


        mTextPaint.setTypeface(Typeface.DEFAULT);
        canvas.drawText("mTextPaint.setTypeface(Typeface.DEFAULT)", 0, 750, mTextPaint);
        mTextPaint.setTypeface(Typeface.SERIF);
        canvas.drawText("mTextPaint.setTypeface(Typeface.SERIF)", 0, 800, mTextPaint);
//        mTextPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Satisfy-Regular.ttf"));
//        canvas.drawText("Satisfy-Regular.ttf", 100, 650, mTextPaint);

        mTextPaint.setTypeface(Typeface.DEFAULT);
        mTextPaint.setFakeBoldText(false);
        canvas.drawText("我不是粗体setFakeBoldText", 0, 870, mTextPaint);
        mTextPaint.setFakeBoldText(true);
        canvas.drawText("我是粗体setFakeBoldText", 0, 920, mTextPaint);

        mTextPaint.setFakeBoldText(false);
        mTextPaint.setStrikeThruText(true);
        canvas.drawText("设置删除线：setStrikeThruText", 0, 1000, mTextPaint);

        mTextPaint.setStrikeThruText(false);
        mTextPaint.setUnderlineText(true);
        canvas.drawText("设置下划线：setUnderlineText", 0, 1070, mTextPaint);

        mTextPaint.setUnderlineText(false);
        mTextPaint.setTextSkewX(-0.5f);
        canvas.drawText("斜体：setTextSkewX", 0, 1140, mTextPaint);

        mTextPaint.setTextSkewX(0);
        mTextPaint.setTextScaleX(1.2f);
        canvas.drawText("水平缩放：setTextScaleX", 0, 1210, mTextPaint);

        mTextPaint.setLetterSpacing(0.2f);
        mTextPaint.setTextScaleX(1);
        canvas.drawText("setLetterSpacing(0.2f)", 0, 1280, mTextPaint);

        mTextPaint.setLetterSpacing(0.0f);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("(Paint.Align.LEFT)", 500, 1350, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("(Paint.Align.CENTER)", 500, 1420, mTextPaint);
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("(Paint.Align.RIGHT)", 500, 1490 , mTextPaint);

        mTextPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("获得行高：getFontSpacing", 0, 1600, mTextPaint);
        canvas.drawText("获得行高：getFontSpacing", 0, 1600 + mTextPaint.getFontSpacing(), mTextPaint);


        float offsetX = 10;
        float offsetY = 1730;
        mTextPaint.setStyle(Paint.Style.FILL);
        String text4 = "获取文字的显示范围";
        canvas.drawText(text4, offsetX, offsetY, mTextPaint);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text4, 0, text4.length(), bounds);
        bounds.left += offsetX;
        bounds.top += offsetY;
        bounds.right += offsetX;
        bounds.bottom += offsetY;
        mTextPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(bounds, mTextPaint);

        offsetY = 1820;
        String text5 = "获取文字的宽度：measureText";
        canvas.drawText(text5, offsetX, offsetY, mTextPaint);
        float textWidth = mTextPaint.measureText(text5);
        canvas.drawLine(offsetX, offsetY, offsetX + textWidth, offsetY, mTextPaint);
        //measureText() 测出来的宽度总是比 getTextBounds() 大一点

//        mTextPaint.setStrokeWidth(20);
        mTextPaint.setColor(Color.RED);
        canvas.drawLine(0, 1900, 800, 1900, mTextPaint);
        canvas.drawLine(0, 2000, 800, 2000, mTextPaint);
        canvas.drawLine(0, 2100, 800, 2100, mTextPaint);

        canvas.drawText("测试数据abfgj", 0, 2000 + (bounds.bottom - bounds.top)/2, mTextPaint);

        //文字的y轴坐标
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float y = 2000 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        canvas.drawText("测试数据abfgj", 400, y, mTextPaint);


        Path pathRect = new Path();
        RectF rect2 =  new RectF(20, 2200, 300, 2400);
        float radii[] ={10,15,20,25,30,35,40,45};
        pathRect.addRoundRect(rect2, radii, Path.Direction.CCW);

        canvas.drawPath(pathRect, mPaint);
    }
}
