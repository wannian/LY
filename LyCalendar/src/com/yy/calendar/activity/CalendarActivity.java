package com.yy.calendar.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.yy.calendar.R;
import com.yy.calendar.view.FlyAnimationInterface;
import com.yy.calendar.view.MonthView;

public class CalendarActivity extends Activity implements OnClickListener {

    private Button controlView;
    private MonthView monthView;
    private ViewGroup controlContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlView = (Button) findViewById(R.id.control_view);
        controlView.setOnClickListener(this);
        monthView = (MonthView) findViewById(R.id.month_view);
        monthView.setAnimationInterface(new FlyAnimationInterface(getApplicationContext(), monthView));
        controlContainer = (ViewGroup) findViewById(R.id.control_container);
        controlContainer.setVisibility(View.GONE);
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
        if (controlContainer.getVisibility() == View.VISIBLE) {
            controlContainer.setVisibility(View.GONE);
            hideControlContainer();
        } else {
            controlContainer.setVisibility(View.VISIBLE);
            showControlContainer();
        }
        return false;
    }

    private void showControlContainer() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -2,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new OvershootInterpolator());
        controlContainer.startAnimation(animation);
    }

    private void hideControlContainer() {
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation.setDuration(1000);
        animation.setInterpolator(new AnticipateInterpolator());
        controlContainer.startAnimation(animation);
    }
}
