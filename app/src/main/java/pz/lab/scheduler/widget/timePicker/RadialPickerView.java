package pz.lab.scheduler.widget.timePicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import pz.lab.scheduler.R;

/**
 * Created by Hakus on 2016-12-24.
 */

public class RadialPickerView extends View{
    private static final int[] HOURS_NUMBERS_12 = {12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private static final String[] HOURS_12_TEXT = new String[12], MINUTES_TEXT = new String[12];
    private static final int[] MINUTES_NUMBERS = {0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55};
    private float cos[] = new float[60], sin[] = new float[60];
    private float centerX, centerY;
    private boolean minutes = false;
    private float textHeight;
    private int textInsets, hourTextSize;
    private boolean drawSelectionCircle = false;
    private float selectionCircleX, selectionCircleY, labelsDistance, selectorRadius, selectorStroke;
    private float radius;
    private float hourX[] = new float[12], hourY[] = new float[12],
                minuteX[] = new float[60], minuteY[] = new float[60];
    private Paint circlePaint = new Paint(), hour12Paint = new Paint(), selectionPaint = new Paint(), selectionStrokePaint;
    private TimePickerModel timeModel;
    private static final String TAG = "RadialPicker";
    public RadialPickerView(Context context, AttributeSet atribSet) {
        super(context,atribSet);

        circlePaint.setAntiAlias(true);
        circlePaint.setColor(getResources().getColor(R.color.pickerBgDefault));

        setTimeModel(new TimePickerModel());

        selectionPaint.setAntiAlias(true);
        selectionPaint.setColor(getResources().getColor(R.color.pickerSelector));

        selectionStrokePaint = new Paint();
        selectionStrokePaint.setColor(getResources().getColor(R.color.pickerSelectorCenter));

        selectorRadius = getResources().getDimensionPixelSize(R.dimen.timepicker_selector_radius);
        selectorStroke = getResources().getDimensionPixelSize(R.dimen.timepicker_selector_stroke);

        textInsets = getResources().getDimensionPixelSize(R.dimen.timepicker_text_inset_normal);
        hourTextSize = getResources().getDimensionPixelSize(R.dimen.timepicker_text_size_normal);
        hour12Paint.setAntiAlias(true);
        hour12Paint.setTextSize(hourTextSize);
        hour12Paint.setColor(Color.BLACK);
        calculateConstans(60);
        convertToText();

    }

    public TimePickerModel getTimeModel() {
        return timeModel;
    }

    public void setTimeModel(TimePickerModel timeModel) {
        this.timeModel = timeModel;
        readTimeFromModel();
        setMinutes(false);
        drawSelectionCircle=true;
        invalidate();
    }

    private void calculateConstans(int lblsCount) {
        double dx = (Math.PI*2.0)/lblsCount;
        double x = Math.PI / 2.0;

       for (int i = 0; i < lblsCount; i++) {
            cos[i] = (float) Math.cos(x);
            sin[i] = (float) Math.sin(x);
            x += dx;
        }
    }

    private void convertToText(){
        for (int i = 0; i < 12; i++) {
            HOURS_12_TEXT[i] = String.format("%d", HOURS_NUMBERS_12[i]);
            MINUTES_TEXT[i] = String.format("%d", MINUTES_NUMBERS[i]);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        calculateSelectorCoords(event.getX(), event.getY(), minutes?60:12);
        drawSelectionCircle=true;
        invalidate();
        if(!minutes&&(event.getAction()==MotionEvent.ACTION_UP)){
            Handler handler = new Handler(Looper.getMainLooper());
            final Runnable r = new Runnable() {
                public void run() {
                   setMinutes(true);
                   invalidate();
                    timeModel.fireTimeChange(TimePickerEvent.TimePart.MINUTE);
                }
            };
            handler.postDelayed(r, 300);
        }
       return true;
    }

    private void calculateSelectorCoords(float touchX, float touchY, int labelsCount){
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
        int selectedHour = (int) Math.round(touchDeg/(360/labelsCount))%labelsCount;
        if(!minutes) {
            selectionCircleX = hourX[selectedHour];
            selectionCircleY = hourY[selectedHour];
            timeModel.setHour(selectedHour);
            timeModel.fireTimeChange(TimePickerEvent.TimePart.HOUR);
        }
        else{
            selectionCircleX = minuteX[selectedHour];
            selectionCircleY = minuteY[selectedHour];
            timeModel.setMinute(selectedHour);
            timeModel.fireTimeChange(TimePickerEvent.TimePart.MINUTE);
        }
    }

    private void readTimeFromModel(){
        if(minutes){
            selectionCircleX = minuteX[timeModel.getMinute()];
            selectionCircleY = minuteY[timeModel.getMinute()];
        }
        else{

            selectionCircleX = hourX[timeModel.getHour()%12];
            selectionCircleY = hourY[timeModel.getHour()%12];
        }
    }

    public void setMinutes(boolean minutes) {
        this.minutes = minutes;
        readTimeFromModel();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);
        if (drawSelectionCircle)
            drawSelector(canvas);
        if(!minutes)
            drawHours(canvas);
        else
            drawMinutes(canvas);
    }

    private void drawCircle(Canvas canvas){
        canvas.drawCircle(centerX,centerY, radius, circlePaint);
    }

    private void drawHours(Canvas canvas) {
        drawText(canvas,hourTextSize,HOURS_12_TEXT,hourX,hourY,1,hour12Paint);
    }

    private void drawSelector(Canvas canvas){
        canvas.drawLine(centerX,centerY,selectionCircleX,(int)(selectionCircleY-(textHeight/2.0)),selectionPaint);
        canvas.drawCircle(selectionCircleX,(int)(selectionCircleY-(textHeight/2.0)),selectorRadius, selectionPaint);
        canvas.drawCircle(selectionCircleX,(int)(selectionCircleY-(textHeight/2.0)),selectorStroke, selectionStrokePaint);
    }

    private void drawMinutes(Canvas canvas){
        drawText(canvas,hourTextSize,MINUTES_TEXT,minuteX,minuteY,5,hour12Paint);
    }

    private void drawText(Canvas canvas, int textSize,
                                 String[] texts, float[] textX, float[] textY, int indexStep, Paint paint) {
        paint.setTextSize(textSize);

        for (int i = 0; i < 12; i++) {
            paint.setColor(Color.BLACK);
            canvas.drawText(texts[i], textX[i*indexStep]-(paint.measureText(texts[i])/2), textY[i*indexStep], paint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centerX=(getWidth())/2.0f;
        centerY=(getHeight())/2.0f;
        radius = Math.min(centerX,centerY);
        //TODO not pixel density safe!!!
        //if(radius>300)
        //    radius=300;
        Rect bounds = new Rect();
        hour12Paint.getTextBounds("1", 0, 1, bounds);
        textHeight = bounds.height();
        calculatePositions(hour12Paint, hourTextSize, radius-textInsets, centerX, centerY, hourX,hourY);
        calculatePositions(hour12Paint, hourTextSize, radius-textInsets, centerX, centerY, minuteX, minuteY);
    }



    private void calculatePositions(Paint paint, int textSize, float radius, float xCenter, float yCenter,
                                    float[] x, float[] y) {
        paint.setTextSize(textSize);
        yCenter -= (paint.descent() + paint.ascent()) / 2;
        for (int i = 0; i < x.length; i++) {
            x[i] = xCenter - radius * cos[i*(x.length==12?5:1)];
            y[i] = yCenter - radius * sin[i*(x.length==12?5:1)];
        }
        float dfx = x[2]-x[1],
                dfy = y[2]-y[1];
        labelsDistance = (float) (Math.sqrt(Math.pow(dfx,2)+Math.pow(dfy,2))/2);
    }


}
