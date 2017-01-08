package pz.lab.scheduler.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.Time;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import pz.lab.scheduler.R;

public class Calendar extends View{

    private static final int[] MONTH_DAY_NUMBER ={31,28,31,30,31,30,31,31,30,31,30,31};
    private static final String[] DAYS = {"PN","WT","SR","CZ","PT","SB","ND"};
    private static int topHeigh=150;
    private static final String[] MONTHS = {"Styczen","Luty","Marzec","Kwiecien","Maj","Czerwiec","Lipiec","Sierpien","Wrzesien","Pazdziernik","Listopad","Grudzien"};
    private int widthBox, heightBox, width, height;
    private int  hourTextSize, firstDay,monthDay,month,year,thisDay,thisMonth,thisYear;
    private float x1,x2,y1,y2;
    private String currentDateandTime;
    private static float X_DIFF=100;
    private static SimpleDateFormat today;
    private static final String[] DAY_TEXT = new String[31];
    private Paint boxPaint = new Paint(), dayTextPaint = new Paint();
    float x[] = new float[7], y[] = new float[7];
    private static final String TAG = "Calendar";

    public Calendar(Context context,AttributeSet atribSet) {
        super(context,atribSet);
        boxPaint.setAntiAlias(true);
        boxPaint.setColor(Color.BLUE);
        dayTextPaint.setAntiAlias(true);
        dayTextPaint.setTextSize(50);
        dayTextPaint.setColor(Color.BLACK);
        getdate();
        convertToText();
        hourTextSize = getResources().getDimensionPixelSize(R.dimen.timepicker_text_size_normal);
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            { y1 = event.getY();
                x1 = event.getX();
                break;}
            case MotionEvent.ACTION_UP:
            {y2 = event.getY();
                x2 = event.getX();
                float diffy=y2-y1;
                float diffx=x2-x1;
                if (diffy<-100)
                {
                    changedate(1);
                }
                if(diffy>100)
                {
                    changedate(-1);
                }
                if(diffy<=heightBox&&diffx<=widthBox){

                }
                break;}
        }
        return true;
    }

    private void getdate(){
    //    today= new SimpleDateFormat("yyyy-MM-dd");
    //    month=today.MONTH_FIELD-2;
    //    firstDay=today.DAY_OF_WEEK_FIELD-4;
    //    monthDay=today.DAY_OF_WEEK_IN_MONTH_FIELD-4;
    //    year=today.YEAR_FIELD;


     //   SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
     //    currentDateandTime = sdf.format(new Date());

        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        thisDay=(today.weekDay+6)%7;
        monthDay=today.monthDay;             // Day of the month (1-31)
        month=thisMonth=today.month;              // Month (0-11)
        year=thisYear=today.year;                // Year
        firstDay=Math.abs((monthDay%7)-thisDay-1);

    };
    public void changedate(int side){
        if(side==-1){
            int tmp;
            if(month==0){month=12;year--;}
            month=(month+side)%12;
            tmp=(MONTH_DAY_NUMBER[month]%7);
            firstDay=(7-(tmp-firstDay))%7;
        }
        else{
            firstDay=(firstDay+MONTH_DAY_NUMBER[month])%7;
            month=(month+side)%12;
            if(month==0)year++;
        }
            invalidate();

    };

    private void convertToText(){
        for (int i = 0; i < 31; i++) {
            DAY_TEXT[i] = String.format("%d", i+1);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBox(canvas,x,y,firstDay,month);
        drawTxt(canvas);
       // canvas.drawText(String.format("%d",today), 20, 1200, dayTextPaint);
       // canvas.drawText(String.format("%d",month), 20, 1200, dayTextPaint);
       // canvas.drawText(String.format("%d",firstDay), 60, 1200, dayTextPaint);
       // canvas.drawText(String.format("%d",monthDay), 120, 1200, dayTextPaint);
        // canvas.drawText(String.format("%d",tmp), 160, 1200, dayTextPaint);

    }

    private void drawBox(Canvas canvas, float[] textX, float[] textY, int day1,int monthNr){
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
            if(i-day1==thisDay+1&&monthNr==thisMonth&& year==thisYear)
                boxPaint.setColor(Color.rgb(11,192,228));
            canvas.drawRect(textX[i%7],textY[j],textX[i%7]+widthBox,textY[j]+heightBox,boxPaint);
        }

    }

    private void drawTxt(Canvas canvas) {
        drawTextHeader(canvas,DAYS,x,y,dayTextPaint);
        drawText(canvas,hourTextSize,DAY_TEXT,x,y,dayTextPaint,firstDay,month);
    }

    private void calculatePositions(Paint paint, int textSize, float boxWidth, float boxHeight, float widthX, float heightY,
                                    float[] x, float[] y) {
        paint.setTextSize(textSize);
        for (int i = 0; i < 7; i++) {
            x[i]=10+i*(boxWidth+5);
            y[i]=topHeigh+i*(boxHeight+5);
        }
    }

    private void drawTextHeader(Canvas canvas,
                                String[] texts, float[] textX, float[] textY, Paint paint) {
        dayTextPaint.setTextSize(80);
        paint.setColor(Color.rgb(60,50,100));
        canvas.drawText(MONTHS[month] + " " +year,30 , 60,paint);
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
        int flaga=0;
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
                if(day==0){flaga=1;}
            }

            else if(x>=MONTH_DAY_NUMBER[monthNr]&&flaga==0){
                x=0;
            }
           else if(flaga==1){
                x=0;
                flaga=0;
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
        heightBox=((height-topHeigh)/6)-5;

    }
}
