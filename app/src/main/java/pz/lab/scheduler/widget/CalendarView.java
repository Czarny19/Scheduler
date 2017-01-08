package pz.lab.scheduler.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import pz.lab.scheduler.R;

public class CalendarView extends FrameLayout {

    private  Calendar calendar;
    private float x1,x2;

    public CalendarView(Context context, AttributeSet atribSet) {
        super(context, atribSet);
        calendar= (Calendar) findViewById(R.id.calendar);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view_layout, this, true);

    }
    public boolean onTouchEvent(View v,MotionEvent event)
    {
        return true;
    }
}
