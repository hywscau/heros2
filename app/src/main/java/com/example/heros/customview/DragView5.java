package com.example.heros.customview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

public class DragView5 extends View {

    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public DragView5(Context context) {
        super(context);
        ininView(context);
    }

    public DragView5(Context context, AttributeSet attrs) {
        super(context, attrs);
        ininView(context);
    }

    public DragView5(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ininView(context);
    }

    private void ininView(Context context) {
        setBackgroundColor(Color.BLUE);
        // 初始化Scroller
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断Scroller是否执行完毕

        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            Log.e("hyw3","mScroller.getCurrX():"+mScroller.getCurrX() +"  mScroller.getCurrY():"+mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        Log.e("hyw","event.getX():"+event.getX()+"  event.getRawX():"+event.getRawX());
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                Log.e("hyw2","offsetX:"+offsetX);
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开时，执行滑动过程
                View viewGroup = ((View) getParent());
                mScroller.startScroll(
                        viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),
                        -viewGroup.getScrollY());
                invalidate();
                Log.e("hyw4","viewGroup.getScrollX():"+viewGroup.getScrollX()+"  viewGroup.getScrollY() :"+viewGroup.getScrollY());
                break;
        }
        return true;
    }
}
