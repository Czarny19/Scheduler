package pz.lab.scheduler.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import pz.lab.scheduler.R;

/**
 * Created by Hakus on 2016-12-24.
 */

public class RadialPickerView extends View{
    private static final int[] HOURS_NUMBERS_24 = {0, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private static final int[] HOURS_NUMBERS_12 = {12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static final String[] HOURS_12_TEXT = new String[12];
    private static final int[] MINUTES_NUMBERS = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
    private float cos[] = new float[12], sin[] = new float[12];
    private int centerX, centerY, width, height;
    private int textInsets, hourTextSize;
    private float radius;
    private Paint circlePaint = new Paint(), hour12Paint = new Paint();
    private static final String TAG = "RadialPicker";
    public RadialPickerView(Context context) {
        super(context);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.RED);

        hour12Paint.setAntiAlias(true);
        hour12Paint.setTextSize(50);
        hour12Paint.setColor(Color.BLACK);
        calculateConstans();
        convertToText();
        textInsets = getResources().getDimensionPixelSize(R.dimen.timepicker_text_inset_normal);
        hourTextSize = getResources().getDimensionPixelSize(R.dimen.timepicker_text_size_normal);
    }

    private void calculateConstans() {
        double dx = (Math.PI*2.0)/12;
        double x = Math.PI / 2.0;

        for (int i = 0; i < 12; i++) {
            cos[i] = (float) Math.cos(x);
            sin[i] = (float) Math.sin(x);
            x += dx;
        }
    }

    private void convertToText(){
        for (int i = 0; i < 12; i++) {
            HOURS_12_TEXT[i] = String.format("%d", HOURS_NUMBERS_12[i]);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawHours(canvas);
    }

    private void drawCircle(Canvas canvas){
        canvas.drawCircle(centerX,centerY, radius, circlePaint);
    }

    private void drawHours(Canvas canvas) {
        float x[] = new float[12], y[] = new float[12];
        calculatePositions(hour12Paint, hourTextSize, radius-textInsets, centerX, centerY, x,y);
        drawText(canvas,hourTextSize,HOURS_12_TEXT,x,y,hour12Paint);
    }
    private void calculatePositions(Paint paint, int textSize, float radius, float xCenter, float yCenter,
                                          float[] x, float[] y) {
        paint.setTextSize(textSize);
        yCenter -= (paint.descent() + paint.ascent()) / 2;
        for (int i = 0; i < 12; i++) {
            x[i] = xCenter - radius * cos[i];
            y[i] = yCenter - radius * sin[i];
        }
    }

    private void drawText(Canvas canvas, int textSize,
                                 String[] texts, float[] textX, float[] textY, Paint paint) {
        for (int i = 0; i < 12; i++) {
            paint.setColor(Color.BLACK);
            canvas.drawText(texts[i], textX[i], textY[i], paint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centerX=(getWidth())/2;
        centerY=(getHeight())/2;
        radius = Math.min(centerX,centerY);
        width=getWidth();
        height=getHeight();
    }
}
