package pz.lab.scheduler.CalendarWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.CalendarWidget.event.DayPickerEvent;
import pz.lab.scheduler.CalendarWidget.event.DayPickerListener;
import pz.lab.scheduler.R;

/**
 * Created by Hakus on 2017-01-06.
 */

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

    public DayPickerView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        this.context=context;
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
                weekChange();
                break;
            }
        }
    }

    public void weekChange(){
        if(x2-x1<-200){
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            date=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)+7);
            loadList(date);}
        if(x2-x1>200){
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            date=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)-7);
            loadList(date);}
    }

    public void buttonClick() {
            if(button.isChecked()){
                calendar.setVisibility(View.VISIBLE);
                week.setVisibility((View.GONE));
            }
            else{
                calendar.setVisibility(View.GONE);
                week.setVisibility((View.VISIBLE));
                date=calendar.getDate();
                loadList(date);
            }
        }

    private void loadList(Date date)
    {   int day1;
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        day1=(5+cal.get(Calendar.DAY_OF_WEEK))%7;
        Date dat1=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)-day1);
        Date dat2=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)+(6-day1));

       // ea=new DayAdapter(context,dat1,dat2);
       // list.setAdapter(ea);
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
