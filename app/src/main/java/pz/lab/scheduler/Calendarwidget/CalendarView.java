package pz.lab.scheduler.CalendarWidget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import pz.lab.scheduler.CalendarWidget.event.DayPickerEvent;
import pz.lab.scheduler.CalendarWidget.event.DayPickerListener;
import pz.lab.scheduler.R;

public class CalendarView extends View{

    // static final int[] monthDayNumber ={31,28,31,30,31,30,31,31,30,31,30,31};
    //private static final String[] days = {"PN","WT","SR","CZ","PT","SB","ND"};
    //private static final String[] months = {"Styczen","Luty","Marzec","Kwiecien","Maj","Czerwiec","Lipiec","Sierpien","Wrzesien","Pazdziernik","Listopad","Grudzien"};
    private Date[] lista={new Date(2017-1900,1,1),new Date(2017-1900,1,1),new Date(2017-1900,1,13)};
    private Date[] lista1={new Date(2017-1900,2,5),new Date(2017-1900,2,3),new Date(2017-1900,2,15)};

    private static final String[] DAY_TEXT = new String[31];
    private int tasksDays[]=new int[31];

    private String[]days,months;
    private int[] monthDayNumber;
    private static int topHeigh=150;
    private int widthBox, heightBox, width, height;
    private float x1,x2,y1,y2;
    public int currentDay=0,currentMonth=0,currentYear=0,firstDay=0,month=0,year=0,day=0,selectedDay=0;
    private int  hourTextSize,yi=0,xi=0;
    private Date today,date,selectedDayDate= new Date();
    public boolean model;
    public DayPickerModel dayModel;

    private Paint boxPaint = new Paint(), dayTextPaint = new Paint(),taskPaint=new Paint();
    float x[] = new float[7], y[] = new float[7];
    private static final String TAG = "Calendar";
    private List<DayPickerListener> listeners;

    public CalendarView(Context context, AttributeSet atribSet) {

        super(context,atribSet);
        boxPaint.setAntiAlias(true);
        boxPaint.setColor(Color.BLUE);
        dayTextPaint.setAntiAlias(true);
        dayTextPaint.setTextSize(50);
        dayTextPaint.setColor(Color.BLACK);
        taskPaint.setColor(Color.	rgb(255, 128, 159));
        days = context.getResources().getStringArray(R.array.short_week_days);
        months = context.getResources().getStringArray(R.array.months);
        monthDayNumber = context.getResources().getIntArray(R.array.months_day_number);
        firstDayDate();
        model=false;
        convertToText();
        hourTextSize = getResources().getDimensionPixelSize(R.dimen.timepicker_text_size_normal);
        listeners = new ArrayList<>();
    }

    public void setDayPickerModel(DayPickerModel timeModel) {
        this.dayModel = timeModel;
        setModel(false);
    }

    public void setModel(boolean model) {
        this.model = model;
        invalidate();
    }

    public void addTimePickerListener(DayPickerListener listener){
        listeners.add(listener);
    }

    public void removeTimePickerListener(DayPickerListener listener){
        listeners.remove(listener);
    }

    protected void fireTimeChange(Date selectedDate){
        DayPickerEvent event = new DayPickerEvent();
        event.setSelectedDay(selectedDate);
        for (DayPickerListener l : listeners) {
            l.onDaySelectionChange(event);
        }
    }

    public Date getDate(){
        return date;
    }

    public boolean onTouchEvent(MotionEvent event) {
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
                if (diffy<-heightBox){
                    changedate(1);
                }
                else if(diffy>heightBox){
                    changedate(-1);
                }
                daySelect(diffy,diffx);
                break;}
        }
        return true;
    }

    public void daySelect(float diffy, float diffx){
        yi=0;xi=0;
        if(Math.abs(diffy)<heightBox&&Math.abs(diffx)<widthBox) {
            for(int i=0;i<7;i++){
                if(x2>x[i]){
                    xi=i;
                }
            }for(int i=0;i<7;i++){
                if(y2>y[i]){
                    yi=i;
                }
            }
            selectedDay=yi*7+xi+1-firstDay;
            selectedDayDate=new Date(year-1900,month,selectedDay);
            if(selectedDay<1){changedate(-1);invalidate();}
            else {
                if (selectedDay > monthDayNumber[month]) {
                    changedate(1);
                    invalidate();
                } else {
                    fireTimeChange(selectedDayDate);
                }
            }
        }
    }

    public void getTasksDays(int c){
        for(int i=0;i<31;i++)tasksDays[i]=0;
        Calendar cal=Calendar.getInstance();
        for(int i=0;i<lista.length;i++){
            if(c==0) cal.setTime(lista[i]);
            else cal.setTime(lista1[i]);
           tasksDays[cal.get(Calendar.DATE)-1]=1;
        }
    }

    public void setInf(Date dat){
        Calendar tmp=Calendar.getInstance();
        tmp.setTime(dat);
        currentYear= tmp.get(Calendar.YEAR);
        currentMonth= tmp.get(Calendar.MONTH);
        currentDay= tmp.get(Calendar.DATE);
    }

    public void setMonthInf(Date dat){
        Calendar tmp=Calendar.getInstance();
        tmp.setTime(dat);
        year= tmp.get(Calendar.YEAR);
        month= tmp.get(Calendar.MONTH);
        day= tmp.get(Calendar.DATE);
        firstDay=(5+tmp.get(Calendar.DAY_OF_WEEK))%7;
        if(month==1&&year%4==0){
            monthDayNumber[1]=29;
        }
        else monthDayNumber[1]=28;
        getTasksDays(month);

    }

    private void firstDayDate(){

        today = new Date();
        setInf(today);
        date= new Date(currentYear-1900,currentMonth,1);
        setMonthInf(date);

    };

    public void changedate(int side){
        date.setMonth(month+side);
        setMonthInf(date);
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
        //drawTask(canvas,x,y,firstDay,month,lista);
        canvas.drawText(String.format("%d",selectedDay), 80, 80, dayTextPaint);
        canvas.drawText(selectedDayDate.toString(), 80, 1300, dayTextPaint);
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
            if(i>= monthDayNumber[monthNr]+day1){
                boxPaint.setColor(Color.rgb(188,192,228));
            }
            if(i%7==0){j++;}
            if(i-(day1-1)==currentDay&&monthNr==currentMonth&& year==currentYear)
                boxPaint.setColor(Color.rgb(11,192,228));
            canvas.drawRect(textX[i%7],textY[j],textX[i%7]+widthBox,textY[j]+heightBox,boxPaint);
        }

    }

    private void drawTxt(Canvas canvas) {
        drawTextHeader(canvas, days,x,y,dayTextPaint);
        drawText(canvas,hourTextSize,DAY_TEXT,x,y,dayTextPaint,firstDay,month);
    }

    private void calculatePositions(Paint paint, int textSize, float boxWidth, float boxHeight, float widthX, float heightY, float[] x, float[] y) {
        paint.setTextSize(textSize);
        for (int i = 0; i < 7; i++) {
            x[i]=10+i*(boxWidth+5);
            y[i]=topHeigh+i*(boxHeight+5);
        }
    }

    private void drawTextHeader(Canvas canvas, String[] texts, float[] textX, float[] textY, Paint paint) {
        dayTextPaint.setTextSize(80);
        paint.setColor(Color.rgb(60,50,100));
        canvas.drawText(year + " " + months[month] ,30 , 60,paint);
        for(int i=0;i<7;i++){
            dayTextPaint.setTextSize(70);
            paint.setColor(Color.rgb(7,19,122));
            canvas.drawText(texts[i], textX[i]+(widthBox/2)-45, textY[0]-10,paint);
        }
    }

    private void drawText(Canvas canvas, int textSize,String[] texts, float[] textX, float[] textY, Paint paint,int day1,int monthNr) {
        int x=0, j=-1,midleX=0,flaga=0,day=day1;
        for (int i = 0; i < 42; i++) {
            if(day!=0){
                if (monthNr == 0) {
                    x = monthDayNumber[11] - day;
                    day--;
                }
                else {
                    x = monthDayNumber[monthNr - 1] - day;
                    day--;
                }
                if(day==0)
                {flaga=1;}
            }

            else if(x>= monthDayNumber[monthNr]&&flaga==0){
                x=0;
                flaga=2;
            }
           else if(flaga==1){
                x=0;
                flaga=0;
            }
            if(i%7==0){j++;}
            paint.setColor(Color.WHITE);

            if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
                dayTextPaint.setTextSize(heightBox/2-10);
                if(x<9)midleX=widthBox/2-(int)paint.getTextSize()/4-5;
                else midleX=widthBox/2-(int)paint.getTextSize()/2-7;
            } else{
                dayTextPaint.setTextSize(heightBox-10);
                if(x<9)midleX=(int)paint.getTextSize()/2-5;
                else midleX=(int)paint.getTextSize()/4-7;
            }
            canvas.drawText(texts[x], textX[i%7]+ midleX, textY[j]+paint.getTextSize()-5, paint);

            if(tasksDays[x]==1&&flaga==0){
                    if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
                        canvas.drawCircle(textX[i % 7] + widthBox / 2, textY[j] + heightBox * 3 / 4, paint.getTextSize() / 4, taskPaint);
                    }else canvas.drawCircle(textX[i % 7] + widthBox *3 / 4, textY[j] + heightBox  / 2, paint.getTextSize() / 4, taskPaint);
                }
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
