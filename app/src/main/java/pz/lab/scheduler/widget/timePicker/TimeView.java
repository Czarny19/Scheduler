package pz.lab.scheduler.widget.timePicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import pz.lab.scheduler.R;

/**
 * Created by Hakus on 2017-01-06.
 */

public class TimeView extends FrameLayout implements TimePickerListener, View.OnTouchListener{
    private TextView hour, minute, doubleDot;
    private TimePickerModel model;
    private RadialPickerView radialPicker;
    public TimeView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.time_view_layout, this, true);
        model = new TimePickerModel();
        model.addTimePickerListener(this);
        hour = (TextView) findViewById(R.id.hourView);
        minute = (TextView) findViewById(R.id.minuteView);
        radialPicker = (RadialPickerView) findViewById(R.id.radialPicker);
        doubleDot = (TextView) findViewById(R.id.doubleDotView);

        radialPicker.setTimeModel(model);

        hour.setText("05");
        hour.setOnTouchListener(this);
        minute.setOnTouchListener(this);
        doubleDot.setText(":");
        minute.setText("21");
    }



    @Override
    public void onTimeSelectionChange(TimePickerEvent event) {
            hour.setText(String.format("%1$02d", model.getHour()));
            minute.setText(String.format("%1$02d", model.getMinute()));
            changeSelectedLabelHighlight(event.getSelectedTimePart());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==hour){
            changeSelectedLabelHighlight(TimePickerEvent.TimePart.HOUR);
            radialPicker.setMinutes(false);
        }else if(v==minute){
            radialPicker.setMinutes(true);
            changeSelectedLabelHighlight(TimePickerEvent.TimePart.MINUTE);
        }
        return false;
    }

    private void changeSelectedLabelHighlight(TimePickerEvent.TimePart timePart){
        if(timePart == TimePickerEvent.TimePart.HOUR){
            hour.setTextColor(getResources().getColor(R.color.colorPrimary));
            minute.setTextColor(getResources().getColor(R.color.labelDefault));
        }
        else{
            minute.setTextColor(getResources().getColor(R.color.colorPrimary));
            hour.setTextColor(getResources().getColor(R.color.labelDefault));
        }
    }
}
