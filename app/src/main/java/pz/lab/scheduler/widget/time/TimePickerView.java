package pz.lab.scheduler.widget.time;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Date;

import pz.lab.scheduler.R;
import pz.lab.scheduler.widget.time.event.TimePickerEvent;
import pz.lab.scheduler.widget.time.event.TimePickerEventListener;

/**
 * Created by Hakus on 2017-01-06.
 */

public class TimePickerView extends FrameLayout implements TimePickerEventListener, View.OnTouchListener{
    private TextView hour, minute, amView, pmView;
    private TimePickerModel model;
    private RadialPickerView radialPicker;
    public TimePickerView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.time_picker_view_layout, this, true);
        model = new TimePickerModel(new Date());
        model.addTimePickerListener(this);
        hour = (TextView) findViewById(R.id.hourView);
        minute = (TextView) findViewById(R.id.minuteView);
        radialPicker = (RadialPickerView) findViewById(R.id.radialPicker);
        amView = (TextView) findViewById(R.id.amView);
        amView.setOnTouchListener(this);

        pmView = (TextView) findViewById(R.id.pmView);
        pmView.setOnTouchListener(this);

        radialPicker.setTimeModel(model);
        hour.setOnTouchListener(this);
        minute.setOnTouchListener(this);
        updateLabels();
        changeSelectedTimePartLabelHighlight(TimePickerEvent.TimePart.HOUR);
        changeSelectedDayPartLabelHighlight(model.getDayPart());
    }

    public void setTime(Date date) {
        model.setTime(date);
    }

    public void setTime(long time) {
        model.setTime(time);
    }

    public long getTime() {
        return model.getTime();
    }

    public void removeTimePickerListener(TimePickerEventListener listener) {
        model.removeTimePickerListener(listener);
    }

    public void addTimePickerListener(TimePickerEventListener listener) {
        model.addTimePickerListener(listener);
    }

    @Override
    public void onTimeSelectionChange(TimePickerEvent event) {
        updateLabels();
        changeSelectedTimePartLabelHighlight(event.getSelectedTimePart());
    }

    @Override
    public void onDayPartChange(TimePickerEvent event) {
        updateLabels();
    }

    private void updateLabels(){
        int hour = model.getHour();
        if(hour==0&&model.getDayPart()== DayPart.PM)
           model.setHour(12);
        else if(hour==12&&model.getDayPart()== DayPart.AM)
           model.setHour(0);
        this.hour.setText(String.format("%1$02d", model.getHour()));
        minute.setText(String.format("%1$02d", model.getMinute()));
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==hour){
            changeSelectedTimePartLabelHighlight(TimePickerEvent.TimePart.HOUR);
            radialPicker.setMinutes(false);
        }else if(v==minute){
            radialPicker.setMinutes(true);
            changeSelectedTimePartLabelHighlight(TimePickerEvent.TimePart.MINUTE);
        }else if(v==amView){
            amView.setTextColor(getResources().getColor(R.color.pickerSelector));
            pmView.setTextColor(getResources().getColor(R.color.labelDefault));
            model.setDayPart(DayPart.AM);
            model.fireDayPartChange(DayPart.AM);
        }
        else if(v==pmView){
            pmView.setTextColor(getResources().getColor(R.color.pickerSelector));
            amView.setTextColor(getResources().getColor(R.color.labelDefault));
            model.setDayPart(DayPart.PM);
            model.fireDayPartChange(DayPart.PM);
        }
        return true;
    }

    private void changeSelectedTimePartLabelHighlight(TimePickerEvent.TimePart timePart){
        if(timePart == TimePickerEvent.TimePart.HOUR){
            hour.setTextColor(getResources().getColor(R.color.pickerSelector));
            minute.setTextColor(getResources().getColor(R.color.labelDefault));
        }
        else{
            minute.setTextColor(getResources().getColor(R.color.pickerSelector));
            hour.setTextColor(getResources().getColor(R.color.labelDefault));
        }
    }

    private void changeSelectedDayPartLabelHighlight(DayPart dayPart){
        if(dayPart==DayPart.AM){
            amView.setTextColor(getResources().getColor(R.color.pickerSelector));
            pmView.setTextColor(getResources().getColor(R.color.labelDefault));
        }else{
            pmView.setTextColor(getResources().getColor(R.color.pickerSelector));
            amView.setTextColor(getResources().getColor(R.color.labelDefault));
        }
    }
}
