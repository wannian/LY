package com.yy.calendar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.GridView;

import com.yy.calendar.Logging;

public class MonthGridView extends GridView implements OnGestureListener {

    public static interface Callback {
        void prev();

        void next();
    }

    private GestureDetector gestureDetector;
    private Callback callback;

    public MonthGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gestureDetector = new GestureDetector(getContext(), this);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }

    protected void onDrawVerticalScrollBar(Canvas canvas, Drawable scrollBar, int l, int t, int r, int b) {
        Logging.i("onDrawVerticalScrollBar t:" + t + ",r:" + r + "," + isVerticalScrollBarEnabled());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final float x1 = e1.getX();
        final float x2 = e2.getX();
        final float deltaX = x1 - x2;
        Logging.i("onFling:" + x1 + "," + x2 + "," + deltaX);
        if (Math.abs(deltaX) >= 100) {
            if (deltaX > 0) {
                if (callback != null) {
                    callback.next();
                }
            } else if (deltaX < 0) {
                if (callback != null) {
                    callback.prev();
                }
            }
            return true;
        }
        return false;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
