package pz.lab.scheduler.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.lang.Math.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pz.lab.scheduler.R;

/**
 * Created by Hakus on 2016-12-24.
 */

public class Calendar extends View{

    private static final int[] MONTH_DAY_NUMBER ={31,28,31,30,31,30,31,31,30,31,30,31};
    private static final String[] DAYS = {"PN","WT","SR","CZ","PT","SB","ND"};
    private int widthBox, heightBox, width, height;
    private int textInsets, hourTextSize, firstDay,monthDay,month,tmp;
    private static final String[] DAY_TEXT = new String[31];
    private Paint boxPaint = new Paint(), dayTextPaint = new Paint();
    float x[] = new float[7], y[] = new float[7];
    private static final String TAG = "Calendar";

    public Calendar(Context context) {
        super(context);
        boxPaint.setAntiAlias(true);
        boxPaint.setColor(Color.BLUE);
        dayTextPaint.setAntiAlias(true);
        dayTextPaint.setTextSize(50);
        dayTextPaint.setColor(Color.BLACK);
        getdate();
        convertToText();
        textInsets = getResources().getDimensionPixelSize(R.dimen.timepicker_text_inset_normal);
        hourTextSize = getResources().getDimensionPixelSize(R.dimen.timepicker_text_size_normal);
    }

    private void getdate(){
        SimpleDateFormat simpledate= new SimpleDateFormat();
        month=DateFormat.getDateInstance().MONTH_FIELD-2;
        firstDay=DateFormat.getDateInstance().DAY_OF_WEEK_FIELD-4;
        monthDay=DateFormat.getDateInstance().DAY_OF_WEEK_IN_MONTH_FIELD-4;
        firstDay=Math.abs((monthDay%7)-firstDay-1);
        //tmp=Math.abs((monthDay%7)-firstDay-1);
    };

    private void convertToText(){
        for (int i = 0; i < 31; i++) {
            DAY_TEXT[i] = String.format("%d", i+1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas,x,y,firstDay,month);
        drawHours(canvas);
       // canvas.drawText(String.format("%d",month), 20, 1200, dayTextPaint);
       // canvas.drawText(String.format("%d",firstDay), 60, 1200, dayTextPaint);
       // canvas.drawText(String.format("%d",monthDay), 120, 1200, dayTextPaint);
       // canvas.drawText(String.format("%d",tmp), 160, 1200, dayTextPaint);

    }

    private void drawCircle(Canvas canvas, float[] textX, float[] textY, int day1,int monthNr){
        calculatePositions(dayTextPaint, hourTextSize, widthBox, heightBox , width, height, x,y);
        int j=-1;
        if(day1!=0){
            boxPaint.setColor(Color.rgb(188,192,228));
        }
        for (int i = 0; i < 42; i++) {
            if(i>=day1){
                boxPaint.setColor(Color.rgb(91,104,219));
            }
            if(i>=MONTH_DAY_NUMBER[monthNr]+day1){
                boxPaint.setColor(Color.rgb(188,192,228));
            }
            if(i%7==0){j++;}
            canvas.drawRect(textX[i%7],textY[j],textX[i%7]+widthBox,textY[j]+heightBox,boxPaint);
        }

    }

    private void drawHours(Canvas canvas) {
        drawTextHeader(canvas,DAYS,x,y,dayTextPaint);
        drawText(canvas,hourTextSize,DAY_TEXT,x,y,dayTextPaint,firstDay,month);
    }

    private void calculatePositions(Paint paint, int textSize, float boxWidth, float boxHeight, float widthX, float heightY,
                                          float[] x, float[] y) {
        paint.setTextSize(textSize);
        for (int i = 0; i < 7; i++) {
            x[i]=10+i*(boxWidth+5);
            y[i]=70+i*(boxHeight+5);
        }
    }

    private void drawTextHeader(Canvas canvas,
                          String[] texts, float[] textX, float[] textY, Paint paint) {
       for(int i=0;i<7;i++){
           dayTextPaint.setTextSize(70);
           paint.setColor(Color.rgb(7,19,122));
           canvas.drawText(texts[i], textX[i]+(widthBox/2)-45, textY[0]-10,paint);
        }
    }


    private void drawText(Canvas canvas, int textSize,
                                 String[] texts, float[] textX, float[] textY, Paint paint,int day1,int monthNr) {
        int x=0, j=-1;
        int day=day1;
        for (int i = 0; i < 42; i++) {
            if(day!=0){
                if (monthNr == 0) {
                    x = MONTH_DAY_NUMBER[11] - day;
                    day--;
                }
                else {
                    x = MONTH_DAY_NUMBER[monthNr - 1] - day;
                    day--;
                }
            }
            else if(x>=MONTH_DAY_NUMBER[monthNr]){
               x=0;
            }
            dayTextPaint.setTextSize(50);
            paint.setColor(Color.WHITE);
            if(i%7==0){j++;}
            canvas.drawText(texts[x], textX[i%7]+5, textY[j]+textSize+5, paint);
            x++;
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
        width=getWidth();
        height=getHeight();
        widthBox=((width-10)/7)-5;
        heightBox=((height-70)/6)-5;

    }
}
