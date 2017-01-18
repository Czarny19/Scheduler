package pz.lab.scheduler.CalendarWidget.Day;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import pz.lab.scheduler.R;

public class DayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        String data = getIntent().getExtras().getString("date");
        TextView topText = (TextView)findViewById(R.id.year);
        //TextView downText = (TextView) rowView.findViewById(R.id.downText);
        //viewHolder.img.setImageDrawable(getResources().getDrawable(R.drawable.box_green));

        topText.setText(data);
        topText.setTextColor(Color.BLUE);
    }
}
