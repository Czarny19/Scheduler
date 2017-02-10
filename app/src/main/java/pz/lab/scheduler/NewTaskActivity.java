package pz.lab.scheduler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class NewTaskActivity extends AppCompatActivity {
    private CheckBox allDayBox;
    private Button endTimeButton, startTimeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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


    }

}
