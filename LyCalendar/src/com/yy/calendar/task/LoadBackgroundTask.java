package com.yy.calendar.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.AsyncTask;
import android.view.WindowManager;

public class LoadBackgroundTask extends AsyncTask<Void, Void, Bitmap> {
    public static interface Callback {
        void onEnd(Bitmap bitmap);
    }

    private int month;
    private int width;
    private int height;
    private Callback callback;
    private Context context;

    public LoadBackgroundTask(Context context, int month, int width, int height, Callback callback) {
        this.month = month;
        this.width = width <= 0 ? 200 : width;
        this.height = height <= 0 ? 200 : height;
        this.callback = callback;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        String text = String.valueOf(month);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        paint.setTextSize(wm.getDefaultDisplay().getWidth() / 2);
        paint.setColor(0x5FAAAAAA);
        paint.setStyle(Style.FILL);
        paint.setTextAlign(Align.CENTER);
        FontMetrics fontMetrics = paint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        float textBaseY = height - (height - fontHeight) / 2 - fontMetrics.bottom;
        canvas.drawText(text, (width) / 2, textBaseY, paint);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        callback.onEnd(bitmap);
    }
}
