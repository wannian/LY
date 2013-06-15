package com.yy.calendar.adapter;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.yy.calendar.CalendarUtil;
import com.yy.calendar.CalendarUtil.DayState;
import com.yy.calendar.Day;
import com.yy.calendar.R;
import com.yy.calendar.view.WeekItemView;

public class MonthAdapter extends BaseAdapter {
    private static final int HEAD_SIZE = 7;
    private List<Day> days;
    private String[] textWeekends;
    private Context context;
    private int itemHeight;
    private int weekHeight;
    private int verticalSpacing;
    private GridView gridView;
    private int prevDayColor;
    private int dayColor;
    private int currentDayColor;
    private int checkedDayColor;

    public MonthAdapter(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
        Resources resources = context.getResources();
        textWeekends = resources.getStringArray(R.array.text_weekends);
        itemHeight = resources.getDimensionPixelSize(R.dimen.month_view_item_height);
        weekHeight = resources.getDimensionPixelSize(R.dimen.month_view_item_week_height);
        verticalSpacing = resources.getDimensionPixelSize(R.dimen.month_view_vertical_spacing);
        prevDayColor = resources.getColor(R.color.month_view_item_prev_day_background);
        dayColor = resources.getColor(R.color.month_view_item_day_background_color);
        currentDayColor = resources.getColor(R.color.month_view_item_current_day_background);
        checkedDayColor = resources.getColor(R.color.month_view_item_checked_day_background);
    }

    public MonthAdapter(Context context, GridView gridView, int year, int month) {
        this(context, gridView);
        changeDate(year, month);
    }

    private void changeDate(int year, int month) {
        DayState ds = CalendarUtil.generateDays(year, month);
        days = ds.days;
        ViewGroup.LayoutParams lp = gridView.getLayoutParams();
        lp.height = ds.rowSize * (itemHeight + verticalSpacing) + weekHeight;
        gridView.setLayoutParams(lp);
    }

    public void change(int year, int month) {
        changeDate(year, month);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (days == null ? 0 : days.size()) + HEAD_SIZE;
    }

    @Override
    public Object getItem(int position) {
        if (position < HEAD_SIZE) {
            return null;
        }
        return days.get(position - HEAD_SIZE);
    }

    @Override
    public long getItemId(int position) {
        if (position < HEAD_SIZE) {
            return -1;
        }
        return days.get(position - HEAD_SIZE).getDate();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (position < HEAD_SIZE) {
            if (!(convertView instanceof WeekItemView)) {
                view = LayoutInflater.from(context).inflate(R.layout.month_view_item_week, parent, false);
            }
            TextView weekView = (TextView) view.findViewById(R.id.week);
            weekView.setText(textWeekends[position]);
            return view;
        }

        if (view == null || view instanceof WeekItemView) {
            view = LayoutInflater.from(context).inflate(R.layout.month_view_item, parent, false);
        }
        Day day = days.get(position - HEAD_SIZE);
        int flag = day.getFlag();
        view.setVisibility(View.VISIBLE);
        view.destroyDrawingCache();
        TextView dayView = (TextView) view.findViewById(R.id.day);
        TextView lundarView = (TextView) view.findViewById(R.id.lundar);
        View content = view.findViewById(R.id.content);
        dayView.setText(String.valueOf(day.getDate()));
        lundarView.setText(day.getLundar().getDisplay());
        if (gridView.getCheckedItemPosition() == position) {
            content.setBackgroundColor(checkedDayColor);
        } else {
            if ((flag & Day.FLAG_FILL) != Day.FLAG_FILL) {
                content.setBackgroundColor(prevDayColor);
            } else {
                content.setBackgroundColor(dayColor);
            }
            if (CalendarUtil.isToday(day)) {
                content.setBackgroundColor(currentDayColor);
            }
        }
        return view;
    }
}
