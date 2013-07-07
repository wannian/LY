package com.yy.calendar.view;

import java.util.Calendar;

import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.yy.calendar.R;

public class CalendarFlyAnimation implements AnimationInterface<Calendar> {
    private AnimationInterface.Callback<Calendar> callback;
    private LayoutAnimationController inNextController;
    private LayoutAnimationController inPrevController;
    private LayoutAnimationController outNextController;
    private LayoutAnimationController outPrevController;

    public CalendarFlyAnimation(Context context, Callback<Calendar> callback) {
        this.callback = callback;
        inNextController = new LayoutAnimationController(
                AnimationUtils.loadAnimation(context, R.anim.anim_fly_next_in), 0.2f);
        outNextController = new LayoutAnimationController(AnimationUtils.loadAnimation(context,
                R.anim.anim_fly_next_out), 0.2f);
        outNextController.setOrder(LayoutAnimationController.ORDER_REVERSE);

        inPrevController = new LayoutAnimationController(
                AnimationUtils.loadAnimation(context, R.anim.anim_fly_prev_in), 0.2f);
        inPrevController.setOrder(LayoutAnimationController.ORDER_REVERSE);
        outPrevController = new LayoutAnimationController(AnimationUtils.loadAnimation(context,
                R.anim.anim_fly_prev_out), 0.2f);
    }

    @Override
    public void startNextAnimation(final ViewGroup view, final Calendar e) {
        startAnimation(view, e, outNextController, inNextController);
    }

    @Override
    public void startPrevAnimation(final ViewGroup view, final Calendar e) {
        startAnimation(view, e, outPrevController, inPrevController);
    }

    public void startAnimation(final ViewGroup view, final Calendar e, final LayoutAnimationController outController,
            final LayoutAnimationController inController) {
        view.setLayoutAnimation(outController);
        view.setLayoutAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (callback != null) {
                    callback.onAnimationEnd(e);
                }
                view.setLayoutAnimation(inController);
                view.setLayoutAnimationListener(null);
                view.startLayoutAnimation();
            }
        });
        view.startLayoutAnimation();
    }
}
