package com.yy.calendar.view;

import android.view.ViewGroup;

public interface AnimationInterface<E> {
    public static interface Callback<E> {
        void onAnimationEnd(E e);
    }

    void startNextAnimation(ViewGroup view, E e);

    void startPrevAnimation(ViewGroup view, E e);
}
