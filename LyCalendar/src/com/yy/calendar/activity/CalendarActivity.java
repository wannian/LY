package com.yy.calendar.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.yy.calendar.R;
import com.yy.calendar.view.MonthView;
import com.yy.calendar.view.CalendarTranslateAnimation;

public class CalendarActivity extends Activity implements OnClickListener {

    private Button controlView;
    private MonthView monthView;
    private ViewGroup controlContainer;

    public static final int CONTROL_STATUS_SHOW_MIDDEL = 0;
    public static final int CONTROL_STATUS_SHOW_LEFT = 1;
    private int controlStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlView = (Button) findViewById(R.id.control_view);
        controlView.setOnClickListener(this);

        monthView = (MonthView) findViewById(R.id.month_view);
        monthView.setAnimationInterface(new CalendarTranslateAnimation(getApplicationContext(), monthView));

        controlContainer = (ViewGroup) findViewById(R.id.control_container);
        addControlFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.control_view:
                onCreateOptionsMenu(null);
                break;
            default:
                break;
        }
    }

    private void addControlFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(getControlId(), ControlFragment.newInstance());
        ft.commitAllowingStateLoss();
    }

    private int getControlId() {
        return R.id.control_container;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (controlStatus == CONTROL_STATUS_SHOW_LEFT) {
            controlStatus = CONTROL_STATUS_SHOW_MIDDEL;
            hideControlContainer();
        } else {
            controlStatus = CONTROL_STATUS_SHOW_LEFT;
            showControlContainer();
        }
        return false;
    }

    private static final String PROP_LIST_WIDTH = "listWidthAnim";

    private void showControlContainer() {
        PropertyValuesHolder values = PropertyValuesHolder.ofInt(PROP_LIST_WIDTH, monthView.getLeft(),
                controlContainer.getWidth());
        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(monthView, values).setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    private void hideControlContainer() {
        PropertyValuesHolder values = PropertyValuesHolder.ofInt(PROP_LIST_WIDTH, monthView.getLeft(), 0);
        final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(monthView, values).setDuration(500);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}
