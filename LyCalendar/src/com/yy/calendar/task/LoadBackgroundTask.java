package com.yy.calendar.task;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.AsyncTask;

public class LoadBackgroundTask extends AsyncTask<Void, Void, Void> {
    public static interface Callback {
        void onEnd(Bitmap bitmap);
    }

    private int month;
    private int width;
    private int height;
    private Bitmap bitmap;
    private Callback callback;

    public LoadBackgroundTask(int month, int width, int height, Callback callback) {
        this.month = month;
        this.width = width <= 0 ? 200 : width;
        this.height = height <= 0 ? 200 : height;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(Void... params) {
        String text = String.valueOf(month);
        bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(180);
        paint.setColor(0x5faaaaaa);
        paint.setStyle(Style.FILL);
        paint.setTextAlign(Align.CENTER);
        FontMetrics fontMetrics = paint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
        canvas.drawText(text, (width) / 2, textBaseY, paint);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        callback.onEnd(bitmap);
        bitmap = null;
    }
}
