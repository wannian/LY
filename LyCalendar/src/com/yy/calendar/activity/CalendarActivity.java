package com.yy.calendar.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.yy.calendar.R;

public class CalendarActivity extends Activity implements OnClickListener {

    private Button controlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controlView = (Button) findViewById(R.id.control_view);
        controlView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.control_view:
                controlView.setVisibility(View.GONE);
                addControlFragment();
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
}
