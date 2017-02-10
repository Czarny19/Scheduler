package pz.lab.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.model.Task;

public class NewTaskActivity extends AppCompatActivity {
    private CheckBox allDayBox;
    private Button endTimeButton, startTimeButton;
    private Date selectedDay;
    private EditText taskTitle, taskNote;
    private long endTime, startTime;
    private DatabaseReference databaseReference;
    private static final int END_TIME_CODE = 2, START_TIME_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        selectedDay = (Date) getIntent().getExtras().get("date");
        startTime = selectedDay.getTime();
        prepareInitialEndTime(startTime);



        endTimeButton = (Button) findViewById(R.id.end_time_button);
        startTimeButton = (Button) findViewById(R.id.start_time_button);
        updateEndTimeLabel();
        updateStartTimeLabel();

        taskTitle = (EditText) findViewById(R.id.task_title);
        taskNote = (EditText) findViewById(R.id.note);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_task_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cancel) {
            finish();
        }else if(id==R.id.save){
            if(saveTask())
                finish();
        }
        return false;
    }


    private void showTimePicker(int timeCode){
        Intent picker = new Intent(this, TimePickerDialog.class);
        picker.putExtra("returnCode", timeCode);
        startActivityForResult(picker,timeCode);
    }

    private long prepareInitialEndTime(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, getHour(time)+1);
        calendar.set(Calendar.MINUTE, getMinute((time)));
        return calendar.getTime().getTime();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == END_TIME_CODE){
            endTime = data.getExtras().getLong("time");
            updateEndTimeLabel();
        }

        if(requestCode == START_TIME_CODE){
            startTime = data.getExtras().getLong("time");
            updateStartTimeLabel();
        }
    }

    private void updateEndTimeLabel(){
        endTimeButton.setText(String.format("%02d", getHour(endTime))+":"+String.format("%02d", getMinute(endTime)));
    }

    private void updateStartTimeLabel(){
        startTimeButton.setText(String.format("%02d", getHour(startTime))+":"+String.format("%02d", getMinute(startTime)));
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

    private boolean saveTask(){
        if(TextUtils.isEmpty(taskTitle.getText().toString())) {
            taskTitle.setError("Musisz podać tytuł");
            return false;
        }
        String key = databaseReference.push().getKey();
        Task task = new Task();
        task.setDateStart(buildDateForTask(startTime));
        task.setDateEnd(buildDateForTask(endTime));
        task.setId(key);
        task.setName(taskTitle.getText().toString());
        task.setNote(taskNote.getText().toString());
        databaseReference.child(key).setValue(task);
        return true;
    }

    private Date buildDateForTask(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDay);
        calendar.set(Calendar.HOUR_OF_DAY, getHour(time));
        calendar.set(Calendar.MINUTE, getMinute((time)));
        return calendar.getTime();
    }
}
