package com.yy.calendar.view;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class CalendarTranslateAnimation implements AnimationInterface<Calendar> {

    private static final String TAG = "yy";
    private AnimationInterface.Callback<Calendar> callback;

    public CalendarTranslateAnimation(Context applicationContext, Callback<Calendar> callback) {
        this.callback = callback;
    }

    @Override
    public void startNextAnimation(final ViewGroup view, final Calendar e) {
        final Bitmap bitmap = getCacheBitmap(view);
        if (bitmap == null) {
            return;
        }
        final ImageView imageView = new ImageView(view.getContext());
        imageView.setImageBitmap(bitmap);
        final ViewGroup parent = (ViewGroup) view.getParent();
        parent.addView(imageView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animaiton(e, bitmap, imageView, parent, animation);
    }

    @Override
    public void startPrevAnimation(ViewGroup view, final Calendar e) {
        final Bitmap bitmap = getCacheBitmap(view);
        if (bitmap == null) {
            return;
        }
        final ImageView imageView = new ImageView(view.getContext());
        imageView.setImageBitmap(bitmap);
        final ViewGroup parent = (ViewGroup) view.getParent();
        parent.addView(imageView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animaiton(e, bitmap, imageView, parent, animation);
    }

    private Bitmap getCacheBitmap(ViewGroup v) {
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(Color.WHITE);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e(TAG, "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    private void animaiton(final Calendar e, final Bitmap bitmap, final ImageView imageView, final ViewGroup parent,
            TranslateAnimation animation) {
        animation.setDuration(1000);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                if (callback != null) {
                    callback.onAnimationEnd(e);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                parent.removeView(imageView);
                bitmap.recycle();
            }
        });
        imageView.startAnimation(animation);
    }
}
