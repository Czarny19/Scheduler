package pz.lab.scheduler.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
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
    private float centerX, centerY;
    private int width, height;
    private int textInsets, hourTextSize;
    private boolean drawSelectionCircle = false;
    private float selectionCircleX, selectionCircleY, hourDistance;
    private float radius;
    private float hourX[] = new float[12], hourY[] = new float[12];
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
    public boolean onTouchEvent(MotionEvent event) {
        calculateSelectorCoords(event.getX(), event.getY());
        drawSelectionCircle=true;
        invalidate();
       return true;
    }

    private void calculateSelectorCoords(float touchX, float touchY){
        int baseDeg;
        if(touchX>=centerX && touchY<=centerY){
            baseDeg = 0;
        }else  if(touchX>=centerX && touchY>centerY){
            baseDeg = 90;
        }else  if(touchX<centerX && touchY>centerY){
            baseDeg = 180;
        }else{
            baseDeg = 270;
        }
        double touchDeg = baseDeg + ((baseDeg/90)%2==0?-1:1)*( ((baseDeg/90)%2==0?-90:0) + Math.toDegrees(Math.atan( Math.abs(touchY-centerY)/Math.abs(touchX-centerX))) );
        int selectedHour = (int) Math.round(touchDeg/30)%12;
        selectionCircleX=hourX[selectedHour];
        selectionCircleY=hourY[selectedHour];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        if (drawSelectionCircle)
            drawSelectionCircle(canvas);
        drawHours(canvas);


    }

    private void drawCircle(Canvas canvas){
        canvas.drawCircle(centerX,centerY, radius, circlePaint);
    }

    private void drawHours(Canvas canvas) {
        drawText(canvas,hourTextSize,HOURS_12_TEXT,hourX,hourY,hour12Paint);
    }

    private void drawSelectionCircle(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(selectionCircleX,selectionCircleY,25, paint );
    }

    private void calculateHoursPositions(Paint paint, int textSize, float radius, float xCenter, float yCenter,
                                          float[] x, float[] y) {
        paint.setTextSize(textSize);
        yCenter -= (paint.descent() + paint.ascent()) / 2;
        for (int i = 0; i < 12; i++) {
            x[i] = xCenter - radius * cos[i];
            y[i] = yCenter - radius * sin[i];
        }
        float dfx = x[2]-x[1],
                dfy = y[2]-y[1];
        hourDistance = (float) (Math.sqrt(Math.pow(dfx,2)+Math.pow(dfy,2))/2);
    }

    private void drawText(Canvas canvas, int textSize,
                                 String[] texts, float[] textX, float[] textY, Paint paint) {
        paint.setTextSize(textSize);
        for (int i = 0; i < 12; i++) {
            paint.setColor(Color.BLACK);
            canvas.drawText(texts[i], textX[i], textY[i], paint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centerX=(getWidth())/2.0f;
        centerY=(getHeight())/2.0f;
        radius = Math.min(centerX,centerY);
        width=getWidth();
        height=getHeight();
        calculateHoursPositions(hour12Paint, hourTextSize, radius-textInsets, centerX, centerY, hourX,hourY);
    }
}
