package pz.lab.scheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import pz.lab.scheduler.widget.time.TimePickerView;

public class TimePickerDialog extends AppCompatActivity {
    private TimePickerView picker;
    private Button setTimeButton;
    private int returnCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker_dialog);
        returnCode = getIntent().getExtras().getInt("returnCode");
        picker = (TimePickerView) findViewById(R.id.time_picker);
        setTimeButton = (Button) findViewById(R.id.set_time_button);
        setTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnTime();
            }
        });

    }


    private void returnTime(){
        Intent results = new Intent();
        results.putExtra("time", picker.getTime());
        setResult(Activity.RESULT_OK,results);
        finish();
    }

}
