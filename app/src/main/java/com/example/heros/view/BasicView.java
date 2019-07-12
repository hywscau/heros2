package com.example.heros.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.heros.R;

public class BasicView extends View {

    private Paint mPaint;
    private Path mPath;

    public BasicView(Context context) {
        super(context);

    }

    public BasicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public BasicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(60); // 线条宽度为 20 像素
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setBackgroundColor(Color.parseColor("#333333"));

        drawGraphics(canvas);
        drawPath(canvas);
        drawbitmap(canvas);
        drawText(canvas);
    }

    private void drawGraphics(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(100, 100, 100, mPaint);

        canvas.drawRect(300, 20, 500, 220, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(550, 100, mPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(620, 100, mPaint);

        mPaint.setColor(Color.BLUE);
        float[] points = {100, 100, 700, 100, 780, 100};
        // 绘制三个点： (100, 100) (700, 100) (780, 100)
        canvas.drawPoints(points, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawOval(20, 250, 320, 450, mPaint);

        canvas.drawLine(350, 350, 550, 350, mPaint);

        float[] lines = {600, 300, 800, 300, 600, 400, 800, 400};
        canvas.drawLines(lines, mPaint);

        canvas.drawRoundRect(20, 500, 300, 700, 50, 50, mPaint);


        canvas.drawArc(350, 500, 550, 700, -110, 100, true, mPaint); // 绘制扇形
        canvas.drawArc(600, 500, 800, 700, 20, 140, false, mPaint); //

    }

    private void drawPath(Canvas canvas) {
        mPath.addArc(20, 720, 220, 920, -225, 225);
        mPath.arcTo(220, 720, 420, 920, -180, 225, false);
        mPath.lineTo(220, 1062);
        mPath.close();


        mPath.moveTo(500, 750);
        mPath.lineTo(600, 850); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
        mPath.lineTo(500, 850);
        mPath.rLineTo(50, 100);


        mPath.addCircle(150,1200,100, Path.Direction.CW);
        mPath.addCircle(300,1200,100, Path.Direction.CW);

        canvas.drawPath(mPath, mPaint);


        mPaint.setStyle(Paint.Style.FILL);
        Path path2 = new Path();
        path2.setFillType(Path.FillType.EVEN_ODD);
//        path2.setFillType(Path.FillType.INVERSE_EVEN_ODD);
//        path2.setFillType(Path.FillType.WINDING);
        path2.addCircle(150,1200,100, Path.Direction.CW);
        path2.addCircle(300,1200,100, Path.Direction.CW);
        canvas.drawPath(path2, mPaint);

        /** EVEN_ODD 和 WINDING 的原理
         EVEN_ODD
         即 even-odd rule （奇偶原则,偶数外部）：对于平面中的任意一点，向任意方向射出一条射线，这条射线和图形相交的次数（相交才算，相切不算哦）
         如果是奇数，则这个点被认为在图形内部，是要被涂色的区域

         WINDING
         即 non-zero winding rule （非零环绕数原则，0外部）：首先，它需要你图形中的所有线条都是有绘制方向的：
         然后，同样是从平面中的点向任意方向射出一条射线，但计算规则不一样：以 0 为初始值，对于射线和图形的所有交点，遇到每个顺时针的交点（
         图形从射线的左边向右穿过）把结果加 1，遇到每个逆时针的交点（图形从射线的右边向左穿过）把结果减 1，最终把所有的交点都算上，
         得到的结果如果不是 0，则认为这个点在图形内部，是要被涂色的区域；如果是 0，则认为这个点在图形外部，是不被涂色的区域。
         **/
    }

    private void drawbitmap(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.in_icon);
        canvas.drawBitmap(bitmap, 500, 1200, mPaint);
        canvas.drawBitmap(bitmap, 500, 1200, mPaint);
    }

    private void drawText(Canvas canvas) {
        mPaint.setTextSize(50);
        canvas.drawText("测试文字", 650, 1200, mPaint);
    }

}

