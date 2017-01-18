package pz.lab.scheduler.CalendarWidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.CalendarWidget.Day.DayActivity;
import pz.lab.scheduler.CalendarWidget.event.DayPickerEvent;
import pz.lab.scheduler.CalendarWidget.event.DayPickerListener;
import pz.lab.scheduler.R;

public class CalendarScreenActivity extends AppCompatActivity implements DayPickerListener {
   // public int NUM_ITEMS_PAGE   = 7;

    private DayAdapter ea;
    private CalendarView calendar;
    float x1=0,x2=0;
    private ListView list;
    private Date date;
    private View week;
   // @Override
    public void onDaySelectionChange(DayPickerEvent event) {
        Toast.makeText(this, "Wybrano dzień", Toast.LENGTH_SHORT).show();
        changeSelectedDayActivity(event.getSelectedDay());
    }

    @Override
    public void onModelSelectionChange(DayPickerEvent event) {
    }

    public void taster(){
        Toast.makeText(this, "Scroll", Toast.LENGTH_SHORT).show();
    }


    private void changeSelectedDayActivity(Date selectedDay) {
        Intent intent = new Intent(this, DayActivity.class);
        selectedDay.toString();
        intent.putExtra("date", selectedDay.toString());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_calendar_screen);
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.addTimePickerListener(this);

    }

    private void loadList(Date date)
    {   int day1;
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        day1=(5+cal.get(Calendar.DAY_OF_WEEK))%7;
        Date dat1=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)-day1);
        Date dat2=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)+(6-day1));

        ea=new DayAdapter(this,dat1,dat2);
        list.setAdapter(ea);
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



