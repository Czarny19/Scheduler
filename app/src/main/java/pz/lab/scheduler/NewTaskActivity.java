package pz.lab.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.Calendar;

import pz.lab.scheduler.widget.time.TimePickerModel;

public class NewTaskActivity extends FragmentActivity {
    private CheckBox allDayBox;
    private Button endTimeButton, startTimeButton;
    private TimePickerModel startTimeModel, endTimeModel;
    private long endTime, startTime;
    private static final int END_TIME_CODE = 2, START_TIME_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.new_task_title));
        endTimeButton = (Button) findViewById(R.id.end_time_button);
        startTimeButton = (Button) findViewById(R.id.start_time_button);
        allDayBox = (CheckBox) findViewById(R.id.all_day_checkbox);
        allDayBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    endTimeButton.setEnabled(!isChecked);
                    startTimeButton.setEnabled(!isChecked);
            }
        });

        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(START_TIME_CODE);
            }
        });

        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(END_TIME_CODE);
            }
        });

        startTimeModel = new TimePickerModel();
        endTimeModel = new TimePickerModel();

    }

    private void showTimePicker(int timeCode){
        Intent picker = new Intent(this, TimePickerDialog.class);
        picker.putExtra("returnCode", timeCode);
        startActivityForResult(picker,timeCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == END_TIME_CODE){
            endTime = data.getExtras().getLong("time");
            endTimeButton.setText(String.format("%02d", getHour(endTime))+":"+String.format("%02d", getMinute(endTime)));
        }

        if(requestCode == START_TIME_CODE){
            startTime = data.getExtras().getLong("time");
            startTimeButton.setText(String.format("%02d", getHour(startTime))+":"+String.format("%02d", getMinute(startTime)));
        }
    }

    private int getHour(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private int getMinute(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.MINUTE);
    }
}
