package com.example.heros.view.datepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.example.heros.R;


public class WeekDayView extends View {
    //上横线颜色
    private int mTopLineColor = Color.parseColor("#CCE4F2");
    //下横线颜色
    private int mBottomLineColor = Color.parseColor("#CCE4F2");
    //周一到周五的颜色
    private int mWeedayColor = Color.parseColor("#1FC2F3");
    //周六、周日的颜色
    private int mWeekendColor = Color.parseColor("#fa4451");
    //线的宽度
    private int mStrokeWidth = 4;
    private int mWeekSize = 14;
    private Paint paint;
    private DisplayMetrics mDisplayMetrics;
    private String[] weekString = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    int color;

    public WeekDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WeekDayView);
//		 mWeekSize = ta.getDimensionPixelSize((int) TypedValue.applyDimension(  
//                 TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()),25);  
        mWeekSize = (int) ta.getDimension(R.styleable.WeekDayView_text_size, 16);
        color = ta.getColor(R.styleable.WeekDayView_text_color, 0);
        Log.e("hyw", "mWeekSize:" + mWeekSize + "     color:" + color);
        ta.recycle();
        mDisplayMetrics = getResources().getDisplayMetrics();
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {
            paint.setTextSize(mWeekSize);
            Rect mBound = new Rect();
            String mTitleText = "日";
            paint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            heightSize = desired;
            Log.e("hyw", "AT_MOST  heightSize:" + heightSize);
        } else {
            Log.e("hyw", "  heightSize:" + heightSize);
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = mDisplayMetrics.densityDpi * 300;
            Log.e("hyw", "AT_MOST  widthSize:" + widthSize);
        } else {
            Log.e("hyw", "  widthSize:" + widthSize);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        //进行画上下线
        paint.setStyle(Style.STROKE);
        paint.setColor(mTopLineColor);
        paint.setStrokeWidth(mStrokeWidth);
        canvas.drawLine(0, 0, width, 0, paint);

        //画下横线
        paint.setColor(mBottomLineColor);
        canvas.drawLine(0, height, width, height, paint);

        paint.setStyle(Style.FILL);
        paint.setTextSize(mWeekSize);
        paint.setAntiAlias(true);
        int columnWidth = width / 7;
        Log.e("hyw", "paint settext:" + mWeekSize * mDisplayMetrics.scaledDensity);
        for (int i = 0; i < weekString.length; i++) {
            String text = weekString[i];
            int fontWidth = (int) paint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
            int startY = (int) (height / 2 - (paint.ascent() + paint.descent()) / 2);
            if (text.indexOf("日") > -1 || text.indexOf("六") > -1) {
                paint.setColor(mWeekendColor);
            } else {
                paint.setColor(mWeedayColor);
            }
            canvas.drawText(text, startX, startY, paint);
        }
    }


    /**
     * 设置字体的大小
     *
     * @param mWeekSize
     */
    public void setmWeekSize(int mWeekSize) {
        this.mWeekSize = mWeekSize;
    }


}
