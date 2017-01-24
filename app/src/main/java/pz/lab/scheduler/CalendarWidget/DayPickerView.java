package pz.lab.scheduler.CalendarWidget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.CalendarWidget.event.DayPickerEvent;
import pz.lab.scheduler.CalendarWidget.event.DayPickerListener;
import pz.lab.scheduler.R;



public class DayPickerView extends FrameLayout implements DayPickerListener, View.OnTouchListener{
    private ToggleButton button;
    private DayPickerModel model;
    private CalendarView calendar;
    private View week;
    private DayAdapter ea;
    float x1=0,x2=0;
    private ListView list;
    private Context context;
    private Date date;
    private String[]days,months;


    public DayPickerView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        this.context=context;
        days = this.context.getResources().getStringArray(R.array.week_days);
        months = this.context.getResources().getStringArray(R.array.months);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view_layout, this, true);

        button = (ToggleButton) findViewById(R.id.toggleButton);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick();
            }
        });

        model = new DayPickerModel(false);
        model.addTimePickerListener(this);
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setDayPickerModel(model);
        list=(ListView)findViewById(R.id.weekList);
        week= (View)findViewById(R.id.week);
        week.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               changeEvent(event);
                return true;
            }
        });
    }

    public void changeEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x1 = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                x2 = event.getX();
               //weekChange(); //to nie dziala tu
                break;
            }
        }
    }

    public void weekChange(){
        if(x2-x1<-500){
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            date=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)+7);
            loadList(date);}
        if(x2-x1>500){
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            date=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)-7);
            loadList(date);}
    }

    public void buttonClick() {
            if(button.isChecked()){
                calendar.setVisibility(View.GONE);
                week.setVisibility((View.VISIBLE));
                model.setModel(true);
                date=calendar.getDate();
                loadList(date);

            }
            else{
                calendar.setVisibility(View.VISIBLE);
                model.setModel(false);
                week.setVisibility((View.GONE));
            }
        }

    private void loadList(Date date) {
        int day1;
        Calendar cal=Calendar.getInstance();
        Calendar cal2=Calendar.getInstance();
        cal.setTime(new Date());
        cal.setTime(date);

        Date dat1,dat2;
        if(cal.get(Calendar.MONTH)==cal2.get(Calendar.MONTH)){
            day1=(5+cal2.get(Calendar.DAY_OF_WEEK))%7;
            dat1=new Date(cal2.get(Calendar.YEAR)-1900,cal2.get(Calendar.MONTH),cal2.get(Calendar.DATE)-day1);
        }
        else{
            day1=(5+cal.get(Calendar.DAY_OF_WEEK))%7;
            dat1=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)-day1);
            dat2=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)+(6-day1));
        }
        ea= new DayAdapter((Activity) context,dat1,days,months);
        list.setAdapter(ea);
    }

    public void removeTimePickerListener(DayPickerListener listener) {
        model.removeTimePickerListener(listener);
    }

    public void addTimePickerListener(DayPickerListener listener) {
        model.addTimePickerListener(listener);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
       /* if(v==hour){
            changeSelectedTimePartLabelHighlight(TimePickerEvent.TimePart.HOUR);
            radialPicker.setMinutes(false);
        }else if(v==minute){
            radialPicker.setMinutes(true);
            changeSelectedTimePartLabelHighlight(TimePickerEvent.TimePart.MINUTE);
        }else if(v==amView){
            amView.setTextColor(getResources().getColor(R.color.pickerSelector));
            pmView.setTextColor(getResources().getColor(R.color.labelDefault));
            model.setDayPart(DayPart.AM);
            model.fireModelChange(DayPart.AM);
        }
        else if(v==pmView){
            pmView.setTextColor(getResources().getColor(R.color.pickerSelector));
            amView.setTextColor(getResources().getColor(R.color.labelDefault));
            model.setDayPart(DayPart.PM);
            model.fireDayPartChange(DayPart.PM);
        }*/
        return true;
    }

    @Override
    public void onDaySelectionChange(DayPickerEvent event) {
    }

    @Override
    public void onModelSelectionChange(DayPickerEvent event) {
    }


}
