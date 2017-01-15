package pz.lab.scheduler.CalendarWidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.Date;

import pz.lab.scheduler.R;

public class CalendarScreenActivity extends AppCompatActivity implements DayPickerListener {
   // public int NUM_ITEMS_PAGE   = 7;

    private DayAdapter ea;
    private CalendarView calendar;
    float x1=0,x2=0;
    ListView lista;
    Date date;
    @Override
    public void onDaySelectionChange(DayPickerEvent event) {
        Toast.makeText(this, "Wybrano dzień", Toast.LENGTH_SHORT).show();
        changeSelectedDayActivity(event.getSelectedDay());
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
        setContentView(R.layout.activity_main_screen);
        // LayoutInflater inflater = getLayoutInflater();
        // Calendar calendar = findViewById(R.layout.activity_main_screen).findViewById(R.id.class.);

        //   this.addTimePickerListener(this);
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.addTimePickerListener(this);
        final View week=(View)findViewById(R.id.week);
        lista=(ListView)findViewById(R.id.weekList);
        lista.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                case MotionEvent.ACTION_DOWN: {
                    x1 = event.getX();
                    break;}
                case MotionEvent.ACTION_UP: {
                    x2 = event.getX();
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
                    // Do what you want
                    break;}

                }return true;
            }
        });

        final ToggleButton tB = (ToggleButton) findViewById(R.id.toggleButton);
        tB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(tB.isChecked()){
                    calendar.setVisibility(View.VISIBLE);
                    week.setVisibility((View.GONE));
                }
                else{
                    calendar.setVisibility(View.GONE);
                    week.setVisibility((View.VISIBLE));
                    date=calendar.date;
                    loadList(date);
                }
            }
        });
    }
    private void loadList(Date date)
    {   int day1;
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        day1=(5+cal.get(Calendar.DAY_OF_WEEK))%7;
        Date dat1=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)-day1);
        Date dat2=new Date(cal.get(Calendar.YEAR)-1900,cal.get(Calendar.MONTH),cal.get(Calendar.DATE)+(6-day1));

        ea=new DayAdapter(this,dat1,dat2);
        lista.setAdapter(ea);
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



