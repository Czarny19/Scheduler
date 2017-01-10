package pz.lab.scheduler.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Date;

import pz.lab.scheduler.MainScreen;
import pz.lab.scheduler.NoteActivity;
import pz.lab.scheduler.R;

/**
 * Created by Hakus on 2017-01-06.
 */

public class CalendarView extends FrameLayout implements DayPickerListener {
    private TextView hour, minute, doubleDot;
    private DayPickerModel model;
    private Calendar calendar;
    public CalendarView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view_layout, this, true);
        model = new DayPickerModel(new Date());
      model.addTimePickerListener(this);

        calendar = (Calendar) findViewById(R.id.calendar);
        calendar.setDayModel(model);

    }



   // @Override
    public void onTimeSelectionChange(DayPickerEvent event) {
          //  updateLabels();
            changeSelectedDayActivity(event.getSelectedDay());
    }

   // private void updateLabels(){
   // }

// wywoła nowe activity/ otwprzy louout
    private void changeSelectedDayActivity(Date selectedDay){
     //   NoteActivity noteActivity = new NoteActivity();
      //  EditText editText = (EditText) findViewById(R.id.edit_message);
       // String message = editText.getText().toString();
       // intent.putExtra(EXTRA_MESSAGE, message);

      //  startActivity(noteActivity);
        calendar.selectedDay++;
        calendar.invalidate();


    }
}
