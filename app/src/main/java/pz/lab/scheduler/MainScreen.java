package pz.lab.scheduler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Date;

import pz.lab.scheduler.widget.Calendar;
import pz.lab.scheduler.widget.DayPickerEvent;
import pz.lab.scheduler.widget.DayPickerListener;
import pz.lab.scheduler.widget.DayPickerModel;

public class MainScreen extends AppCompatActivity implements DayPickerListener {
    DayPickerModel model;

    public void onTimeSelectionChange(DayPickerEvent event) {
        //  updateLabels();
        changeSelectedDayActivity(event.getSelectedDay());
    }

    private void changeSelectedDayActivity(Date selectedDay){
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
       // LayoutInflater inflater = getLayoutInflater();
       // Calendar calendar = findViewById(R.layout.activity_main_screen).findViewById(R.id.class.);

        //model = new DayPickerModel(new Date());
     //   this.addTimePickerListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
