package pz.lab.scheduler.widget.timePicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Date;

import pz.lab.scheduler.R;

/**
 * Created by Hakus on 2017-01-06.
 */

public class TimeView extends FrameLayout implements TimePickerListener, View.OnTouchListener{
    private TextView hour, minute, amView, pmView;
    private TimePickerModel model;
    private RadialPickerView radialPicker;
    public TimeView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.time_view_layout, this, true);
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
        changeSelectedLabelHighlight(TimePickerEvent.TimePart.HOUR);
    }



    @Override
    public void onTimeSelectionChange(TimePickerEvent event) {
            updateLabels();
            changeSelectedLabelHighlight(event.getSelectedTimePart());
    }

    private void updateLabels(){
        int hour = model.getHour();
        if(hour==0&&model.getDayPart()== TimePickerModel.DayPart.PM)
           model.setHour(12);
        else if(hour==12&&model.getDayPart()== TimePickerModel.DayPart.AM)
           model.setHour(0);
        this.hour.setText(String.format("%1$02d", model.getHour()));
        minute.setText(String.format("%1$02d", model.getMinute()));
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==hour){
            changeSelectedLabelHighlight(TimePickerEvent.TimePart.HOUR);
            radialPicker.setMinutes(false);
        }else if(v==minute){
            radialPicker.setMinutes(true);
            changeSelectedLabelHighlight(TimePickerEvent.TimePart.MINUTE);
        }else if(v==amView){
            amView.setTextColor(getResources().getColor(R.color.pickerSelector));
            pmView.setTextColor(getResources().getColor(R.color.labelDefault));
            model.setDayPart(TimePickerModel.DayPart.AM);
            model.fireTimeChange(TimePickerEvent.TimePart.HOUR);
        }
        else if(v==pmView){
            pmView.setTextColor(getResources().getColor(R.color.pickerSelector));
            amView.setTextColor(getResources().getColor(R.color.labelDefault));
            model.setDayPart(TimePickerModel.DayPart.PM);
            model.fireTimeChange(TimePickerEvent.TimePart.HOUR);
        }
        return true;
    }

    private void changeSelectedLabelHighlight(TimePickerEvent.TimePart timePart){
        if(timePart == TimePickerEvent.TimePart.HOUR){
            hour.setTextColor(getResources().getColor(R.color.pickerSelector));
            minute.setTextColor(getResources().getColor(R.color.labelDefault));
        }
        else{
            minute.setTextColor(getResources().getColor(R.color.pickerSelector));
            hour.setTextColor(getResources().getColor(R.color.labelDefault));
        }
    }
}
