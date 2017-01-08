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

public class CalendarView extends FrameLayout implements View.OnTouchListener{

    private  Calendar calendar;
    private int x1,y1;

    public CalendarView(Context context, AttributeSet atribSet) {
        super(context, atribSet);
        calendar= (Calendar) findViewById(R.id.calendar);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view_layout, this, true);

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v==calendar) {
            float x= event.getX();
            float y= event.getY();

                    for(int i=0;i<7;i++){
                        if(x>calendar.x[i]){
                            x1=i;
                        }
                    }for(int i=0;i<7;i++){
                        if(y>calendar.y[i]){
                            y1=i;
                        }
                    }
          //  calendar.setXY(x1,y1);
            }
        return false;
    }
}
