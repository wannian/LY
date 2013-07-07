package com.yy.calendar.view;

import java.lang.ref.WeakReference;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yy.calendar.Logging;
import com.yy.calendar.R;
import com.yy.calendar.adapter.MonthAdapter;
import com.yy.calendar.task.LoadBackgroundTask;

public class MonthView extends LinearLayout implements View.OnClickListener, MonthGridView.Callback,
        OnItemClickListener, AnimationInterface.Callback<Calendar> {

    private MonthGridView gridView;
    private MonthAdapter adapter;
    private Button nextMonth;
    private Button preMonth;
    private Calendar currentCalendar;
    private TextView titleView;
    private Button today;
    private Bitmap groundbackNumBitmap;
    private AnimationInterface<Calendar> animationInterface;

    private static final int MSG_BACKGROUND_NUMBER = 1;
    private Handler handler = new MyHandler(this);

    static class MyHandler extends Handler {
        private WeakReference<MonthView> e;

        MyHandler(MonthView e) {
            this.e = new WeakReference<MonthView>(e);
        }

        @Override
        public void handleMessage(Message msg) {
            final MonthView e = this.e.get();
            switch (msg.what) {
                case MSG_BACKGROUND_NUMBER:
                    new LoadBackgroundTask(e.getContext(), Integer.parseInt(msg.obj.toString()), e.gridView.getWidth(),
                            e.gridView.getHeight(), new LoadBackgroundTask.Callback() {
                                @Override
                                public void onEnd(Bitmap bitmap) {
                                    e.releaseBitmap(e.groundbackNumBitmap);
                                    e.groundbackNumBitmap = bitmap;
                                    e.gridView.setBackgroundDrawable(new BitmapDrawable(e.groundbackNumBitmap));
                                }
                            }).execute();
                    break;
                default:
                    break;
            }
        }

    };

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        animationInterface = new CalendarFlyAnimation(getContext(), this);

        nextMonth = (Button) findViewById(R.id.next_month);
        nextMonth.setOnClickListener(this);

        preMonth = (Button) findViewById(R.id.prev_month);
        preMonth.setOnClickListener(this);

        today = (Button) findViewById(R.id.today);
        today.setOnClickListener(this);

        titleView = (TextView) findViewById(R.id.title);

        gridView = (MonthGridView) findViewById(R.id.month_grid_view);
        gridView.setOnItemClickListener(this);
        adapter = new MonthAdapter(getContext(), gridView);
        gridView.setCallback(this);
        gridView.setAdapter(adapter);

        currentCalendar = Calendar.getInstance();
        setTitleDate(currentCalendar);
        change2Date(currentCalendar, false);
    }

    public void setListWidthAnim(int value) {
        int width = getWidth();
        setLeft(value);
        setRight(value + width);
    }

    public void setAnimationInterface(AnimationInterface<Calendar> animationInterface) {
        this.animationInterface = animationInterface;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_month:
                next();
                break;
            case R.id.prev_month:
                prev();
                break;
            case R.id.today:
                today();
                break;
            default:
                break;
        }
    }

    @Override
    public void prev() {
        gridView.clearChoices();
        currentCalendar.add(Calendar.MONTH, -1);
        setTitleDate(currentCalendar);
        change2Date(currentCalendar, true, true);
    }

    @Override
    public void next() {
        gridView.clearChoices();
        currentCalendar.add(Calendar.MONTH, 1);
        setTitleDate(currentCalendar);
        change2Date(currentCalendar, true, false);
    }

    private void today() {
        gridView.clearChoices();
        currentCalendar = Calendar.getInstance();
        setTitleDate(currentCalendar);
        change2Date(currentCalendar, true);
    }

    private void change2Date(final Calendar calendar, boolean anim) {
        if (!anim) {
            change2DateInner(calendar);
            return;
        }
        animationInterface.startNextAnimation(gridView, calendar);
    }

    private void change2Date(final Calendar calendar, boolean anim, boolean before) {
        if (!anim) {
            change2DateInner(calendar);
            return;
        }

        if (before) {
            animationInterface.startPrevAnimation(gridView, calendar);
        } else {
            animationInterface.startNextAnimation(gridView, calendar);
        }
    }

    private void change2DateInner(Calendar calendar) {
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) + 1;
        adapter.change(year, month);
        handler.removeMessages(MSG_BACKGROUND_NUMBER);
        handler.sendMessageDelayed(handler.obtainMessage(MSG_BACKGROUND_NUMBER, month), 250);
    }

    private void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Logging.i("onItemClick:" + position + "," + id + "," + gridView.getWidth() + ','
                + ((GridView) parent).getCheckedItemPosition());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAnimationEnd(Calendar e) {
        gridView.setBackgroundDrawable(null);
        change2DateInner(e);
    }

    private void setTitleDate(Calendar calendar) {
        titleView.setText(DateUtils.formatDateTime(getContext(), calendar.getTimeInMillis(),
                DateUtils.FORMAT_NO_MONTH_DAY));
    }
}
